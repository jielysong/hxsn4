package com.hxsn.zzd.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hxsn.library.utils.DataUtil;
import com.hxsn.zzd.R;
import com.hxsn.zzd.TApplication;
import com.hxsn.zzd.utils.DebugUtil;


public class SettingActivity extends Activity implements View.OnClickListener {

    private RelativeLayout rl2, rl3, rl4, rl5, rl6;
    private TextView  txtNickname, txtRealname, txtEmail, txtPhone, txtAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        TApplication.activities.add(this);

        addView();
        addListener();
    }

    private void addListener() {
        rl2.setOnClickListener(this);
        rl3.setOnClickListener(this);
        rl4.setOnClickListener(this);
        rl5.setOnClickListener(this);
        rl6.setOnClickListener(this);
    }

    private void addView() {
        rl2 = (RelativeLayout) findViewById(R.id.rl_2);
        rl3 = (RelativeLayout) findViewById(R.id.rl_3);
        rl4 = (RelativeLayout) findViewById(R.id.rl_4);
        rl5 = (RelativeLayout) findViewById(R.id.rl_5);
        rl6 = (RelativeLayout) findViewById(R.id.rl_6);
        TextView txtUsername = (TextView) findViewById(R.id.txt_username);
        txtAddress = (TextView) findViewById(R.id.txt_address);
        txtEmail = (TextView) findViewById(R.id.txt_email);
        txtNickname = (TextView) findViewById(R.id.txt_nickname);
        txtPhone = (TextView) findViewById(R.id.txt_phone);
        txtRealname = (TextView) findViewById(R.id.txt_realname);

        txtUsername.setText(TApplication.user.getUserName());
        txtRealname.setText(TApplication.user.getRealName());
        txtPhone.setText(TApplication.user.getPhone());
        String nickName = TApplication.user.getNickName();
        nickName = DataUtil.toHexString(nickName);
        DebugUtil.d("字符转换", nickName);
        txtNickname.setText(TApplication.user.getNickName());
        txtEmail.setText(TApplication.user.getEmail());
        txtAddress.setText(TApplication.user.getAddress());

        ImageView imgBack = (ImageView) findViewById(R.id.img_left);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DebugUtil.d("SettingActivity onBackPressed");
                onBackPressed();
            }
        });
        TextView txtTitle = (TextView) findViewById(R.id.txt_middle);
        txtTitle.setText("个人设置");
    }


    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.setClass(this, SetTextActivity.class);

        switch (v.getId()) {
            case R.id.rl_2://昵称
                intent.putExtra("mode",2);
                startActivityForResult(intent, 2);
                break;
            case R.id.rl_3://真名
                intent.putExtra("mode",3);
                startActivityForResult(intent, 3);
                break;
            case R.id.rl_4://手机号
                intent.putExtra("mode",4);
                startActivityForResult(intent, 4);
                break;
            case R.id.rl_5://邮箱
                intent.putExtra("mode",5);
                startActivityForResult(intent, 5);
                break;
            case R.id.rl_6://地址
                intent.putExtra("mode",6);
                startActivityForResult(intent, 6);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {
            String stringName = data.getStringExtra("stringName");
            switch (requestCode) {
                case 2:
                    txtNickname.setText(stringName);
                    break;
                case 3:
                    txtRealname.setText(stringName);
                    break;
                case 4:
                    txtPhone.setText(stringName);
                    break;
                case 5:
                    txtEmail.setText(stringName);
                    break;
                case 6:
                    txtAddress.setText(stringName);
                    break;
            }
        }
    }
}
