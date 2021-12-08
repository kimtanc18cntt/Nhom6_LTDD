package com.example.diemdanh;

public class ThietBiKetNoi
{
    private String tenThietBi;
    private String diaChi;

    public String getTenThietBi() {
        return tenThietBi;
    }

    public void setTenThietBi(String tenThietBi) {
        this.tenThietBi = tenThietBi;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public ThietBiKetNoi(String tenThietBi, String diaChi)
    {
        this.tenThietBi = tenThietBi;
        this.diaChi = diaChi;
    }
}
