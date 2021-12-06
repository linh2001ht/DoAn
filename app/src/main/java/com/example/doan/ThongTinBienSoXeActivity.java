package com.example.doan;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ThongTinBienSoXeActivity extends AppCompatActivity {
    private Spinner bsxLoaiXe, bsxIdCSH;
    private EditText bsxMaBSX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Thông tin biển số xe");
        setContentView(R.layout.activity_thong_tin_bien_so_xe);
        AppDatabase appDatabase = AppDatabase.getInstance(this);
        AppDao appDao = appDatabase.appDao();
        bsxMaBSX = findViewById(R.id.bsx_maBSX);
        bsxIdCSH = findViewById(R.id.bsx_idCHS);
        ArrayAdapter<ChuSoHuu> adapter = new ArrayAdapter<>(this,
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
            bsxLoaiXe.setSelection(bienSoXe.getLoaixe());
            bsxMaBSX.setText(bienSoXe.getMaBSX());
        } else {
            this.setTitle("Thêm biển số xe");
        }

    }

    public void sendData(){
        String maBSX = bsxMaBSX.getText().toString();
        int idCSH = (int) bsxIdCSH.getSelectedItemId()+1;
        int loaixe =(int) bsxLoaiXe.getSelectedItemId();
        BienSoXe bienSoXe = new BienSoXe(maBSX, loaixe, idCSH);
        Intent returnIntent = new Intent();
        returnIntent.putExtra("data", bienSoXe);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu) {
        getMenuInflater().inflate( R.menu.menu2, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_Ok:
                sendData();
                return true;
            case R.id.action_Cancel:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}