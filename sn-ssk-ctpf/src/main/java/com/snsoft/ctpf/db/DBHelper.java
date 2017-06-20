package com.snsoft.ctpf.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.snsoft.ctpf.util.Const;
import com.snsoft.ctpf.util.DebugUtil;


/**
 *  Created by Administrator on 16-4-7.
 */
public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, Const.SQLITE_DBNAME, null, Const.SQLITE_VERTION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

       /* //创建user表
        String sql = "create table if not exists user(" +
                "id Integer PRIMARY KEY," +
                "userId varchar(60)," +
                "userName varchar(50)," +
                "realName varchar(50)," +
                "nickName varchar(50)," +
                "phone varchar(30)," +
                "email varchar(30)," +
                "address varchar(50))";
        db.execSQL(sql);
        DebugUtil.i("DbHelper", "user table create ok");*/

        //创建字典表
        String sql = "create table if not exists dictionary(" +
                "strkey varchar(12) PRIMARY KEY," +
                "strvalue varchar(50))";
        db.execSQL(sql);
        DebugUtil.i("DbHelper", "dictionary table create ok");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists dictionary");
        //db.execSQL("drop table if exists user");
        onCreate(db);
    }
}
