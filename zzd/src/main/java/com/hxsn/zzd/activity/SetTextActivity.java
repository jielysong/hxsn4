package com.hxsn.zzd.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxsn.library.db.Shared;
import com.hxsn.library.http.HttpRequest;
import com.hxsn.zzd.R;
import com.hxsn.zzd.TApplication;
import com.hxsn.zzd.utils.Const;

import java.util.HashMap;
import java.util.Map;

//设置我的信息
public class SetTextActivity extends Activity implements View.OnClickListener {

    private EditText edtSsk;
    private TextView txtTitle, txtNotice;
    private Button btnSure;
    private ImageView imgLeft;
    private String[] params = {"nickname", "realname", "phone", "email", "address"};//上传服务器的参数
    private String param;
    private String oldName;
    private int mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_text);

        addView();
        addListener();

        setText();
    }

    private void addListener() {
        imgLeft.setOnClickListener(this);
        btnSure.setOnClickListener(this);
    }

    private void addView() {
        edtSsk = (EditText) findViewById(R.id.edt_ssk);
        txtTitle = (TextView) findViewById(R.id.txt_middle);
        txtNotice = (TextView) findViewById(R.id.txt_notice);
        btnSure = (Button) findViewById(R.id.btn_sure);
        imgLeft = (ImageView) findViewById(R.id.img_left);
    }

    private void setText() {
        Intent intent = getIntent();
        mode = intent.getIntExtra("mode",2);
        switch (mode) {
            case 2:
                txtTitle.setText("修改昵称");
                oldName = TApplication.user.getNickName();
                edtSsk.setText(oldName);
                param = params[0];
                break;
            case 3:
                txtTitle.setText("添加真实姓名");
                oldName = TApplication.user.getRealName();
                edtSsk.setText(oldName);
                txtNotice.setVisibility(View.VISIBLE);
                param = params[1];
                break;
            case 4:
                txtTitle.setText("修改手机号");
                oldName = TApplication.user.getPhone();
                edtSsk.setText(oldName);
                param = params[2];
                break;
            case 5:
                oldName = TApplication.user.getEmail();
                edtSsk.setText(TApplication.user.getEmail());
                txtTitle.setText("修改邮箱");
                param = params[3];
                break;
            case 6:
                oldName = TApplication.user.getAddress();
                edtSsk.setText(TApplication.user.getAddress());
                txtTitle.setText("修改地址");
                param = params[4];
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_left:
                onBackPressed();
                break;
            case R.id.btn_sure:
                final String name = edtSsk.getText().toString();
                if (name.length() == 0 || name.equals(oldName)) {
                    onBackPressed();
                    break;
                }
                Map<String,String> map = new HashMap<>();
                map.put("uid",TApplication.user.getUserId());
                map.put(param,name);
                String url = Const.URL_MODIFY_MINE;// + TApplication.user.getId() + "&" + param + "=" + name;
                new HttpRequest(this) {
                    Intent intent = new Intent(SetTextActivity.this, SettingActivity.class);
                    @Override
                    public void getResponse(String response) {
                        switch (mode) {
                            case 2:
                                TApplication.user.setNickName(name);
                                break;
                            case 3:
                                TApplication.user.setRealName(name);
                                break;
                            case 4:
                                TApplication.user.setPhone(name);
                                break;
                            case 5:
                                TApplication.user.setEmail(name);
                                break;
                            case 6:
                                TApplication.user.setAddress(name);
                                break;
                        }
                        Shared.setUser(TApplication.user);
                        intent.putExtra("stringName", name);
                        SetTextActivity.this.setResult(Activity.RESULT_OK,intent);
                        finish();
                    }
                }.doPostRequest(url,map);
                break;
        }

    }
}
