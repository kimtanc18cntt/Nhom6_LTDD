package com.example.diemdanh;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.View;
import android.widget.Button;
import android.database.Cursor;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ListView;
import android.database.sqlite.SQLiteDatabase;

public class ThemMonHoc extends AppCompatActivity
{
    Button trove, luu;
    EditText tenmonhoc, tenlop;
    private SQLiteDatabase dtb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_mon_hoc);
        dtb = openOrCreateDatabase("DiemDanh.db", MODE_PRIVATE, null);
        AnhXa();
        TaoMonHoc();
        trove.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
                Intent intent = new Intent(ThemMonHoc.this, MainActivity.class);
                startActivity(intent);
            }
        });
        luu.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                boolean th1 = false;
                boolean th2 = false;
                String tenmh, tenl;
                String ten = tenmonhoc.getText().toString().trim();
                String lop = tenlop.getText().toString().trim();
                String sql = "SELECT * FROM MonHoc";
                Cursor cursor = dtb.rawQuery(sql, null);
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    tenmh = cursor.getString(0);
                    tenl = cursor.getString(1);
                    if (ten.equals(tenmh) && lop.equals(tenl))
                    {
                            th1 = true;
                            break;
                    }
                    cursor.moveToNext();
                }
                if (tenmonhoc.getText().toString().isEmpty())
                {
                    tenmonhoc.setError("B???n ch??a nh???p t??n m??n h???c");
                    tenmonhoc.requestFocus();
                } else if (tenlop.getText().toString().isEmpty())
                {
                    tenlop.setError("B???n ch??a nh???p t??n l???p");
                    tenlop.requestFocus();
                } else if (th1 == true) {
                    tenmonhoc.setError("L???p h???c ???? t???n t???i m??n h???c n??y");
                    tenmonhoc.requestFocus();
                }else
                {
                    String sql3 = "INSERT INTO MonHoc(TenMonHoc, TenLop) VALUES ('"+ tenmonhoc.getText().toString().trim() +"', " +
                            "'"+ tenlop.getText().toString().trim() +"')";
                    dtb.execSQL(sql3);
                    Toast.makeText(ThemMonHoc.this, "T???o m??n h???c th??nh c??ng", Toast.LENGTH_LONG).show();
                    finish();
                    Intent intent = new Intent(ThemMonHoc.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
    public void AnhXa()
    {
        tenmonhoc = (EditText)findViewById(R.id.editTenMonHoc);
        tenlop = (EditText)findViewById(R.id.editTenLop);
        trove = (Button) findViewById(R.id.btnTroVeTuThemMonHoc);
        luu = (Button)findViewById(R.id.btnLuuMonHoc);
    }
    public void TaoMonHoc()
    {
        String sql = "CREATE TABLE IF NOT EXISTS MonHoc (TenMonHoc text, TenLop text)";
        //STT integer primary key autoincrement
        dtb.execSQL(sql);
    }
}