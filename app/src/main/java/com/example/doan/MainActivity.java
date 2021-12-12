package com.example.doan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    private Button btn1, btn2, btn3, btn4;

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

//        appDatabase = AppDatabase.getInstance(this);
//        appDao = appDatabase.appDao();

//        AsyncTask.execute(new Runnable() {
//            @Override
//            public void run() {
//
//                appDao.insertChuSoHuu(new ChuSoHuu(1,"NVA",true, DateConverter.toDate("14/12/1999"),"0123456789","Quang Nam",null));
//                appDao.insertChuSoHuu(new ChuSoHuu(2,"LTB",false,DateConverter.toDate("31/01/1972"),"0154896574","Quang Nam",null));
//                appDao.insertChuSoHuu(new ChuSoHuu(3,"NVB",false,DateConverter.toDate("13/11/1985"),"0256145896","Da Nang",null));
//                appDao.insertChuSoHuu(new ChuSoHuu(4,"HTK",true,DateConverter.toDate("28/02/19996"),"0236541257","Hai Phong",null));
//                appDao.insertChuSoHuu(new ChuSoHuu(5,"HDA",false,DateConverter.toDate("16/09/2000"),"0159874532","Quang Tri",null));
//                appDao.insertChuSoHuu(new ChuSoHuu(6,"NMQ",true,DateConverter.toDate("07/02/1967"),"0125478965","Hue",null));
//                appDao.insertChuSoHuu(new ChuSoHuu(7,"LHV",true,DateConverter.toDate("15/10/1999"),"0145478963","Hue",null));
//                appDao.insertChuSoHuu(new ChuSoHuu(8,"PTH",true,DateConverter.toDate("04/12/1955"),"0125476540","Binh Dinh",null));
//                appDao.insertChuSoHuu(new ChuSoHuu(9,"PQL",false,DateConverter.toDate("09/06/1954"),"0212546390","Nghe An",null));
//                appDao.insertChuSoHuu(new ChuSoHuu(10,"PHMT",true,DateConverter.toDate("17/12/1964"),"0154789654","Ha Tinh",null));
//                appDao.insertChuSoHuu(new ChuSoHuu(11,"BDP",false,DateConverter.toDate("01/11/1979"),"0542614875","Quang Nam",null));
//                appDao.insertChuSoHuu(new ChuSoHuu(12,"HHP",false,DateConverter.toDate("15/07/1992"),"0954231658","Da Nang",null));
//                appDao.insertChuSoHuu(new ChuSoHuu(13,"PMT",false,DateConverter.toDate("12/12/1991"),"0154789654","Quang Nam",null));
//
//                appDao.insertBienSoXe(new BienSoXe("74-F1 177.95",1, 1));
//                appDao.insertBienSoXe(new BienSoXe("75-G1 422.33",0, 2));
//                appDao.insertBienSoXe(new BienSoXe("74-F1 362.95",1, 3));
//                appDao.insertBienSoXe(new BienSoXe("74-F1 278.88",0, 4));
//                appDao.insertBienSoXe(new BienSoXe("48-H1 080.78",1, 5));
//                appDao.insertBienSoXe(new BienSoXe("74-F1 181.56",0, 6));
//                appDao.insertBienSoXe(new BienSoXe("74-F1 230.88",1, 7));
//                appDao.insertBienSoXe(new BienSoXe("74-G1 397.37",0, 8));
//                appDao.insertBienSoXe(new BienSoXe("74-H1 568.64",1, 9));
//                appDao.insertBienSoXe(new BienSoXe("74-AF 010.51",1, 10));
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
    }


}