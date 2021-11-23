package com.example.sqlite_saveimage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ThemAnhActivity extends AppCompatActivity {
    Button btnAdd, btnCancel;
    EditText editTen, edtMota;
    ImageButton ibtnCam, ibtnFolder;
    ImageView imgHinh;

    int REQUEST_CODE_CAMERA = 123;
    int REQUEST_CODE_FOLDER = 567;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_anh);

        AnhXa();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Chuyển data imageview -> byte
                BitmapDrawable bitmapDrawable = (BitmapDrawable) imgHinh.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArray);
                byte[] hinhAnh = byteArray.toByteArray();

                MainActivity.database.INSERT_DOVAT(
                        editTen.getText().toString().trim(),
                        edtMota.getText().toString().trim(),
                        hinhAnh
                );
                Toast.makeText(ThemAnhActivity.this, "Đã thêm", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ThemAnhActivity.this,MainActivity.class));
            }
        });

        ibtnCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,REQUEST_CODE_CAMERA);
            }
        });
        ibtnFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,REQUEST_CODE_FOLDER);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data != null){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgHinh.setImageBitmap(bitmap);
        }
        if(requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgHinh.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void AnhXa(){
        btnAdd = (Button) findViewById(R.id.btn_add);
        btnCancel = (Button) findViewById(R.id.btn_huy);
        editTen = (EditText) findViewById(R.id.edt_ten);
        edtMota = (EditText) findViewById(R.id.edt_mota);
        imgHinh = (ImageView) findViewById(R.id.imgv_hinh);
        ibtnCam = (ImageButton) findViewById(R.id.img_cam);
        ibtnFolder = (ImageButton) findViewById(R.id.img_folder);
    }

}