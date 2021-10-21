package com.example.baocaolistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class SinhVienAdapter extends BaseAdapter {

    Context myContext;
    int myLayout;
    List<SinhVien> arraySinhVien;

    public SinhVienAdapter(Context context, int layout, List<SinhVien> sinhvienList){
        myContext = context;
        myLayout = layout;
        arraySinhVien = sinhvienList;
    }
    @Override
    public int getCount() {
        return arraySinhVien.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater infater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = infater.inflate(myLayout, null);

        //anh xa va gan gia tri
        TextView txtHoTen = (TextView) view.findViewById(R.id.ht);
        txtHoTen.setText((arraySinhVien.get(i).Hoten));

        TextView txtNamSinh = (TextView) view.findViewById(R.id.ns);
        txtNamSinh.setText(String.valueOf(arraySinhVien.get(i).Namsinh));
        return view;
    }
}
