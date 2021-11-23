package com.example.sqlite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Database database;
    ListView lvCongViec;
    ArrayList<CongViec> arrayCongViec;
    CongViecAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

         lvCongViec= (ListView) findViewById(R.id.listcv);
        arrayCongViec = new ArrayList<>();

        adapter = new CongViecAdapter(this, R.layout.dong_cong_viec ,arrayCongViec);
        lvCongViec.setAdapter(adapter);

        // tao database ghichu
        database= new Database(this, "ghichu.sqlite", null, 1);

        // tao bang cong viec
        database.QueryData("CREATE TABLE IF NOT EXISTS CongViec(Id INTEGER PRIMARY KEY AUTOINCREMENT, TenCV VARCHAR(200))");// tao bang neu bang chua ton tai

        // INSERT DATA
        //database.QueryData("INSERT INTO CongViec VALUES (null,'lam bai tap')");
        //database.QueryData("INSERT INTO CongViec VALUES (null,'ngu trua')");
        // select data
        getdatacongviec();
    }

    public void DialogCoaCV(String tencv, int id ){
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(this);
        dialogXoa.setMessage(" Bạn có muốn xoá công việc "+ tencv +"không?");
        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                database.QueryData("DELETE FROM CongViec WHERE Id='"+id+"'");
                Toast.makeText(MainActivity.this, "Đã xoá"+ tencv, Toast.LENGTH_SHORT).show();
                getdatacongviec();
            }
        });
        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialogXoa.show();

    }

    public void Dialogsuacv(String ten, int id){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_suacv);

        EditText editTencv = (EditText) dialog.findViewById(R.id.suaTenCV);
        Button btnxacnhan = (Button) dialog.findViewById(R.id.btok) ;
        Button btHuy = (Button) dialog.findViewById(R.id.bthuy) ;

        editTencv.setText(ten);

        btHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenMoi = editTencv.getText().toString().trim();
                database.QueryData("UPDATE CongViec SET TenCV='"+tenMoi+"' WHERE Id='"+id+"'");
                Toast.makeText(MainActivity.this, "Đã cập nhập", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                getdatacongviec();
            }
        });

        dialog.show();
    }

    private void getdatacongviec(){
        Cursor dataCongViec = database.GetData("SELECT * FROM CongViec");
        arrayCongViec.clear();
        while (dataCongViec.moveToNext()){
            String ten = dataCongViec.getString(1);
            int id = dataCongViec.getInt(0);
            arrayCongViec.add(new CongViec(id, ten));

        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_congviec, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menuthem){
            DialogThem();
        }

        return super.onOptionsItemSelected(item);
    }
    private void DialogThem(){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_themcv);

        EditText editTen = (EditText) dialog.findViewById(R.id.editTenCV);
        Button btnthem = (Button) dialog.findViewById(R.id.btthem) ;
        Button btnhuy = (Button) dialog.findViewById(R.id.bthuy) ;

        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tencv = editTen.getText().toString();
                if(tencv.equals("")){
                    Toast.makeText(MainActivity.this, "Vui lòng nhập tên công việc", Toast.LENGTH_SHORT).show();
                }else{
                    database.QueryData("INSERT INTO CongViec VALUES (null,'" +tencv+ "')");
                    Toast.makeText(MainActivity.this, "Đã thêm", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    getdatacongviec();
                }
            }
        });

        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}