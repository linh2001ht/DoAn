package com.example.doan;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;

import java.util.Date;
import java.util.List;

@Dao
public interface AppDao {

    //QUERY BANG ChuSoHuu
    @Query("SELECT * FROM ChuSoHuu")
    public List<ChuSoHuu> getAllChuSoHuu();

    @Query("select * from ChuSoHuu where name like '%' || :name || '%'")
    public List<ChuSoHuu> getChuSoHuus(String name);

    @Query("select * from ChuSoHuu where IDchusohuu =:IDchusohuu limit 1")
    public ChuSoHuu findCSHbyID(int IDchusohuu);

    @Insert
    public void insertChuSoHuu(ChuSoHuu... chuSoHuus);

    @TypeConverters({DateConverter.class})
    @Query("UPDATE ChuSoHuu SET name=:name, gioitinh=:gioitinh, " +
            "ngaysinh=:ngaysinh, phone=:phone, address=:address, anhcccd=:anhcccd  WHERE IDchusohuu =:id")
    public void updateChuSoHuu(int id, String name, boolean gioitinh, Date ngaysinh, String phone, String address, byte[] anhcccd);

    @Delete
    public void deleteChuSoHuu(ChuSoHuu chuSoHuu);



    //QUERY BANG BienSoXe

    @Query("select * from BienSoXe")
    public List<BienSoXe> getAllBienSoXe();

    @Query("select * from BienSoXe where maBSX like '%' || :maBSX || '%'")
    public List<BienSoXe> getBienSoXes(String maBSX);

    @Query("select * from biensoxe where maBSX=:maBSX limit 1")
    public BienSoXe findBymaBSX(String maBSX);

    @Query("UPDATE BienSoXe SET loaixe=:loaixe, IDChuSoHuu=:IDChuSoHuu  WHERE maBSX =:maBSX")
    public void updateBienSoXe(String maBSX, int loaixe, int IDChuSoHuu);
    @Insert
    public void insertBienSoXe(BienSoXe... bienSoXes);

    @Delete
    public void deleteBienSoXe(BienSoXe bienSoXe);



    //QUERY BANG LichSuVaoRa

    @Query("select * from LichSuVaoRa")
    public List<LichSuVaoRa> getAllLichSu();

    @Query("select * from LichSuVaoRa where maBSX like '%' || :maBSX || '%'")
    public List<LichSuVaoRa> getLichSus(String maBSX);

    @Insert
    public void insertLichSu(LichSuVaoRa... lichSuVaoRas);

    @Delete
    public void deleteLichSu(LichSuVaoRa lichSuVaoRa);

    @Query("select * from LichSuVaoRa where tgianvao>=:tgvao and tgianra<=:tgra")
    public LichSuVaoRa findLSByTime(String tgvao,String tgra);

    @TypeConverters({TimestampConverter.class})
    @Query("UPDATE LichSuVaoRa SET maBSX=:maBSX, giave=:giave, anhBSXvao=:anhBSXvao, " +
            "tgianvao=:tgianvao, anhBSXra=:anhBSXra ,tgianra=:tgianra WHERE ID =:ID")
    public void updateLichSu(int ID, String maBSX, int giave, byte[] anhBSXvao, Date tgianvao, byte[] anhBSXra, Date tgianra);
}
