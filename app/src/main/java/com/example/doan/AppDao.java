package com.example.doan;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AppDao {

    //QUERY BANG ChuSoHuu
    @Query("SELECT * FROM ChuSoHuu")
    public List<ChuSoHuu> getAllChuSoHuu();

    @Insert
    public void insertChuSoHuu(ChuSoHuu... chuSoHuus);

    @Query("UPDATE ChuSoHuu SET name=:name, gioitinh=:gioitinh, " +
            "ngaysinh=:ngaysinh, phone=:phone, address=:address  WHERE IDchusohuu =:id")
    public void updateChuSoHuu(int id, String name, boolean gioitinh, String ngaysinh, String phone, String address);

    @Delete
    public void deleteChuSoHuu(ChuSoHuu chuSoHuu);



    //QUERY BANG BienSoXe

    @Query("select * from BienSoXe")
    public List<BienSoXe> getAllBienSoXe();

    @Query("select * from biensoxe where maBSX=:maBSX limit 1")
    public BienSoXe findBymaBSX(String maBSX);

    @Query("UPDATE BienSoXe SET IDChuSoHuu=:IDChuSoHuu  WHERE maBSX =:maBSX")
    public void updateBienSoXe(String maBSX, int IDChuSoHuu);
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

    @Query("UPDATE LichSuVaoRa SET maBSX=:maBSX, tgianvao=:tgianvao, " +
            "tgianra=:tgianra WHERE ID =:ID")
    public void updateLichSu(int ID, String maBSX, String tgianvao, String tgianra);
}
