package com.example.doan;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {@ForeignKey(entity = ChuSoHuu.class,
        parentColumns = "IDChuSoHuu",
        childColumns = "IDChuSoHuu",
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE)
})
public class BienSoXe {
    @PrimaryKey(autoGenerate = true)
    private String  maBSX;

    @ColumnInfo
    private int IDChuSoHuu;

    public BienSoXe(String maBSX, int IDChuSoHuu) {
        this.maBSX = maBSX;
        this.IDChuSoHuu = IDChuSoHuu;
    }

    public String maBSX() {
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
}
