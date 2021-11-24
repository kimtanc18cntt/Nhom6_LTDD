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
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class DanhSachSinhVien extends AppCompatActivity
{
    ListView lvdssv;
    Button trove;
    TextView tenmonhoc, tenlop;
    private SQLiteDatabase dtb;
    ArrayAdapter<SinhVien> arrayAdapter;
    ArrayAdapter<SinhVien> arrayAdapter2;
    ArrayList<SinhVien> arrayList1 = new ArrayList<>();
    ArrayList<SinhVien> arrayList2 = new ArrayList<>();
    SinhVien sv;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_sinh_vien);
        dtb = openOrCreateDatabase("DiemDanh.db", MODE_PRIVATE, null);
        AnhXa();
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
                convertView = inflater.inflate(R.layout.dssv, null);

                TextView txttensv = convertView.findViewById(R.id.txtTenSinhVienDS);
                TextView txtmsv = convertView.findViewById(R.id.txtMaSinhVienDS);
                TextView txtlop = convertView.findViewById(R.id.txtLopDS);

                SinhVien sv = arrayList1.get(position);

                txttensv.setText(sv.getTenSinhVien());
                txtmsv.setText(sv.getMaSinhVien());
                txtlop.setText(sv.getLop());

                return convertView;
            }
        };
        lvdssv.setAdapter(arrayAdapter);
        LoadData();
        registerForContextMenu(lvdssv);
        lvdssv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
            {
                sv = (SinhVien) parent.getItemAtPosition(position);
                return false;
            }
        });
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
        lvdssv = (ListView)findViewById(R.id.listDSSV);
        trove = (Button)findViewById(R.id.btnTroVeTuDSSV);
        tenmonhoc = (TextView)findViewById(R.id.txtMonHocDS);
        tenlop = (TextView)findViewById(R.id.txtTenLopDS);
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
            boolean th1 = false;
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
                Intent intent = new Intent(DanhSachSinhVien.this, DanhSachSinhVien.class);
                String tenmh = tenmonhoc.getText().toString().trim();
                String tl = tenlop.getText().toString().trim();
                Bundle bundle = new Bundle();
                bundle.putString("tenmonhoc", tenmh);
                bundle.putString("tenlop", tl);
                intent.putExtras(bundle);
                startActivity(intent);
                //startActivity(intent);
            }
        });
        aler.show();
    }
    public void XoaSinhVien()
    {
        String tenmh = tenmonhoc.getText().toString().trim();
        String tl = tenlop.getText().toString().trim();
        String msv = sv.getMaSinhVien();
        String sql = "DELETE FROM LopHoc WHERE TenMonHoc = '"+ tenmh +"' AND TenLop = '"+ tl +"' AND MaSinhVien = '"+ msv +"'";
        dtb.execSQL(sql);
    }
}