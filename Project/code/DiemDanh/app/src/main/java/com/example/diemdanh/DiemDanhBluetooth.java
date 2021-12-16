package com.example.diemdanh;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

public class DiemDanhBluetooth extends AppCompatActivity
{
    ListView lvdiemdanhbl;
    Button luu, trove, quet;
    TextView tenmonhoc, tenlop, ngay;
    private SQLiteDatabase dtb;
    public static final int REQUEST_ACCESS_COARSE_LOCATION = 1;
    public static final int REQUEST_ENABLE_BLUETOOTH = 11;
    private BluetoothAdapter bluetoothAdapter;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> arrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diem_danh_bluetooth);
        dtb = openOrCreateDatabase("DiemDanh.db", MODE_PRIVATE, null);
        AnhXa();
        TaoLichSuDiemDanh();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String str = df.format(new Date());
        ngay.setText(str);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null)
        {
            tenmonhoc.setText(bundle.getString("tenmonhoc", ""));
            tenlop.setText(bundle.getString("tenlop", ""));
        }
        trove.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                arrayAdapter.clear();
                finish();
            }
        });
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        lvdiemdanhbl.setAdapter(arrayAdapter);
        kiemTraTrangThaiBluetooth();
        luu.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                for (int i = 0;i<arrayAdapter.getCount();i++)
                {
                    String sql = "INSERT INTO LichSuDiemDanh (TenMonHoc, TenLop, Ngay, TenSinhVien) VALUES ('"+ tenmonhoc.getText().toString().trim() +"', '"+ tenlop.getText().toString().trim() +"','"+ ngay.getText().toString().trim() +"', '"+ arrayAdapter.getItem(i) +"')";
                    dtb.execSQL(sql);
                }
                Toast.makeText(DiemDanhBluetooth.this, "Đã lưu danh sách điểm danh", Toast.LENGTH_LONG).show();
                finish();
                Intent intent = new Intent(DiemDanhBluetooth.this, MainActivity.class);
                startActivity(intent);
            }
        });
        quet.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(bluetoothAdapter != null && bluetoothAdapter.isEnabled())
                {
                    if(kiemTraCoarseLocationPermission())
                    {

                        bluetoothAdapter.startDiscovery();

                    }
                }
                else
                {
                    kiemTraTrangThaiBluetooth();
                }
            }
        });
        kiemTraCoarseLocationPermission();
    }

    public void AnhXa()
    {
        lvdiemdanhbl = (ListView)findViewById(R.id.listDiemDanhBL);
        luu = (Button)findViewById(R.id.btnLuuBT);
        quet = (Button)findViewById(R.id.btnQuet);
        trove = (Button)findViewById(R.id.btnTroVeTuDiemDanhBL);
        tenmonhoc = (TextView)findViewById(R.id.txtMonHocBL);
        tenlop = (TextView)findViewById(R.id.txtTenLopBL);
        ngay = (TextView)findViewById(R.id.txtNgayBL);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter(); // khoi tao bo dieu hop blutool
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        registerReceiver(thietBiTimThay, new IntentFilter(BluetoothDevice.ACTION_FOUND));
        registerReceiver(thietBiTimThay, new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_STARTED));
        registerReceiver(thietBiTimThay, new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED));
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        unregisterReceiver(thietBiTimThay);
    }
    private boolean kiemTraCoarseLocationPermission()
    {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_ACCESS_COARSE_LOCATION);
            return false;
        }
        else
        {
            return true;
        }
    }
    private void kiemTraTrangThaiBluetooth()
    {
        if(bluetoothAdapter == null)
        {
            Toast.makeText(DiemDanhBluetooth.this, "Bluetooth không hỗ trợ trên thiết bị của bạn",
                    Toast.LENGTH_LONG).show();

        }
        else
        {
            if(bluetoothAdapter.isEnabled())
            {
                if(bluetoothAdapter.isDiscovering())
                {
                    Toast.makeText(DiemDanhBluetooth.this, "Đang tìm thiết bị...", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(DiemDanhBluetooth.this, "Bluetooth đã bật", Toast.LENGTH_LONG).show();
                    quet.setEnabled(true);
                }
            }
            else
            {
                Toast.makeText(DiemDanhBluetooth.this, "Bạn cần kích hoạt Bluetooth", Toast.LENGTH_LONG).show();
                Intent enableIntent = new Intent(bluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableIntent, REQUEST_ENABLE_BLUETOOTH);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_ENABLE_BLUETOOTH)
        {
            kiemTraTrangThaiBluetooth();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case REQUEST_ACCESS_COARSE_LOCATION:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(DiemDanhBluetooth.this, "Bạn có thể quét thiết bị Bluetooth", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(DiemDanhBluetooth.this, "Bạn không thể quét thiết bị Bluetooth", Toast.LENGTH_LONG).show();
                }
        }
    }
    private final BroadcastReceiver thietBiTimThay = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            String action = intent.getAction();
            if(BluetoothDevice.ACTION_FOUND.equals(action))
            {
                boolean th1 = false;
                BluetoothDevice Device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if(!(Device.getName().equals(null)))
                {
                    if(!(arrayAdapter.getCount() == 0))
                    {
                        for( int i=0; i< arrayList.size(); i++)
                        {
                            if(arrayList.get(i).equals(Device.getName()))
                            {
                                th1 = true;
                                break;
                            }
                            else
                                th1 = false;
                        }
                        if (th1 == false) {
                            arrayAdapter.add(Device.getName());//*
                            arrayList.add(Device.getName());//*
                        }
                    }
                    else
                    {
                        arrayAdapter.add(Device.getName());
                        arrayList.add(Device.getName());
                    }
                    //arrayAdapter.add(Device.getName() + "\n" + Device.getAddress());
                    arrayAdapter.notifyDataSetChanged();
                }
            }
            else if(bluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action))
            {
                quet.setText("Quét Bluetooth");
            }
            else if(bluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action))
            {
                quet.setText("Đang quét...");
            }
        }
    };
    public void TaoLichSuDiemDanh()
    {
        String sql = "CREATE TABLE IF NOT EXISTS LichSuDiemDanh (TenMonHoc text, TenLop text, Ngay text, TenSinhVien text, " +
                "MaSinhVien text, Lop text)";
        //STT integer primary key autoincrement
        dtb.execSQL(sql);
    }

}