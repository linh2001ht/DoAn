package com.example.doan;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(foreignKeys = {@ForeignKey(entity = ChuSoHuu.class,
        parentColumns = "IDchusohuu",
        childColumns = "IDChuSoHuu",
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE)
})
public class BienSoXe implements Serializable {
    @PrimaryKey
    @NonNull
    private String  maBSX;

    @ColumnInfo
    private int IDChuSoHuu;

    public BienSoXe(String maBSX, int IDChuSoHuu) {
        this.maBSX = maBSX;
        this.IDChuSoHuu = IDChuSoHuu;
    }

    public String getMaBSX() {
        return maBSX;
    }

    public void setMaBSX(String maBSX) {
        this.maBSX = maBSX;
    }

    public int getIDChuSoHuu() {
        return IDChuSoHuu;
    }

    public void setIDChuSoHuu(int IDChuSoHuu) {
        this.IDChuSoHuu = IDChuSoHuu;
    }

    @Override
    public String toString() {
        return this.maBSX;
    }
}
