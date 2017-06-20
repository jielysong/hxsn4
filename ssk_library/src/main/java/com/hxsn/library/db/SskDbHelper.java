package com.hxsn.library.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 *  Created by Administrator on 16-4-7.
 */
public class SskDbHelper extends SQLiteOpenHelper {

    public static final int SQLITE_VERTION = 2;              //数据库版本
    public static final String SQLITE_DBNAME = "ssk";            //数据库名称

    public SskDbHelper(Context context) {
        super(context, SQLITE_DBNAME, null, SQLITE_VERTION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //创建user表
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
        Log.i("DbHelper", "user table create ok");


        //创建农事汇信息表
        sql = "create table if not exists nongsh(" +
                "id Integer PRIMARY KEY," +
                "nongshId varchar(60)," +
                "name varchar(50)," +
                "path varchar(50))";
        db.execSQL(sql);
        Log.i("DbHelper", "nongsh table create ok");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists dictionary");
        db.execSQL("drop table if exists user");
        db.execSQL("drop table if exists nongsh");
        onCreate(db);
    }
}
