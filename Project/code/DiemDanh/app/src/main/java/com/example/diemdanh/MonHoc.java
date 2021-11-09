package com.example.diemdanh;

public class MonHoc
{
    private String tenMonHoc;
    private String tenLop;
    private String ngay;

    public MonHoc()
    {
        this.tenMonHoc="";
        this.tenLop="";
        this.ngay="";
    }
    public MonHoc(String tenMonHoc, String tenLop, String ngay)
    {
        this.tenMonHoc = tenMonHoc;
        this.tenLop = tenLop;
        this.ngay=ngay;
    }

    public String getTenMonHoc() {
        return tenMonHoc;
    }

    public void setTenMonHoc(String tenMonHoc) {
        this.tenMonHoc = tenMonHoc;
    }

    public String getTenLop() {
        return tenLop;
    }

    public void setTenLop(String tenLop) {
        this.tenLop = tenLop;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }
}
