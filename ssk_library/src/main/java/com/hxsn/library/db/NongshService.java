package com.hxsn.library.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.hxsn.library.beans.Nongsh;

import java.util.List;


public class NongshService {
    private static NongshService instance;
    NongshDao nongshDao;
    private SskDbHelper helper;
    private SQLiteDatabase db;//DatabaseManager.getInstance().openDatabase();

    //写入 ，不然会是出错，是空指针
    private NongshService(Context context) {
        helper = new SskDbHelper( context);
        //LogUtil.i("UserService", "helper"+helper);
        DatabaseManager.initialize( context, helper);
        db = DatabaseManager.getInstance().openDatabase();
        nongshDao = new NongshDao(db);
    }

    public static NongshService getInstance(Context context) {
        if (instance == null) {
            instance = new NongshService(context);
        }
        return instance;
    }

    public void clear() {
        db = DatabaseManager.getInstance().openDatabase();
        db.beginTransaction();
        try {
            nongshDao.clear();
            db.setTransactionSuccessful();// 设置事务标志为成功，当结束事务时就会提交事务
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            // 结束事务
            db.endTransaction();//结束事务,有两种情况：commit,rollback
            DatabaseManager.getInstance().closeDatabase();
        }
    }

    public void add(Nongsh nongsh) {
        db = DatabaseManager.getInstance().openDatabase();
        db.beginTransaction();
        try {
            nongshDao.insert(nongsh);
            db.setTransactionSuccessful();// 设置事务标志为成功，当结束事务时就会提交事务
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            // 结束事务
            db.endTransaction();
            DatabaseManager.getInstance().closeDatabase();
        }
    }

    public List<Nongsh> getNongshList() {
        db = DatabaseManager.getInstance().openDatabase();
        List<Nongsh> nongshList = null;
        try {
            nongshList = nongshDao.findAll();
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            DatabaseManager.getInstance().closeDatabase();
        }
        return nongshList;
    }

}
