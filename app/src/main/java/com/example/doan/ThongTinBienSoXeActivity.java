package com.example.doan;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ThongTinBienSoXeActivity extends AppCompatActivity {
    private Spinner bsxLoaiXe, bsxIdCSH;
    private EditText bsxMaBSX;
    private boolean isEdit;
    private AppDatabase appDatabase;
    private AppDao appDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Thông tin biển số xe");
        setContentView(R.layout.activity_thong_tin_bien_so_xe);
        appDatabase = AppDatabase.getInstance(this);
        appDao = appDatabase.appDao();
        bsxMaBSX = findViewById(R.id.bsx_maBSX);
        bsxIdCSH = findViewById(R.id.bsx_idCHS);
        ArrayAdapter<ChuSoHuu> adapter = new ArrayAdapter<ChuSoHuu>(this,
                android.R.layout.simple_spinner_item,
                appDao.getAllChuSoHuu());

        // Layout for All ROWs of Spinner.  (Optional for ArrayAdapter).
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        bsxIdCSH.setAdapter(adapter);
        bsxLoaiXe = findViewById(R.id.bsx_loaixe);

        Intent receive = getIntent();
        BienSoXe bienSoXe = (BienSoXe) receive.getSerializableExtra("data");
        if (bienSoXe != null){
            this.setTitle("Sửa biển số xe");
            bsxIdCSH.setSelection(bienSoXe.getIDChuSoHuu()-1);
            bsxMaBSX.setText(bienSoXe.getMaBSX());
        } else {
            this.setTitle("Thêm biển số xe");
        }

    }

    public void sendData(){
        String maBSX = bsxMaBSX.getText().toString();
        int idCSH = (int) bsxIdCSH.getSelectedItemId()+1;
        int loaixe = (int) bsxLoaiXe.getSelectedItemId()+1;
        BienSoXe bienSoXe = new BienSoXe(maBSX, idCSH);
        Intent returnIntent = new Intent();
        returnIntent.putExtra("data", bienSoXe);
        Log.d("qq", "qqqq"+bienSoXe.getIDChuSoHuu());
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu) {
        getMenuInflater().inflate( R.menu.menu2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_Ok:
                sendData();
                break;
            case R.id.action_Cancel:
                this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}