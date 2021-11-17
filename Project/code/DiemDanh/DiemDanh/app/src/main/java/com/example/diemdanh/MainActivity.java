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

            case R.id.menuDanhSach:
                Intent intent1 = new Intent(MainActivity.this, DanhSachSinhVien.class);

                String tenmh1 = mh.getTenMonHoc();
                String tenlop1 = mh.getTenLop();
                Bundle bundle1 = new Bundle();
                bundle1.putString("tenmonhoc", tenmh1);
                bundle1.putString("tenlop", tenlop1);

                intent1.putExtras(bundle1);
                startActivity(intent1);

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
        dtb.execSQL(sql);
        dtb.execSQL(sql2);
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