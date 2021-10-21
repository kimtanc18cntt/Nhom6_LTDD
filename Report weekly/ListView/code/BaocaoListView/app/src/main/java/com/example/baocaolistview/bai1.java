package com.example.baocaolistview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class bai1 extends AppCompatActivity {
    EditText addten;
    Button btnthem, btncapnhap, btnxoa, Trove  ;
    ListView lvmh;
    ArrayList<String> mangMH;
    int vitri= 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_bai1);
        lvmh = (ListView) findViewById(R.id.lvmonhoc);
        mangMH = new ArrayList<String>();
        addten = (EditText) findViewById(R.id.them);
        btnthem = (Button) findViewById(R.id.btchon);
        btncapnhap = (Button) findViewById(R.id.btcn);
        btnxoa = (Button) findViewById(R.id.btxoa);
        Trove = (Button) findViewById(R.id.bttrove);
        mangMH.add("Việt Nam");
        mangMH.add("Đức");
        mangMH.add("Pháp");
        mangMH.add("Mỹ");
        mangMH.add("Bồ Đào Nha");
        mangMH.add("Tây Ban Nha");

        ArrayAdapter adapter = new ArrayAdapter(
                bai1.this,
                android.R.layout.simple_list_item_1,
                mangMH
        );
        lvmh.setAdapter(adapter);
        Trove.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });

        lvmh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                Toast.makeText(bai1.this, mangMH.get(i)+ " vi tri " + i, Toast.LENGTH_SHORT).show();
            }
        });
        lvmh.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(bai1.this, " Ban chọn "+ mangMH.get(i),Toast.LENGTH_SHORT ).show();
                return false;
            }
        });
        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ten = addten.getText().toString();
                mangMH.add(ten);
                adapter.notifyDataSetChanged();
                addten.setText("");
            }
        });
        lvmh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                addten.setText(mangMH.get(i));
                vitri = i;
            }
        });
        btncapnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String t= addten.getText().toString();
                mangMH.set(vitri, t);
                adapter.notifyDataSetChanged();
                addten.setText("");
            }
        });


        lvmh.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(
                        bai1.this,
                        "Ban da xoa" +mangMH.get(i),
                        Toast.LENGTH_SHORT
                ).show();

                mangMH.remove(i);
                adapter.notifyDataSetChanged();
                return false;
            }
        });
        btnxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String t= addten.getText().toString();
                mangMH.remove(t);
                adapter.notifyDataSetChanged();
                addten.setText("");
            }
        });
    }
}