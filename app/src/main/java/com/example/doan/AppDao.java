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

    //@Query("SELECT * FROM ChuSoHuu where IDchusohuu(:id)")
    //public ChuSoHuu findByIDChuSoHuu(int id);
    @Insert
    public void insertChuSoHuu(ChuSoHuu... chuSoHuus);

    @Delete
    public void deleteChuSoHuu(ChuSoHuu chuSoHuu);



    //QUERY BANG BienSoXe

    @Query("select * from BienSoXe")
    public List<BienSoXe> getAllBienSoXe();

    @Query("select * from biensoxe where maBSX(:maBSX) limit 1")
    public BienSoXe findBymaBSX(String maBSX);

    @Insert
    public void insertBienSoXe(BienSoXe... bienSoXes);

    @Delete
    public void deleteBienSoXe(BienSoXe bienSoXe);



    //QUERY BANG LichSuVaoRa

    @Query("select * from LichSuVaoRa")
    public List<LichSuVaoRa> getAllLichSu();

    @Insert
    public void insertLichSu(LichSuVaoRa... lichSuVaoRas);

    @Delete
    public void deleteLichSu(LichSuVaoRa lichSuVaoRa);
}
