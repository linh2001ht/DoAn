package com.example.doan;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ThongTinLichSuActivity extends AppCompatActivity {

    public static final int CAMERA_REQUEST_CODE_IN = 102;
    public static final int GALLERY_REQUEST_CODE_IN = 103;
    public static final int CAMERA_REQUEST_CODE_OUT = 104;
    public static final int GALLERY_REQUEST_CODE_OUT = 105;
    private static byte[] imgIn, imgOut;
    private ImageView imageViewIn, imageViewOut;
    private String maBSX, idCSH;
    private int id;
    private EditText lsGiaVe, editTimeIn, editTimeOut;
    private Spinner lsLoaiXe, lsMaBSX;
    private Button btnCameraIn, btnGalleryIn, btnCameraOut, btnGalleryOut;
    private AppDatabase appDatabase;
    private AppDao appDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_lich_su);
        lsLoaiXe= findViewById(R.id.spinner);
        btnCameraIn = findViewById(R.id.btn_camera_in);
        btnGalleryIn = findViewById(R.id.btn_gallery_in);
        btnGalleryOut = findViewById(R.id.btn_gallery_out);
        btnCameraOut = findViewById(R.id.btn_camera_out);
        imageViewIn = findViewById(R.id.iv_bien_in);
        imageViewOut = findViewById(R.id.iv_bien_out);
        lsMaBSX = findViewById(R.id.ls_maBSX);
        lsGiaVe = findViewById(R.id.ls_giave);
        editTimeIn = findViewById(R.id.edit_time_in);
        editTimeOut = findViewById(R.id.edit_time_out);
        appDatabase = AppDatabase.getInstance(this);
        appDao = appDatabase.appDao();
        ArrayAdapter<BienSoXe> adapter = new ArrayAdapter<BienSoXe>(this,
                android.R.layout.simple_spinner_item,
                appDao.getAllBienSoXe());

        // Layout for All ROWs of Spinner.  (Optional for ArrayAdapter).
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        lsMaBSX.setAdapter(adapter);


        Intent receive = getIntent();
        LichSuVaoRa lichSuVaoRa = (LichSuVaoRa) receive.getSerializableExtra("data");
        if (lichSuVaoRa != null){
            this.setTitle("Sửa lịch sử");
            id = lichSuVaoRa.getID();
            int position = -1;
            for (LichSuVaoRa x:appDao.getAllLichSu()) {
                position += 1;
                if (x.getMaBSX() == lichSuVaoRa.getMaBSX()) break;
            }
            lsMaBSX.setSelection(position);
            editTimeIn.setText(lichSuVaoRa.getTgianvao());
            editTimeOut.setText(lichSuVaoRa.getTgianra());
//            byte[] imgIn = receive.getByteArrayExtra("imgIn");
//            if (imgIn != null){
//                Bitmap bitmap = BitmapFactory.decodeByteArray(imgIn,0,imgIn.length);
//                imageViewIn.setImageBitmap(bitmap);
//            }
//            byte[] imgOut = receive.getByteArrayExtra("imgOut");
//            if (imgIn != null){
//                Bitmap bitmap = BitmapFactory.decodeByteArray(imgOut,0,imgOut.length);
//                imageViewOut.setImageBitmap(bitmap);
//            }
        } else {
            this.setTitle("Thêm lịch sử");
        }

        btnCameraIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQUEST_CODE_IN);
            }
        });
        btnGalleryIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(gallery, GALLERY_REQUEST_CODE_IN);
            }
        });

        btnCameraOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQUEST_CODE_OUT);
            }
        });
        btnGalleryOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(gallery, GALLERY_REQUEST_CODE_OUT);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu) {
        getMenuInflater().inflate( R.menu.menu2, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE_IN) {
            if (resultCode == Activity.RESULT_OK) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                imageViewIn.setImageBitmap(bitmap);
                imgIn = imageViewToByte(imageViewIn);
            }

        }

        if (requestCode == GALLERY_REQUEST_CODE_IN) {
            if (resultCode == Activity.RESULT_OK) {
                Uri contentUri = data.getData();
                imageViewIn.setImageURI(contentUri);
                imgIn = imageViewToByte(imageViewIn);

            }
        }

        if (requestCode == CAMERA_REQUEST_CODE_OUT) {
            if (resultCode == Activity.RESULT_OK) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                imageViewOut.setImageBitmap(bitmap);
                imgOut = imageViewToByte(imageViewOut);
            }
        }

        if (requestCode == GALLERY_REQUEST_CODE_OUT) {
            if (resultCode == Activity.RESULT_OK) {
                Uri contentUri = data.getData();
                imageViewOut.setImageURI(contentUri);
                imgOut = imageViewToByte(imageViewOut);
            }
        }


    }

    public static byte[] imageViewToByte(ImageView image) {
        try {
            Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            return byteArray;
        } catch (Exception e) {
            return null;
        }
    }

    public void sendData(){
        String maBSX = lsMaBSX.getSelectedItem().toString();
        String timeIn = editTimeIn.getText().toString();
        String timeOut = editTimeOut.getText().toString();
        int loaixe = (int) lsLoaiXe.getSelectedItemId()+1;
        LichSuVaoRa lichSuVaoRa = new LichSuVaoRa(id, maBSX, timeIn, timeOut, null);
        Intent returnIntent = new Intent();
        returnIntent.putExtra("data", lichSuVaoRa);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
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