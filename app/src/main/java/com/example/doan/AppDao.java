package com.example.doan;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AppDao {

    //QUERY BANG ChuSoHuu
    @Query("SELECT * FROM ChuSoHuu")
    public List<ChuSoHuu> getAllChuSoHuu();

    @Query("SELECT * FROM ChuSoHuu where IDchusohuu=:id")
    public ChuSoHuu findCSHByIDChuSoHuu(int id);

    @Query("SELECT * FROM ChuSoHuu where name=:name")
    public ChuSoHuu findCSHByName(String name);

    @Insert
    public void insertChuSoHuu(ChuSoHuu... chuSoHuus);

    @Delete
    public void deleteChuSoHuu(ChuSoHuu chuSoHuu);



    //QUERY BANG BienSoXe

    @Query("select * from BienSoXe")
    public List<BienSoXe> getAllBienSoXe();

    @Query("select * from BienSoXe where maBSX=:maBSX")
    public BienSoXe findBSXBymaBSX(String maBSX);

    @Query("select * from BienSoXe where IDChuSoHuu=:id")
    public BienSoXe findBSXByIDchusohuu(int id);

    @Insert
    public void insertBienSoXe(BienSoXe... bienSoXes);

    @Delete
    public void deleteBienSoXe(BienSoXe bienSoXe);



    //QUERY BANG LichSuVaoRa

    @Query("select * from LichSuVaoRa")
    public List<LichSuVaoRa> getAllLichSu();

    @Query("select * from LichSuVaoRa where maBSX=:maBSX")
    public LichSuVaoRa findLSBymaBSX(String maBSX);

    @Query("select * from LichSuVaoRa where tgianvao>=:tgvao and tgianra<=:tgra")
    public LichSuVaoRa findLSByTime(String tgvao,String tgra);

    @Insert
    public void insertLichSu(LichSuVaoRa... lichSuVaoRas);

    @Delete
    public void deleteLichSu(LichSuVaoRa lichSuVaoRa);
}
