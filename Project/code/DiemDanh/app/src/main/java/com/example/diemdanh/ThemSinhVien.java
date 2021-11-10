package com.example.diemdanh;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ThemSinhVien extends AppCompatActivity
{
    EditText tensinhvien, masinhvien, lop;
    Button luu, trove;
    String tenmonhoc, tenlop;
    private SQLiteDatabase dtb;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_sinh_vien);
        dtb = openOrCreateDatabase("DiemDanh.db", MODE_PRIVATE, null);
        AnhXa();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null)
        {
            tenmonhoc = bundle.getString("tenmonhoc", "");
            tenlop = bundle.getString("tenlop", "");
        }
        TaoLopHoc();
        trove.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });
        luu.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                boolean th1 = false;
                boolean th2 = false;
                String ten, ma;
                String tensv = tensinhvien.getText().toString().trim();
                String masv = masinhvien.getText().toString().trim();
                String sql = "SELECT * FROM LopHoc";
                Cursor cursor = dtb.rawQuery(sql, null);
                cursor.moveToFirst();
                while (!cursor.isAfterLast())
                {
                    ten = cursor.getString(2);
                    ma = cursor.getString(3);
                    if (tensv.equals(ten) && masv.equals(ma))
                    {
                        th1 = true;
                        break;
                    } else if(masv.equals(ma))
                    {
                        th2 = true;
                    }
                    cursor.moveToNext();
                }
                if (tensinhvien.getText().toString().isEmpty())
                {
                    tensinhvien.setError("Bạn chưa nhập tên sinh viên");
                    tensinhvien.requestFocus();
                } else if (masinhvien.getText().toString().isEmpty())
                {
                    masinhvien.setError("Bạn chưa nhập mã sinh viên");
                    masinhvien.requestFocus();
                } else if (lop.getText().toString().isEmpty())
                {
                    lop.setError("Bạn chưa nhập lớp");
                    lop.requestFocus();
                } else if (masinhvien.getText().toString().length() != 14)
                {
                    masinhvien.setError("Độ dài mã sinh viên không hợp lệ");
                    masinhvien.requestFocus();
                } else if (th1 == true)
                {
                    masinhvien.setError("Lớp học đã tồn tại sinh viên này");
                    masinhvien.requestFocus();
                } else if (th2 == true)
                {
                    masinhvien.setError("Trùng mã sinh viên, xin vui lòng kiểm tra lại");
                    masinhvien.requestFocus();
                } else
                {
                    String sql3 = "INSERT INTO LopHoc(TenMonHoc, TenLop, TenSinhVien, MaSinhVien, Lop) VALUES ('"+ tenmonhoc +"', " +
                            "'"+ tenlop +"', '"+ tensinhvien.getText().toString().trim() +"', '"+ masinhvien.getText().toString().trim()
                            +"', '"+ lop.getText().toString().trim() +"')";
                    dtb.execSQL(sql3);
                    Toast.makeText(ThemSinhVien.this, "Đã thêm sinh viên vào lớp học", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void AnhXa()
    {
        tensinhvien = (EditText) findViewById(R.id.editTenSinhVien);
        masinhvien = (EditText) findViewById(R.id.editMaSinhVien);
        lop = (EditText) findViewById(R.id.editLop);
        luu = (Button)findViewById(R.id.btnLuuSinhVien);
        trove = (Button)findViewById(R.id.btnTroVeTuThemSinhVien);
    }
    public void TaoLopHoc()
    {
        String sql = "CREATE TABLE IF NOT EXISTS LopHoc (TenMonHoc text, TenLop text, TenSinhVien text, MaSinhVien text, Lop text)";
        //STT integer primary key autoincrement
        dtb.execSQL(sql);
    }
}