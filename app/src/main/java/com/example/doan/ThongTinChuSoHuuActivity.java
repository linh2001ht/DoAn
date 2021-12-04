package com.example.doan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ThongTinChuSoHuuActivity extends AppCompatActivity {
    private EditText cshName, cshNgaySinh, cshPhone, cshAddress;
    private RadioButton radioButtonMale;
    private int id;
    private AppDatabase appDatabase;
    private AppDao appDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Thông tin chủ sở hữu");
        setContentView(R.layout.activity_thong_tin_chu_so_huu);
        appDatabase = AppDatabase.getInstance(this);
        appDao = appDatabase.appDao();
        cshName = findViewById(R.id.csh_name);
        radioButtonMale = findViewById(R.id.radioButton_male);
        cshNgaySinh = findViewById(R.id.csh_date);
        cshPhone = findViewById(R.id.csh_phone);
        cshAddress = findViewById(R.id.csh_address);

        Intent receive = getIntent();
        ChuSoHuu chuSoHuu = (ChuSoHuu) receive.getSerializableExtra("data");
        if (chuSoHuu != null){
            this.setTitle("Sửa chủ sở hữu");
            id = chuSoHuu.getIDchusohuu();
            Log.d("qq", "qq"+id);
            cshName.setText(chuSoHuu.getName());
            radioButtonMale.setChecked(chuSoHuu.getGioitinh());
            cshNgaySinh.setText(chuSoHuu.getNgaysinh());
            cshPhone.setText(chuSoHuu.getPhone());
            cshAddress.setText(chuSoHuu.getAddress());
        } else {
            this.setTitle("Thêm chủ sở hữu");
        }

    }

    public void sendData(){
        String name = cshName.getText().toString();
        boolean gioitinh = radioButtonMale.isChecked();
        String ngaysinh = cshNgaySinh.getText().toString();
        String phone = cshPhone.getText().toString();
        String address = cshAddress.getText().toString();
        ChuSoHuu chuSoHuu = new ChuSoHuu(id, name,gioitinh, ngaysinh,phone, address);
        Intent returnIntent = new Intent();
        returnIntent.putExtra("data", chuSoHuu);
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