package com.hxsn.library.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hxsn.library.beans.Nongsh;

import java.util.ArrayList;
import java.util.List;


/**
 * String realName;
 * String nickName;
 * String email;
 * String phone;
 * String address;
 */
public class NongshDao {

    private final String SQL_ADD = "Insert into nongsh(nongshId,name,path) values(?,?,?)";
    private final String SQL_SELECT = "select nongshId,name,path from nongsh";
                                    // "select userId,userName,realName,nickName,email,phone,address from user"
    private final String SQL_DELETE = "delete from nongsh";
    private final String[] NONGSH_STRINGS = {"nongshId", "name", "path"};
    private SQLiteDatabase db;

    public NongshDao(SQLiteDatabase db) {
        super();
        this.db = db;
    }


    public void clear() throws Exception {
        db.execSQL(SQL_DELETE);
    }


    public void insert(Nongsh nongsh) throws Exception {
        db.execSQL(SQL_ADD, new Object[]{nongsh.getId(), nongsh.getName(), nongsh.getPath()});
    }

    public void update(Nongsh nongsh) throws Exception {
        ContentValues cv = new ContentValues();
        cv.put(NONGSH_STRINGS[0], nongsh.getId());
        cv.put(NONGSH_STRINGS[1], nongsh.getName());
        cv.put(NONGSH_STRINGS[2], nongsh.getPath());

        String[] args = {String.valueOf(nongsh.getId())};
        db.update("user", cv, "userId=?", args);
    }

    public List<Nongsh> findAll() throws Exception {

        List<Nongsh> nongshList = new ArrayList<Nongsh>();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(SQL_SELECT, new String[]{});

            while (cursor.moveToNext()){
                Nongsh nongsh = new Nongsh();
                nongsh.setId(cursor.getString(cursor.getColumnIndex(NONGSH_STRINGS[0])));
                nongsh.setName(cursor.getString(cursor.getColumnIndex(NONGSH_STRINGS[1])));
                nongsh.setPath(cursor.getString(cursor.getColumnIndex(NONGSH_STRINGS[2])));
                nongshList.add(nongsh);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return nongshList;
    }
}
