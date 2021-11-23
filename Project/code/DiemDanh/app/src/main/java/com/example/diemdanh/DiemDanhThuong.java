package com.example.diemdanh;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DiemDanhThuong extends AppCompatActivity
{
    ListView lvdiemdanhthuong;
    Button luu, trove;
    TextView tenmonhoc, tenlop, ngay;
    TextView txttensv, txtmsv, txtlop;
    CheckBox cbddt;
    CheckedTextView cbtv;
    String txt1;
    SinhVien sv;

    private SQLiteDatabase dtb;
    ArrayAdapter<SinhVien> arrayAdapter;
    ArrayList<SinhVien> arrayList1 = new ArrayList<>();
    ArrayList<Integer> arr1 = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diem_danh_thuong);
        dtb = openOrCreateDatabase("DiemDanh.db", MODE_PRIVATE, null);
        AnhXa();
        TaoLopHoc();
        TaoLichSuDiemDanh();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String str = df.format(new Date());
        ngay.setText(str);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null)
        {
            tenmonhoc.setText(bundle.getString("tenmonhoc", ""));
            tenlop.setText(bundle.getString("tenlop", ""));
        }
        arrayAdapter = new ArrayAdapter<SinhVien>(this, 0,arrayList1)
        {
            @NonNull
            @Override
            public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.diemdanhthuong, null);

                txttensv = convertView.findViewById(R.id.txtTenSinhVien);
                txtmsv = convertView.findViewById(R.id.txtMaSinhVien);
                txtlop = convertView.findViewById(R.id.txtLop);
                cbddt = convertView.findViewById(R.id.cbDiemDanhThuong);

                cbddt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
                {
                    boolean th1 = false;
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b)
                    {
                            if (!(arr1.size() == 0)) {
                                for (int i = 0; i < arr1.size(); i++) {
                                    if (arr1.get(i).equals(position)) {
                                        th1 = true;
                                        break;
                                    } else
                                        th1 = false;
                                }
                                if (th1 == false)
                                    arr1.add(position);
                            } else {
                                arr1.add(position);
                            }


                    }
                });


                SinhVien sv = arrayList1.get(position);

                txttensv.setText(sv.getTenSinhVien());
                txtmsv.setText(sv.getMaSinhVien());
                txtlop.setText(sv.getLop());

                return convertView;
            }
        };
        lvdiemdanhthuong.setAdapter(arrayAdapter);
        LoadData();
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
                int i;
                String txt = "";
                for( i=0; i< arr1.size(); i++)
                {
                    txt += arrayList1.get(arr1.get(i)).getTenSinhVien() + "-";
                    //txt += arr1.get(i) + "-";
                    //txt += arr2.get(i);
                    String sql = "INSERT INTO LichSuDiemDanh (TenMonHoc, TenLop, Ngay, TenSinhVien, MaSinhVien, Lop) " +
                            "VALUES ('"+ tenmonhoc.getText().toString().trim() +"', '"+ tenlop.getText().toString().trim() +"', " +
                            "'"+ ngay.getText().toString().trim() +"', '"+ arrayList1.get(arr1.get(i)).getTenSinhVien() +"', " +
                            "'"+ arrayList1.get(arr1.get(i)).getMaSinhVien() +"', '"+ arrayList1.get(arr1.get(i)).getLop() +"')";
                    dtb.execSQL(sql);
                }
                Toast.makeText(DiemDanhThuong.this, "Đã lưu danh sách điểm danh \n" + txt, Toast.LENGTH_LONG).show();
                arr1.clear();
                finish();
                Intent intent = new Intent(DiemDanhThuong.this, MainActivity.class);
                startActivity(intent);
                //arrayList2.clear();
                //}
            }
        });
        registerForContextMenu(lvdiemdanhthuong);
        lvdiemdanhthuong.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
            {
                sv = (SinhVien)parent.getItemAtPosition(position);
                return false;
            }
        });
    }
    public void AnhXa()
    {
        lvdiemdanhthuong = (ListView)findViewById(R.id.listDiemDanhThuong);
        luu = (Button)findViewById(R.id.btnLuuDDT);
        trove = (Button)findViewById(R.id.btnTroVeTuDiemDanhThuong);
        tenmonhoc = (TextView)findViewById(R.id.txtMonHoc);
        tenlop = (TextView)findViewById(R.id.txtTenLopDD);
        ngay = (TextView)findViewById(R.id.txtNgay);
    }
    public void TaoLopHoc()
    {
        String sql = "CREATE TABLE IF NOT EXISTS LopHoc (TenMonHoc text, TenLop text, TenSinhVien text, MaSinhVien text, Lop text)";
        //STT integer primary key autoincrement
        dtb.execSQL(sql);
    }
    public void TaoLichSuDiemDanh()
    {
        String sql = "CREATE TABLE IF NOT EXISTS LichSuDiemDanh (TenMonHoc text, TenLop text, Ngay text, TenSinhVien text, " +
                "MaSinhVien text, Lop text)";
        //STT integer primary key autoincrement
        dtb.execSQL(sql);
    }
    public void LoadData()
    {
        String tmh = tenmonhoc.getText().toString().trim();
        String tl = tenlop.getText().toString().trim();
        String sql = "SELECT * FROM LopHoc Where TenMonHoc = '"+ tmh +"' AND TenLop = '"+ tl +"'";
        Cursor cursor = dtb.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            String tensinhvien = cursor.getString(2);
            String masinhvien = cursor.getString(3);
            String lop = cursor.getString(4);

            SinhVien sv = new SinhVien();
            sv.setTenSinhVien(tensinhvien);
            sv.setMaSinhVien(masinhvien);
            sv.setLop(lop);

            arrayList1.add(sv);
            cursor.moveToNext();
        }
        arrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        getMenuInflater().inflate(R.menu.menusinhvien, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menuXoaSinhVien:
                XacNhanXoa();
                break;
        }
        return super.onContextItemSelected(item);
    }
    public void XoaSinhVien()
    {
        String masv = sv.getMaSinhVien();
        String sql = "DELETE FROM LopHoc WHERE TenMonHoc = '"+ tenmonhoc.getText().toString().trim() +"' AND TenLop = '"+
                tenlop.getText().toString().trim() +"' AND MaSinhVien = '"+ masv +"'";
        dtb.execSQL(sql);
    }
    public void XacNhanXoa()
    {
        final AlertDialog.Builder aler = new AlertDialog.Builder(this);
        aler.setTitle("Điểm Danh");
        aler.setMessage("Bạn có chắc muốn xóa?");
        aler.setPositiveButton("Đóng", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {

            }
        });
        aler.setNegativeButton("Xóa", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                XoaSinhVien();
                finish();
                Intent intent = new Intent(DiemDanhThuong.this, DiemDanhThuong.class);
                startActivity(intent);
            }
        });
        aler.show();
    }
}