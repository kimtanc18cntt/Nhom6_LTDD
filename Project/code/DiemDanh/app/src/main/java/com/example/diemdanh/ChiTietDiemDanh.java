package com.example.diemdanh;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ChiTietDiemDanh extends AppCompatActivity
{
    ListView lvchitietdd;
    Button trove;
    TextView tenmonhoc, tenlop, ngay, soluong, tongsoluong;
    private SQLiteDatabase dtb;
    ArrayAdapter<SinhVien> arrayAdapter;
    ArrayList<SinhVien> arrayList1 = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_diem_danh);
        dtb = openOrCreateDatabase("DiemDanh.db", MODE_PRIVATE, null);
        AnhXa();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null)
        {
            tenmonhoc.setText(bundle.getString("tenmonhoc", ""));
            tenlop.setText(bundle.getString("tenlop", ""));
            ngay.setText(bundle.getString("ngay", ""));
        }
        Cursor Count= dtb.rawQuery("SELECT * FROM LichSuDiemDanh Where TenMonHoc = '"+ tenmonhoc.getText().toString().trim()
                +"' AND TenLop = '"+ tenlop.getText().toString().trim() +"' AND Ngay = '"+ ngay.getText().toString().trim() +"'",
                null);
        //String sl= String.valueOf(Count.getCount());
        soluong.setText(String.valueOf(Count.getCount()));
        Cursor Count2= dtb.rawQuery("SELECT * FROM LopHoc Where TenMonHoc = '"+ tenmonhoc.getText().toString().trim()
                +"' AND TenLop = '"+ tenlop.getText().toString().trim() +"'", null);
        tongsoluong.setText(String.valueOf(Count2.getCount()));
        arrayAdapter = new ArrayAdapter<SinhVien>(this, 0,arrayList1)
        {
            @NonNull
            @Override
            public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.chitiet, null);

                TextView txttensv = convertView.findViewById(R.id.txtTenSinhVien);
                TextView txtmsv = convertView.findViewById(R.id.txtMaSinhVien);
                TextView txtlop = convertView.findViewById(R.id.txtLop);

                SinhVien sv = arrayList1.get(position);

                txttensv.setText(sv.getTenSinhVien());
                txtmsv.setText(sv.getMaSinhVien());
                txtlop.setText(sv.getLop());

                return convertView;
            }
        };
        lvchitietdd.setAdapter(arrayAdapter);
        LoadData();
        trove.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });
    }
    public void AnhXa()
    {
        lvchitietdd = (ListView)findViewById(R.id.listChiTietDD);
        trove = (Button)findViewById(R.id.btnTroVeTuCTDD);
        tenmonhoc = (TextView)findViewById(R.id.txtMonHocCT);
        tenlop = (TextView)findViewById(R.id.txtTenLopCT);
        ngay = (TextView)findViewById(R.id.txtNgayCT);
        soluong = (TextView)findViewById(R.id.txtSoLuong);
        tongsoluong = (TextView)findViewById(R.id.txtTongSoLuong);
    }
    public void LoadData()
    {
        String tmh = tenmonhoc.getText().toString().trim();
        String tl = tenlop.getText().toString().trim();
        String ng = ngay.getText().toString().trim();
        String sql = "SELECT * FROM LichSuDiemDanh Where TenMonHoc = '"+ tmh +"' AND TenLop = '"+ tl +"' AND Ngay = '"+ ng +"'";
        Cursor cursor = dtb.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            String tensinhvien = cursor.getString(3);
            String masinhvien = cursor.getString(4);
            String lop = cursor.getString(5);

            SinhVien sv = new SinhVien();
            sv.setTenSinhVien(tensinhvien);
            sv.setMaSinhVien(masinhvien);
            sv.setLop(lop);

            arrayList1.add(sv);
            cursor.moveToNext();
        }
        arrayAdapter.notifyDataSetChanged();
    }
}