package com.example.doan;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {
    private Button btn1, btn2, btn3, btn4, btn5;

//    private AppDatabase appDatabase;
//    private AppDao appDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1 = (Button)findViewById(R.id.btn1);
        btn2 = (Button)findViewById(R.id.btn2);
        btn3 = (Button)findViewById(R.id.btn3);
        btn4 = (Button)findViewById(R.id.btn4);
        btn5 = (Button)findViewById(R.id.btn5);


//        appDatabase = AppDatabase.getInstance(this);
//        appDao = appDatabase.appDao();

//        AsyncTask.execute(new Runnable() {
//            @Override
//            public void run() {
//                appDao.insertChuSoHuu(new ChuSoHuu(1,"NVA",true,"14/12/1999","0123456789","Quang Nam",""));
//                appDao.insertChuSoHuu(new ChuSoHuu(2,"LTB",false,"31/01/1972","0154896574","Quang Nam",""));
//                appDao.insertChuSoHuu(new ChuSoHuu(3,"NVB",false,"13/11/1985","0256145896","Da Nang",""));
//                appDao.insertChuSoHuu(new ChuSoHuu(4,"HTK",true,"28/02/19996","0236541257","Hai Phong",""));
//                appDao.insertChuSoHuu(new ChuSoHuu(5,"HDA",false,"16/09/2000","0159874532","Quang Tri",""));
//                appDao.insertChuSoHuu(new ChuSoHuu(6,"NMQ",true,"07/02/1967","0125478965","Hue",""));
//                appDao.insertChuSoHuu(new ChuSoHuu(7,"LHV",true,"15/10/1999","0145478963","Hue",""));
//                appDao.insertChuSoHuu(new ChuSoHuu(8,"PTH",true,"04/12/1955","0125476540","Binh Dinh",""));
//                appDao.insertChuSoHuu(new ChuSoHuu(9,"PQL",false,"09/06/1954","0212546390","Nghe An",""));
//                appDao.insertChuSoHuu(new ChuSoHuu(10,"PHMT",true,"17/12/1964","0154789654","Ha Tinh",""));
//                appDao.insertChuSoHuu(new ChuSoHuu(11,"BDP",false,"01/11/1979","0542614875","Quang Nam",""));
//                appDao.insertChuSoHuu(new ChuSoHuu(12,"HHP",false,"15/07/1992","0954231658","Da Nang",""));
//                appDao.insertChuSoHuu(new ChuSoHuu(13,"PMT",false,"12/12/1991","0154789654","Quang Nam",""));
//
//                appDao.insertBienSoXe(new BienSoXe("74-F1 177.95",1));
//                appDao.insertBienSoXe(new BienSoXe("75-G1 422.33",8));
//                appDao.insertBienSoXe(new BienSoXe("74-F1 362.95",2));
//                appDao.insertBienSoXe(new BienSoXe("74-F1 278.88",4));
//                appDao.insertBienSoXe(new BienSoXe("48-H1 080.78",3));
//                appDao.insertBienSoXe(new BienSoXe("74-F1 181.56",6));
//                appDao.insertBienSoXe(new BienSoXe("74-F1 230.88",4));
//                appDao.insertBienSoXe(new BienSoXe("74-G1 397.37",5));
//                appDao.insertBienSoXe(new BienSoXe("74-H1 568.64",9));
//                appDao.insertBienSoXe(new BienSoXe("74-AF 010.51",1));
//
//            }
//        });


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NhanDienBienSoXeActivity.class);
                startActivity(intent);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, QuanLyBienSoXeActivity.class);
                startActivity(intent);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, QuanLyChuSoHuuActivity.class);
                startActivity(intent);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, QuanLyLichSuActivity.class);
                startActivity(intent);
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, QuanLyDoanhThuActivity.class);
                startActivity(intent);
            }
        });
    }


}