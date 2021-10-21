package com.example.baocaolistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MonanAdapter extends BaseAdapter {

        Context myContext;
        int myLayout;
        List<Monan> ArrayMonan;
        public MonanAdapter(Context context, int layout, List<Monan> monanList){
            myContext = context;
            myLayout = layout;
            ArrayMonan = monanList;
        }

    @Override
    public int getCount() {
        return ArrayMonan.size();
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
        LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(myLayout,null);

        TextView txtTen = (TextView) view.findViewById(R.id.tenmon);
        txtTen.setText(ArrayMonan.get(i).Ten);

        TextView txtGia = (TextView)  view.findViewById(R.id.gia);
        txtGia.setText(ArrayMonan.get(i).Gia +"");

        ImageView imgHinh = (ImageView) view.findViewById(R.id.hinh);
        imgHinh.setImageResource(ArrayMonan.get(i).Hinh);

        return view;
    }
}
