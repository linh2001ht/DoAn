package com.example.doan;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(foreignKeys = {@ForeignKey(entity = BienSoXe.class,
        parentColumns = "maBSX",
        childColumns = "maBSX",
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE)
})
public class LichSuVaoRa {
    @PrimaryKey(autoGenerate = true)
    private int ID;

    @ColumnInfo
    private String maBSX;

    @ColumnInfo
    private Date tgianvao;

    @ColumnInfo
    private Date tgianra;

    @ColumnInfo
    private String anhBSX;

    public LichSuVaoRa(int ID, String maBSX, Date tgianvao, Date tgianra, String anhBSX) {
        this.ID = ID;
        this.maBSX = maBSX;
        this.tgianvao = tgianvao;
        this.tgianra = tgianra;
        this.anhBSX = anhBSX;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getMaBSX() {
        return maBSX;
    }

    public void setMaBSX(String maBSX) {
        this.maBSX = maBSX;
    }

    public Date getTgianvao() {
        return tgianvao;
    }

    public void setTgianvao(Date tgianvao) {
        this.tgianvao = tgianvao;
    }

    public Date getTgianra() {
        return tgianra;
    }

    public void setTgianra(Date tgianra) {
        this.tgianra = tgianra;
    }

    public String getAnhBSX() {
        return anhBSX;
    }

    public void setAnhBSX(String anhBSX) {
        this.anhBSX = anhBSX;
    }
}
