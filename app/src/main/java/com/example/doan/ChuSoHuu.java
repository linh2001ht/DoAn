package com.example.doan;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class ChuSoHuu {

    @PrimaryKey(autoGenerate = true)
    private int IDchusohuu;

    @ColumnInfo
    private String name;

    @ColumnInfo
    private Boolean gioitinh;

    @ColumnInfo
    private String ngaysinh;

    @ColumnInfo
    private String phone;

    @ColumnInfo
    private String address;

    @ColumnInfo
    private Byte[] anhcccd;

    public ChuSoHuu(int IDchusohuu, String name, Boolean gioitinh, String ngaysinh, String phone, String address, Byte[] anhcccd) {
        this.IDchusohuu = IDchusohuu;
        this.name = name;
        this.gioitinh = gioitinh;
        this.ngaysinh = ngaysinh;
        this.phone = phone;
        this.address = address;
        this.anhcccd = anhcccd;
    }

    public int getIDchusohuu() {
        return IDchusohuu;
    }

    public void setIDchusohuu(int IDchusohuu) {
        this.IDchusohuu = IDchusohuu;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(Boolean gioitinh) {
        this.gioitinh = gioitinh;
    }

    public String getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(String ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Byte[] getAnhcccd() {
        return anhcccd;
    }

    public void setAnhcccd(Byte[] anhcccd) {
        this.anhcccd = anhcccd;
    }
}
