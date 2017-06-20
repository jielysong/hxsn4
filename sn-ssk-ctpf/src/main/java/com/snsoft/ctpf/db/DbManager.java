package com.snsoft.ctpf.db;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.snsoft.ctpf.util.Const;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *  Created by jiely on 2016/5/20.
 */
public class DbManager {

    private static DbManager manager;
    private SQLiteDatabase database,historyDatabase;
    private static final int BUFFER_SIZE = 1024;

    //private static SQLiteOpenHelper mDatabaseHelper;
    private AtomicInteger mOpenCounter = new AtomicInteger();
    private AtomicInteger mOpenCounterHistory = new AtomicInteger();


    public static synchronized void initialize( SQLiteOpenHelper helper) {
        if (manager == null) {
            manager = new DbManager();
            //mDatabaseHelper = helper;
        }
    }


    public static synchronized DbManager getInstance(){
        if(null == manager){
            manager = new DbManager();
        }
        return manager;
    }

    private DbManager() {

    }

    //获取存入本地的数据库
    public SQLiteDatabase getLocalDatabase(){
        if (mOpenCounter.incrementAndGet() == 1) {
            database = SQLiteDatabase.openOrCreateDatabase(Const.PATH_DBFILE, null);
            Log.i("DbManager","local-sql-path="+Const.PATH_DBFILE);
        }
        return database;
    }

    public SQLiteDatabase getHistoryDatabase() {
        if (mOpenCounterHistory.incrementAndGet() == 1) {
            historyDatabase = SQLiteDatabase.openOrCreateDatabase(Const.PATH_HISTORY_DBFILE, null);
            Log.i("DbManager","history-sql-path="+Const.PATH_HISTORY_DBFILE);
        }
        return historyDatabase;
    }

    public void initdb(Context context) {

        File file = new File(Const.PATH_DB_DIR);
        if(!file.exists()){
            file.mkdirs();
        }

        file = new File(Const.PATH_DBFILE);
        Log.i("DbManager","dbFile_path="+Const.PATH_DBFILE);

        AssetManager assetManager = context.getAssets();
        InputStream is= null;
        FileOutputStream fos = null;
        try {
            is = assetManager.open(Const.ASSETS_DB_NAME);//"sn_ctpf_client.sqlite3"

            fos = new FileOutputStream(file);
            byte[] buffer = new byte[BUFFER_SIZE];
            int count;
            while ((count = is.read(buffer)) > 0) {
                Log.i("DbManager","count="+count);
                fos.write(buffer, 0, count);
            }
            fos.flush();
            fos.close();
            Log.i("DbManager","数据库复制成功！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeHistoryDatabase() {
        int size = mOpenCounterHistory.decrementAndGet();
        if (size == 0) {
            this.historyDatabase.close();
        }
    }

    public synchronized void closeDatabase() {
        int size = mOpenCounter.decrementAndGet();
        if (size == 0) {
            database.close();
        }
    }
}
