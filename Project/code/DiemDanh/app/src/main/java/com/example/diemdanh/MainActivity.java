package com.example.diemdanh;

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
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

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




    public void XoaMonHoc()
    {
        String tenmh = mh.getTenMonHoc();
        String tenlop = mh.getTenLop();
        String sql = "DELETE FROM MonHoc WHERE TenMonHoc = '"+ tenmh +"' AND TenLop = '"+ tenlop +"'";

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
                XoaMonHoc();
                finish();
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        aler.show();
    }
}