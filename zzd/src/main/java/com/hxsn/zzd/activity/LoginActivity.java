package com.hxsn.zzd.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hxsn.library.beans.User;
import com.hxsn.library.db.Shared;
import com.hxsn.library.http.HttpRequest;
import com.hxsn.library.utils.AndroidUtil;
import com.hxsn.library.utils.JsonUtil;
import com.hxsn.zzd.R;
import com.hxsn.zzd.TApplication;
import com.hxsn.zzd.utils.Const;



public class LoginActivity extends Activity implements View.OnClickListener {

    private Button btnLogin;
    private EditText edtUsername, edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        addView();
        addListener();
    }

    private void addView() {
        btnLogin = (Button) findViewById(R.id.btn_login);
        edtUsername = (EditText) findViewById(R.id.edt_username);
        edtPassword = (EditText) findViewById(R.id.edt_password);
        if (TApplication.versionType != Const.RELEASE_VERTION) {
            edtUsername.setText("shouji");
            edtPassword.setText("123456");
        } else {
            String username = Shared.getValue("username");
            edtUsername.setText(username);//显示上次登录过的用户名
        }

    }

    private void addListener() {
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                String name = edtUsername.getText().toString();
                String psw = edtPassword.getText().toString();
                if (name.length() == 0) {
                    AndroidUtil.show(this, "用户名不能为空");
                    break;
                }
                if (psw.length() == 0) {
                    AndroidUtil.show(this, "密码不能为空");
                    break;
                }

                request(name, psw);
                break;
        }
    }

    private void request(String name, String psw) {

        //LoadingDialog.showLoading(this);
        String channelId = Shared.getValue(Const.CODE_CHANNEL_ID);
        if(channelId == null) channelId="";
        String url = Const.URL_LOGIN + name + "&logpwd=" + psw +"&chid="+channelId+"&devtype=3";
        new HttpRequest(this) {
            @Override
            public void getResponse(String response) {
                User user = JsonUtil.getUser(HttpRequest.result);
                if (user != null) {
                    Shared.setValue("userName", user.getUserName());//保存登录名
                }

                Shared.setUser(user);
                TApplication.user = Shared.getUser();
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        }.doPostRequest(url);
    }

}
