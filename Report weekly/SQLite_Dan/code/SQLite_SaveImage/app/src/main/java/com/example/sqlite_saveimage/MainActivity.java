package com.example.sqlite_saveimage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btnThem;
    ListView lvDoVat;
    ArrayList<DoVat> arrayDoVat;
    DovatAdapter adapter;
    public static Database database; //để cho hàm class khác gọi tới sd
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnThem =(Button) findViewById(R.id.btn_them);
        lvDoVat = (ListView) findViewById(R.id.lv_dovat);
        arrayDoVat = new ArrayList<>();

        adapter = new DovatAdapter(this, R.layout.dong_do_vat, arrayDoVat);
        lvDoVat.setAdapter(adapter);

        database = new Database(this, "Quanly.sqlite",null,1);

        database.QueryDatabase("CREATE TABLE IF NOT EXISTS DoVat(Id INTEGER PRIMARY KEY AUTOINCREMENT, Ten VARCHAR(150), MoTa VARCHAR(250),HinhAnh BLOB)");

        //get data
        Cursor cursor = database.GetData("SELECT * FROM DoVat");
        while (cursor.moveToNext()){
            arrayDoVat.add(new DoVat(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getBlob(3)
            ));
        }
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ThemAnhActivity.class));
            }
        });
    }
}