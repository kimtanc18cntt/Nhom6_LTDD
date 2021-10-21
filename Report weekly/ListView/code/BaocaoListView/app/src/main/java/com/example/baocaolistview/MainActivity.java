package com.example.baocaolistview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button B1,B2,B3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        B1 = (Button) findViewById(R.id.b1);
        B3 = (Button) findViewById(R.id.b3);
        B2 = (Button) findViewById(R.id.b2);
        B1.setOnClickListener((view) -> {
            Intent i = new Intent(getApplicationContext(),bai1.class);
            startActivity(i);
        });
        B2.setOnClickListener((view) -> {
            Intent i = new Intent(getApplicationContext(),bai2.class);
            startActivity(i);
        });
        B3.setOnClickListener((view) -> {
            Intent i = new Intent(getApplicationContext(),bai3.class);
            startActivity(i);
        });
    }
    public boolean onCreateOptionMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        return true;
    }

}