package com.snsoft.ctpf.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


/**
 * @desc: Created by jiely on 2015/9/1.
 */
public class DictionaryDao {

    private SQLiteDatabase db;

    public DictionaryDao(SQLiteDatabase db) {
        super();
        this.db = db;
    }

    public void addString(String name, String value) throws Exception {
        String sql = "Insert into dictionary(strkey,strvalue) values(?,?)";
        db.execSQL(sql, new Object[]{name, value});
    }

    public void update(String name, String value) throws Exception {
        ContentValues cv = new ContentValues();
        cv.put("strvalue", value);

        String[] args = {String.valueOf(name)};
        db.update("dictionary", cv, "strkey=?", args);
    }

    public String findValue(String name) throws Exception {
        String sql = "select strvalue from dictionary where strkey=?";
        String value = null;
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(sql, new String[]{name});

            if (cursor.moveToFirst()) {
                value = cursor.getString(cursor.getColumnIndex("strvalue"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return value;
    }
}
