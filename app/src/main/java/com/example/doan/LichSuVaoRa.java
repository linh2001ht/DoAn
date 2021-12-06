package com.example.doan;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.util.Date;

@Entity(foreignKeys = {@ForeignKey(entity = BienSoXe.class,
        parentColumns = "maBSX",
        childColumns = "maBSX",
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE)
},
        indices = {@Index("maBSX")})
public class LichSuVaoRa implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int ID;

    @ColumnInfo
    private String maBSX;

    @ColumnInfo
    private int giave;

    @ColumnInfo
    private byte[] anhBSXvao;

    @ColumnInfo
    @TypeConverters({TimestampConverter.class})
    private Date tgianvao;

    @ColumnInfo
    private byte[] anhBSXra;

    @ColumnInfo
    @TypeConverters({TimestampConverter.class})
    private Date tgianra;

    public LichSuVaoRa(int ID, String maBSX, int giave, byte[] anhBSXvao, Date tgianvao, byte[] anhBSXra, Date tgianra) {
        this.ID = ID;
        this.maBSX = maBSX;
        this.giave = giave;
        this.anhBSXvao = anhBSXvao;
        this.tgianvao = tgianvao;
        this.anhBSXra = anhBSXra;
        this.tgianra = tgianra;
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

    public int getGiave() {
        return giave;
    }

    public void setGiave(int giave) {
        this.giave = giave;
    }

    public byte[] getAnhBSXvao() {
        return anhBSXvao;
    }

    public void setAnhBSXvao(byte[] anhBSXvao) {
        this.anhBSXvao = anhBSXvao;
    }

    public Date getTgianvao() {
        return tgianvao;
    }

    public void setTgianvao(Date tgianvao) {
        this.tgianvao = tgianvao;
    }

    public byte[] getAnhBSXra() {
        return anhBSXra;
    }

    public void setAnhBSXra(byte[] anhBSXra) {
        this.anhBSXra = anhBSXra;
    }

    public Date getTgianra() {
        return tgianra;
    }

    public void setTgianra(Date tgianra) {
        this.tgianra = tgianra;
    }

    @Override
    public String toString() {
        String tmp = this.maBSX;
        if (this.tgianvao != null) {
            tmp += " vào lúc " + TimestampConverter.toString(this.tgianvao);
            if (this.tgianra != null) tmp += " ra lúc " + TimestampConverter.toString(this.tgianra);
        }
        return tmp;
    }
}
