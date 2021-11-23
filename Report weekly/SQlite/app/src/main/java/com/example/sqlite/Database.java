package com.example.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {
    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    // truy van khong tra ket qua:create, ínert, update, delete
    public void QueryData(String sql){// cau lenh truy van kieu chuoi
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    // truy van tra ve ke qua: select
    public Cursor GetData(String sql){
    SQLiteDatabase datebase = getReadableDatabase();
    return datebase.rawQuery(sql, null);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
