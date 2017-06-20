package com.hxsn.ssk.activity;

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
import com.hxsn.ssk.R;
import com.hxsn.ssk.TApplication;
import com.hxsn.ssk.utils.Const;


public class LoginActivity extends BaseActivity implements View.OnClickListener {

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
            String username = Shared.getValue("userName");
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


        String channelId = Shared.getValue(Const.CODE_CHANNEL_ID);
        String url = Const.URL_LOGIN + name + "&logpwd=" + psw +"&chid="+channelId;
        new HttpRequest(TApplication.context) {
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
