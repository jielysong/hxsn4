package com.hxsn.library.http;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.hxsn.library.Memory;
import com.hxsn.library.utils.AndroidUtil;
import com.hxsn.library.utils.NetUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Map;


public abstract class HttpRequest {

    public static String result;
    private Context context;
    private MyStringRequest stringRequest;

    public HttpRequest(Context context) {
        this.context = context;
    }

    public String getResult() {
        return result;
    }

    public abstract void getResponse(String response);

    public HttpRequest doMyGet(final String url) {
        Log.i("HttpRequest-doMyGet", "url=" + url);
        if (!NetUtil.isConnect( context)) {
            AndroidUtil.show(context, "没有网络连接");
            return null;
        }

        stringRequest = new MyStringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("HttpRequest-doMyGet", "response=" + response);
                getResponse(response);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.i("HttpRequest-doMyGet", "volleyError=" + volleyError.toString());
                handleError(volleyError);
                volleyError.printStackTrace();
            }
        });
        stringRequest.setRetryPolicy((new DefaultRetryPolicy(20 * 1000, 5, 1.0f)));
        Memory.requestQueue.add(stringRequest);
        return null;
    }

    public void setRetryPolicy(int x,int cnt){
        stringRequest.setRetryPolicy((new DefaultRetryPolicy(x, cnt, 1.0f)));
    }

    private void handleJson(JSONObject jsonObject){
        try {
            this.result = jsonObject.getString("result");
            getResponse(this.getResult());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void doGet(final String url) {
        Log.i("HttpRequest-doGet", "url=" + url);
        if (!NetUtil.isConnect( context)) {
            AndroidUtil.show(context, "没有网络连接");
            return;
        }

        stringRequest = new MyStringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("HttpRequest-doGet", "response=" + response);
                codeSwitch(response);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.i("HttpRequest-doGet", "volleyError=" + volleyError.toString());
                handleError(volleyError);
                volleyError.printStackTrace();
            }
        });

        Memory.requestQueue.add(stringRequest);
    }

    public void doPostRequest(final String url) {
        Log.i("HttpRequest", "url=" + url);
        if (!NetUtil.isConnect(context)) {
            AndroidUtil.show(context, "没有网络连接");
            return;
        }
        stringRequest = new MyStringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                codeSwitch(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("LOGIN-ERROR", error.getMessage(), error);
                byte[] htmlBodyBytes = error.networkResponse.data;
                Log.e("LOGIN-ERROR", new String(htmlBodyBytes), error);
                handleError(error);
            }
        });
        Memory.requestQueue.add(stringRequest);
    }

    /**
     * post传参
     * @param url 接口
     * @param map 参数
     */
    public void doPostRequest(final String url,Map<String,String> map) {
        Log.i("HttpRequest", "url=" + url);
        if (!NetUtil.isConnect(context)) {
            AndroidUtil.show(context, "没有网络连接");
            return;
        }
        stringRequest = new MyStringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                codeSwitch(response);
            }
        },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                handleError(volleyError);
            }
        });
        stringRequest.setpMap(map);
        Memory.requestQueue.add(stringRequest);
    }


    /**
     * post 传参和上传文件
     * @param url      接口
     * @param fileName 上传文件的命名空间
     * @param file      上传的文件
     * @param map      参数
     */
    public void doPostRequest(final String url,String fileName,File file,Map<String,String> map){
        Log.i("HttpRequest", "url=" + url);
        if (!NetUtil.isConnect(context)) {
            AndroidUtil.show(context, "没有网络连接");
            return;
        }

        MultipartRequest postRequest = new MultipartRequest(url, fileName,file,map,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        codeSwitch(response);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                handleError(volleyError);
            }
        });

        Memory.requestQueue.add(postRequest);
    }


    private void codeSwitch(String response) {
        Log.i("HttpRequest", "response=" + response);
        try {
            JSONObject jsonObject = new JSONObject(response);
            int code = jsonObject.getInt("code");
            switch (code) {
                case 200:
                    this.result = jsonObject.getString("result");
                    response = this.getResult();
                    getResponse(response);
                    break;
                case 101:
                    AndroidUtil.show(context, "参数错误");
                    break;
                case 301:
                    AndroidUtil.show(context, "用户名不存在或密码错误");
                    break;
                case 302:
                    AndroidUtil.show(context, "账号被停用，该账号被管理员停用");
                    break;
                case 320:
                    AndroidUtil.show(context, "原始密码错误");
                    break;
                default:
                    AndroidUtil.show(context, "未知错误");
                    break;
            }
        } catch (JSONException e) {
            AndroidUtil.show(context, "json格式不正确");
            e.printStackTrace();
        }
    }


    private void handleError(VolleyError volleyError){
        NetworkResponse networkResponse = volleyError.networkResponse;
        if (networkResponse != null) {
            switch (networkResponse.statusCode) {
                case 408:
                    AndroidUtil.show(context, "超时");
                    break;
                case 401:
                    break;
            }
        }
    }

}
