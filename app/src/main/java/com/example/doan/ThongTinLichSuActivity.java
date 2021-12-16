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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.annotations.Nullable;

import java.io.ByteArrayOutputStream;
import java.util.Date;

public class ThongTinLichSuActivity extends AppCompatActivity {

    public static final int CAMERA_REQUEST_CODE_IN = 102;
    public static final int GALLERY_REQUEST_CODE_IN = 103;
    public static final int CAMERA_REQUEST_CODE_OUT = 104;
    public static final int GALLERY_REQUEST_CODE_OUT = 105;
    private ImageView imageViewIn, imageViewOut;
    private int id;
    private EditText lsGiaVe, editTimeIn, editTimeOut;
    private Spinner lsMaBSX;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_lich_su);

        imageViewIn = findViewById(R.id.iv_bien_in);
        imageViewOut = findViewById(R.id.iv_bien_out);
        lsMaBSX = findViewById(R.id.ls_maBSX);
        lsGiaVe = findViewById(R.id.ls_giave);
        editTimeIn = findViewById(R.id.edit_time_in);
        editTimeOut = findViewById(R.id.edit_time_out);
        AppDatabase appDatabase = AppDatabase.getInstance(this);
        AppDao appDao = appDatabase.appDao();
        ArrayAdapter<BienSoXe> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                appDao.getAllBienSoXe());

        // Layout for All ROWs of Spinner.  (Optional for ArrayAdapter).
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        lsMaBSX.setAdapter(adapter);


        Intent receive = getIntent();
        String bien = receive.getStringExtra("MaBSX");
        if (bien != null) {
            this.setTitle("Thêm lịch sử");
            int position = -1;
            for (BienSoXe x: appDao.getAllBienSoXe()) {
                position += 1;
                if (x.getMaBSX().equals(bien)) break;
            }
            lsMaBSX.setSelection(position);
        } else {
            LichSuVaoRa lichSuVaoRa = (LichSuVaoRa) receive.getSerializableExtra("data");
            if (lichSuVaoRa != null) {
                this.setTitle("Sửa lịch sử");
                id = lichSuVaoRa.getID();
                int position = -1;
                for (BienSoXe x : appDao.getAllBienSoXe()) {
                    position += 1;
                    if (x.getMaBSX().equals(lichSuVaoRa.getMaBSX())) break;
                }
                lsMaBSX.setSelection(position);
                lsGiaVe.setText(String.valueOf(lichSuVaoRa.getGiave()));
                editTimeIn.setText(TimestampConverter.toString(lichSuVaoRa.getTgianvao()));
                editTimeOut.setText(TimestampConverter.toString(lichSuVaoRa.getTgianra()));
                byte[] imgIn = lichSuVaoRa.getAnhBSXvao();
                if (imgIn != null) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imgIn, 0, imgIn.length);
                    imageViewIn.setImageBitmap(bitmap);
                }
                byte[] imgOut = lichSuVaoRa.getAnhBSXra();
                if (imgOut != null) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imgOut, 0, imgOut.length);
                    imageViewOut.setImageBitmap(bitmap);
                }
            }
            else {
                this.setTitle("Thêm lịch sử");
            }
        }

        imageViewIn.setOnClickListener(view -> {
            PopupMenu popupMenuIn = new PopupMenu(ThongTinLichSuActivity.this, view);
            popupMenuIn.inflate(R.menu.popup_menu);
            popupMenuIn.setOnMenuItemClickListener(menuItem -> {
                switch (menuItem.getItemId()) {
                    case R.id.open_camera:
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, CAMERA_REQUEST_CODE_IN);
                        return true;
                    case R.id.open_gallery:
                        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(gallery, GALLERY_REQUEST_CODE_IN);
                        return true;
                }
                return false;
            });
            popupMenuIn.show();
        });

        imageViewOut.setOnClickListener(view -> {
            PopupMenu popupMenuOut = new PopupMenu(ThongTinLichSuActivity.this, imageViewOut);
            popupMenuOut.inflate(R.menu.popup_menu);
            popupMenuOut.setOnMenuItemClickListener(menuItem -> {
                switch (menuItem.getItemId()) {
                    case R.id.open_camera:
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, CAMERA_REQUEST_CODE_OUT);
                        return true;
                    case R.id.open_gallery:
                        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(gallery, GALLERY_REQUEST_CODE_OUT);
                        return true;
                }
                return false;
            });
            popupMenuOut.show();
        });
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE_IN) {
            if (resultCode == Activity.RESULT_OK) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                imageViewIn.setImageBitmap(bitmap);
                editTimeIn.setText(TimestampConverter.toString(new Date()));
            }

        }

        if (requestCode == GALLERY_REQUEST_CODE_IN) {
            if (resultCode == Activity.RESULT_OK) {
                Uri contentUri = data.getData();
                imageViewIn.setImageURI(contentUri);
                editTimeIn.setText(TimestampConverter.toString(new Date()));
            }
        }

        if (requestCode == CAMERA_REQUEST_CODE_OUT) {
            if (resultCode == Activity.RESULT_OK) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                imageViewOut.setImageBitmap(bitmap);
                editTimeOut.setText(TimestampConverter.toString(new Date()));
            }
        }

        if (requestCode == GALLERY_REQUEST_CODE_OUT) {
            if (resultCode == Activity.RESULT_OK) {
                Uri contentUri = data.getData();
                imageViewOut.setImageURI(contentUri);
                editTimeOut.setText(TimestampConverter.toString((new Date())));
            }
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

    public void sendData(){
        try {
            String maBSX = lsMaBSX.getSelectedItem().toString();
            int giave = Integer.parseInt(lsGiaVe.getText().toString());
            byte[] imgIn = imageViewToByte(imageViewIn);
            Date timeIn = TimestampConverter.toTimestamp(editTimeIn.getText().toString());
            byte[] imgOut = imageViewToByte(imageViewOut);
            Date timeOut = TimestampConverter.toTimestamp(editTimeOut.getText().toString());
            LichSuVaoRa lichSuVaoRa = new LichSuVaoRa(id, maBSX, giave, imgIn, timeIn, imgOut, timeOut);
            Intent returnIntent = new Intent();
            returnIntent.putExtra("data", lichSuVaoRa);
            setResult(Activity.RESULT_OK,returnIntent);
            finish();
        } catch (Exception ex) {
            Toast.makeText(ThongTinLichSuActivity.this, ex.toString(), Toast.LENGTH_SHORT).show();
        }

    }


}