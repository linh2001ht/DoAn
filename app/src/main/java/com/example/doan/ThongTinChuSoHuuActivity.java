package com.example.doan;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.annotations.Nullable;

import java.io.ByteArrayOutputStream;
import java.util.Date;

public class ThongTinChuSoHuuActivity extends AppCompatActivity {
    public static final int CSH_CAMERA_REQUEST_CODE = 999;
    public static final int CSH_GALLERY_REQUEST_CODE = 888;
    private EditText cshName, cshNgaySinh, cshPhone, cshAddress;
    private RadioButton radioButtonMale, radioButtonFemale;
    private int id;
    private ImageView cshImage;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Thông tin chủ sở hữu");
        setContentView(R.layout.activity_thong_tin_chu_so_huu);
        cshName = findViewById(R.id.csh_name);
        radioButtonMale = findViewById(R.id.radioButton_male);
        radioButtonFemale = findViewById(R.id.radioButton_female);
        cshNgaySinh = findViewById(R.id.csh_date);
        cshPhone = findViewById(R.id.csh_phone);
        cshAddress = findViewById(R.id.csh_address);
        cshImage = findViewById(R.id.csh_image);



        Intent receive = getIntent();
        ChuSoHuu chuSoHuu = (ChuSoHuu) receive.getSerializableExtra("data");
        if (chuSoHuu != null){
            this.setTitle("Sửa chủ sở hữu");
            id = chuSoHuu.getIDchusohuu();
            cshName.setText(chuSoHuu.getName());
            if (chuSoHuu.getGioitinh()) radioButtonMale.setChecked(true);
            else radioButtonFemale.setChecked(true);
            cshNgaySinh.setText(DateConverter.toString(chuSoHuu.getNgaysinh()));
            cshPhone.setText(chuSoHuu.getPhone());
            cshAddress.setText(chuSoHuu.getAddress());
            byte[] byteArray = chuSoHuu.getAnhcccd();
            if (byteArray != null) {
                Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                cshImage.setImageBitmap(bmp);
            }
        } else {
            this.setTitle("Thêm chủ sở hữu");
        }

        cshImage.setOnClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(ThongTinChuSoHuuActivity.this, view);
            popupMenu.inflate(R.menu.popup_menu);
            popupMenu.setOnMenuItemClickListener(menuItem -> {
                switch (menuItem.getItemId()) {
                    case R.id.open_camera:
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, CSH_CAMERA_REQUEST_CODE);
                        return true;
                    case R.id.open_gallery:
                        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(gallery, CSH_GALLERY_REQUEST_CODE);
                        return true;
                }
                return false;
            });
            popupMenu.show();
        });
    }

    public void sendData(){
        String name = cshName.getText().toString();
        boolean gioitinh = radioButtonMale.isChecked();
        Date ngaysinh = DateConverter.toDate(cshNgaySinh.getText().toString());
        String phone = cshPhone.getText().toString();
        String address = cshAddress.getText().toString();
        byte[] anh = imageViewToByte(cshImage);
        ChuSoHuu chuSoHuu = new ChuSoHuu(id, name,gioitinh, ngaysinh,phone, address, anh);
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

    public static byte[] imageViewToByte(ImageView image) {
        try {
            Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            return stream.toByteArray();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CSH_CAMERA_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                cshImage.setImageBitmap(bitmap);
            }

        }

        if (requestCode == CSH_GALLERY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri contentUri = data.getData();
                cshImage.setImageURI(contentUri);
            }
        }

    }

}