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
    private String tgianvao;

    @ColumnInfo
    private String tgianra;

    @ColumnInfo
    private Byte[] anhBSX;

    public LichSuVaoRa(int ID, String maBSX, String tgianvao, String tgianra, Byte[] anhBSX) {
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

    public String getTgianvao() {
        return tgianvao;
    }

    public void setTgianvao(String tgianvao) {
        this.tgianvao = tgianvao;
    }

    public String getTgianra() {
        return tgianra;
    }

    public void setTgianra(String tgianra) {
        this.tgianra = tgianra;
    }

    public Byte[] getAnhBSX() {
        return anhBSX;
    }

    public void setAnhBSX(Byte[] anhBSX) {
        this.anhBSX = anhBSX;
    }
}
