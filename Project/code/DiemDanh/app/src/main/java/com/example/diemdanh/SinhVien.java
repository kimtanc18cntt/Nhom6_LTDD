package com.example.diemdanh;

public class SinhVien
{
    private String tenSinhVien;
    private String maSinhVien;
    private String Lop;

    public void SinhVien()
    {
        this.tenSinhVien = "";
        this.maSinhVien = "";
        this.Lop = "";
    }
    public void SinhVien(String tenSinhVien, String maSinhVien, String Lop)
    {
        this.tenSinhVien = tenSinhVien;
        this.maSinhVien = maSinhVien;
        this.Lop = Lop;
    }
    public String getTenSinhVien() {
        return tenSinhVien;
    }

    public void setTenSinhVien(String tenSinhVien) {
        this.tenSinhVien = tenSinhVien;
    }

    public String getMaSinhVien() {
        return maSinhVien;
    }

    public void setMaSinhVien(String maSinhVien) {
        this.maSinhVien = maSinhVien;
    }

    public String getLop() {
        return Lop;
    }

    public void setLop(String lop) {
        Lop = lop;
    }
}
