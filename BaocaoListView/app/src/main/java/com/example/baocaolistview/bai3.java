package com.example.baocaolistview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class bai3 extends AppCompatActivity {
    ListView lvMonan;
    ArrayList<Monan> mangmonan;
    Button Trove;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_bai3);
        lvMonan = (ListView)  findViewById(R.id.listmonan);
        Trove = (Button) findViewById(R.id.bttrove);
        Trove.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });
        mangmonan = new ArrayList<Monan>();

        mangmonan.add(new Monan("Cua hoàng đế",3000000,R.drawable.cua));
        mangmonan.add(new Monan("Tôm Hùm Alaska",2500000,R.drawable.tom));
        mangmonan.add(new Monan("Mực ",3000000,R.drawable.muc));
        mangmonan.add(new Monan("Hàu nướng",500000,R.drawable.hau));
        mangmonan.add(new Monan("Cá lóc chiên  ",3000000,R.drawable.caloc));

        MonanAdapter MAadpter = new MonanAdapter(
                bai3.this,
                R.layout.dong_mon_an,
                mangmonan
        );
        //do ra listview
        lvMonan.setAdapter(MAadpter);
    }
}