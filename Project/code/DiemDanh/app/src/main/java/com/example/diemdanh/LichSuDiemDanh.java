package com.example.diemdanh;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class LichSuDiemDanh extends AppCompatActivity
{
    ListView lvlichsudiemdanh;
    Button trove;
    TextView tenmonhoc, tenlop;
    private SQLiteDatabase dtb;
    ArrayAdapter<MonHoc> arrayAdapter;
    ArrayList<MonHoc> arrayList1 = new ArrayList<>();
    ArrayList<String> arr1 = new ArrayList<>();
    SinhVien sv;
    MonHoc mh;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lich_su_diem_danh);
        dtb = openOrCreateDatabase("DiemDanh.db", MODE_PRIVATE, null);
        AnhXa();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null)
        {
            tenmonhoc.setText(bundle.getString("tenmonhoc", ""));
            tenlop.setText(bundle.getString("tenlop", ""));
        }
        arrayAdapter = new ArrayAdapter<MonHoc>(this, 0 , arrayList1)
        {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.lichsu, null);

                TextView txtngay = convertView.findViewById(R.id.txtNgayDD);
                MonHoc mh = arrayList1.get(position);

                txtngay.setText(mh.getNgay());
                return convertView;
            }
        };
        lvlichsudiemdanh.setAdapter(arrayAdapter);
        LoadData();
        trove.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
                //Intent intent = new Intent(LichSuDiemDanh.this, MainActivity.class);
                //startActivity(intent);
            }
        });
        lvlichsudiemdanh.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                mh = (MonHoc)parent.getItemAtPosition(position);

                Intent intent = new Intent(LichSuDiemDanh.this, ChiTietDiemDanh.class);

                String tenmh = mh.getTenMonHoc();
                String tenlop = mh.getTenLop();
                String ngay = mh.getNgay();

                Bundle bundle = new Bundle();
                bundle.putString("tenmonhoc", tenmh);
                bundle.putString("tenlop", tenlop);
                bundle.putString("ngay", ngay);

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
    public void AnhXa()
    {
        lvlichsudiemdanh = (ListView)findViewById(R.id.listLSDD);
        trove = (Button)findViewById(R.id.btnTroVeTuLSDD);
        tenmonhoc = (TextView)findViewById(R.id.txtMonHoc2);
        tenlop = (TextView)findViewById(R.id.txtTenLopDD2);
    }
    public void LoadData()
    {
        boolean th1 = false;
        String tmh = tenmonhoc.getText().toString().trim();
        String tl = tenlop.getText().toString().trim();
        String sql = "SELECT * FROM LichSuDiemDanh Where TenMonHoc = '"+ tmh +"' AND TenLop = '"+ tl +"'";
        Cursor cursor = dtb.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            String tenmh = cursor.getString(0);
            String tenlop = cursor.getString(1);
            String ngay = cursor.getString(2);
            //arr1.add(ngay);

            MonHoc mh = new MonHoc();
            mh.setTenMonHoc(tenmh);
            mh.setTenLop(tenlop);
            mh.setNgay(ngay);

            if(!(arrayList1.size() == 0))
            {
                for( int i=0; i< arrayList1.size(); i++)
                {
                    if(arrayList1.get(i).getNgay().equals(ngay))
                    {
                        th1 = true;
                        break;
                    }
                    else
                        th1 = false;
                }
                if (th1 == false)
                    arrayList1.add(mh);
            }
            else
            {
                arrayList1.add(mh);
            }
            cursor.moveToNext();
        }
        arrayAdapter.notifyDataSetChanged();
    }

}