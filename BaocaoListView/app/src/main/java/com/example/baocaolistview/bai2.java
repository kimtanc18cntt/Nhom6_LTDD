package com.example.baocaolistview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class bai2 extends AppCompatActivity {
    ListView lvsinhvien;
    ArrayList<SinhVien> mangSinhVien;
    Button Trove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_bai2);
        lvsinhvien = (ListView) findViewById(R.id.listmonan);
        Trove = (Button) findViewById(R.id.bttrove);
        Trove.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });
        mangSinhVien = new ArrayList<SinhVien>();
        mangSinhVien.add(new SinhVien("Lê Kim Tân ",1996));
        mangSinhVien.add(new SinhVien("Cao Thị Thu Dân ", 2000 ));
        mangSinhVien.add(new SinhVien("Nguyễn Hiền Luân  ",2000 ));
        mangSinhVien.add(new SinhVien("Tân Đẹp Trai ",1996 ));
        mangSinhVien.add(new SinhVien("Tân Men Lì ",1996 ));
        mangSinhVien.add(new SinhVien("Dân Đẹp gái ",1996));

        SinhVienAdapter adapter = new SinhVienAdapter(
                bai2.this,
                R.layout.dong_sinh_vien,
                mangSinhVien
        );
        lvsinhvien.setAdapter(adapter);
    }
}