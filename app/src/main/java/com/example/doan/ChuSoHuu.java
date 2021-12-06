package com.example.doan;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.util.Date;

@Entity
public class ChuSoHuu implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int IDchusohuu;

    @ColumnInfo
    private String name;

    @ColumnInfo
    private Boolean gioitinh;

    @ColumnInfo
    @TypeConverters({DateConverter.class})
    private Date ngaysinh;

    @ColumnInfo
    private String phone;

    @ColumnInfo
    private String address;

    @ColumnInfo
    private byte[] anhcccd;

    public ChuSoHuu(int IDchusohuu, String name, Boolean gioitinh, Date ngaysinh, String phone, String address, byte[] anhcccd) {
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

    public Date getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(Date ngaysinh) {
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

    public byte[] getAnhcccd() {
        return anhcccd;
    }

    public void setAnhcccd(byte[] anhcccd) {
        this.anhcccd = anhcccd;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
