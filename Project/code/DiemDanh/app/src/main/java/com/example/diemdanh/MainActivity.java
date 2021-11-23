package com.example.diemdanh;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.LayoutInflater;
import android.database.Cursor;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import java.util.ArrayList;
import android.widget.TextView;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity
{
    ListView lvmonhoc;
    Button themmonhoc;
    MonHoc mh;
    private SQLiteDatabase dtb;
    ArrayAdapter<MonHoc> arrayAdapter;
    ArrayList<MonHoc> arrayList1 = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dtb = openOrCreateDatabase("DiemDanh.db", MODE_PRIVATE, null);
        AnhXa();
        TaoMonHoc();
        arrayAdapter = new ArrayAdapter<MonHoc>(this, 0 , arrayList1)
        {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.monhoc, null);

                TextView txttenmh = convertView.findViewById(R.id.txtTenMonHoc);
                TextView txttenlop = convertView.findViewById(R.id.txtTenLop);

                MonHoc mh = arrayList1.get(position);

                txttenmh.setText(mh.getTenMonHoc());
                txttenlop.setText(mh.getTenLop());
                return convertView;
            }
        };
        lvmonhoc.setAdapter(arrayAdapter);
        LoadData();
        themmonhoc.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainActivity.this, ThemMonHoc.class);
                startActivity(intent);
            }
        });
        registerForContextMenu(lvmonhoc);
        lvmonhoc.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
            {
                mh = (MonHoc)parent.getItemAtPosition(position);
                return false;
            }
        });
    }
    public void AnhXa()
    {
       lvmonhoc = (ListView)findViewById(R.id.listMonHoc);
       themmonhoc = (Button)findViewById(R.id.btnThemMonHoc);
    }
    public void LoadData()
    {
        String sql = "SELECT * FROM MonHoc";
        Cursor cursor = dtb.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            boolean th1 = false;
            String tenmh = cursor.getString(0);
            String tenlop = cursor.getString(1);

            MonHoc mh = new MonHoc();
            mh.setTenMonHoc(tenmh);
            mh.setTenLop(tenlop);

            arrayList1.add(mh);
            cursor.moveToNext();
        }
        arrayAdapter.notifyDataSetChanged();
    }
    public void TaoMonHoc()
    {
        String sql = "CREATE TABLE IF NOT EXISTS MonHoc (TenMonHoc text, TenLop text)";
        //STT integer primary key autoincrement
        dtb.execSQL(sql);
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        getMenuInflater().inflate(R.menu.menumonhoc, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menuThemSV:
                Intent intent = new Intent(MainActivity.this, ThemSinhVien.class);

                String tenmh = mh.getTenMonHoc();
                String tenlop = mh.getTenLop();
                Bundle bundle = new Bundle();
                bundle.putString("tenmonhoc", tenmh);
                bundle.putString("tenlop", tenlop);

                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.menuDiemDanhThuong:
                Intent intent2 = new Intent(MainActivity.this, DiemDanhThuong.class);

                String tenmh2 = mh.getTenMonHoc();
                String tenlop2 = mh.getTenLop();
                Bundle bundle2 = new Bundle();
                bundle2.putString("tenmonhoc", tenmh2);
                bundle2.putString("tenlop", tenlop2);

                intent2.putExtras(bundle2);
                startActivity(intent2);
                break;

            case R.id.menuLichSu:
                Intent intent3 = new Intent(MainActivity.this, LichSuDiemDanh.class);

                String tenmh3 = mh.getTenMonHoc();
                String tenlop3 = mh.getTenLop();
                Bundle bundle3 = new Bundle();
                bundle3.putString("tenmonhoc", tenmh3);
                bundle3.putString("tenlop", tenlop3);

                intent3.putExtras(bundle3);
                startActivity(intent3);
                break;
            case R.id.menuDanhSach:
                Intent intent5 = new Intent(MainActivity.this, DanhSachSinhVien.class);

                String tenmh5 = mh.getTenMonHoc();
                String tenlop5 = mh.getTenLop();
                Bundle bundle5 = new Bundle();
                bundle5.putString("tenmonhoc", tenmh5);
                bundle5.putString("tenlop", tenlop5);

                intent5.putExtras(bundle5);
                startActivity(intent5);

                break;
            case R.id.menuXoa:
                XacNhanXoa();
                break;
        }
        return super.onContextItemSelected(item);
    }

    public void XoaMonHoc()
    {
        String tenmh = mh.getTenMonHoc();
        String tenlop = mh.getTenLop();
        String sql = "DELETE FROM MonHoc WHERE TenMonHoc = '"+ tenmh +"' AND TenLop = '"+ tenlop +"'";
        String sql2 = "DELETE FROM LopHoc WHERE TenMonHoc = '"+ tenmh +"' AND TenLop = '"+ tenlop +"'";
        String sql3 = "DELETE FROM LichSuDiemDanh WHERE TenMonHoc = '"+ tenmh +"' AND TenLop = '"+ tenlop +"'";
        dtb.execSQL(sql);
        dtb.execSQL(sql2);
        dtb.execSQL(sql3);
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
                XoaMonHoc();
                finish();
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        aler.show();
    }
}