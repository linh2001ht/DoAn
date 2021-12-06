package com.example.doan;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(foreignKeys = {@ForeignKey(entity = ChuSoHuu.class,
        parentColumns = "IDchusohuu",
        childColumns = "IDChuSoHuu",
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE)
},
        indices = {@Index("IDChuSoHuu")})
public class BienSoXe implements Serializable {
    @PrimaryKey
    @NonNull
    private String  maBSX;

    @ColumnInfo
    private int loaixe;

    @ColumnInfo
    private int IDChuSoHuu;

    public BienSoXe(@NonNull String maBSX, int loaixe, int IDChuSoHuu) {
        this.maBSX = maBSX;
        this.loaixe = loaixe;
        this.IDChuSoHuu = IDChuSoHuu;
    }

    @NonNull
    public String getMaBSX() {
        return maBSX;
    }

    public void setMaBSX(@NonNull String maBSX) {
        this.maBSX = maBSX;
    }

    public int getLoaixe() {
        return loaixe;
    }

    public void setLoaixe(int loaixe) {
        this.loaixe = loaixe;
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
