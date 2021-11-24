package com.example.diemdanh;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
public class DBManager extends SQLiteOpenHelper
{
    public static final String DATABASE_NAME = "DiemDanh";
    private static final String TABLE_NAME_1 = "MonHoc";
    private static final String MAMONHOC = "MaMonHoc";
    private static final String TENMONHOC = "TenMonHoc";
    private static final String TENLOP = "TenLop";
    private Context context;

    public DBManager(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
        Log.d("DBManager", "DBManager: ");
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String sqlQuery = "CREATE TABLE " + TABLE_NAME_1 + " (" +
                MAMONHOC + " integer primary key AUTOINCREMENT, " +
                TENMONHOC + " TEXT, " + TENLOP + " TEXT)";

        db.execSQL(sqlQuery);
        Toast.makeText(context, "Create successfylly",
                Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_1);
        onCreate(db);
        Toast.makeText(context, "Drop successfylly",
                Toast.LENGTH_SHORT).show();
    }
}