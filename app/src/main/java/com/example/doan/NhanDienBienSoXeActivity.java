package com.example.doan;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NhanDienBienSoXeActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView tvBien;
    String currentPhotoPath;
    public static final int CAMERA_REQUEST_CODE = 102;
    public static final int GALLERY_REQUEST_CODE = 105;
    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseDatabase rootNode;
    DatabaseReference myRef;
    AppDatabase appDatabase;
    AppDao appDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        rootNode = FirebaseDatabase.getInstance();
        myRef = rootNode.getReference();
        setContentView(R.layout.activity_nhan_dien_bien_so_xe);
        Button btnCamera = findViewById(R.id.btn_camera);
        Button btnCheck = findViewById(R.id.btn_check);
        tvBien = findViewById(R.id.tv_bien);
        imageView = findViewById(R.id.iv_bien);
        Button btnGallery = findViewById(R.id.btn_gallery);
        appDatabase = AppDatabase.getInstance(this);
        appDao = appDatabase.appDao();

        btnCamera.setOnClickListener(view -> {
            tvBien.setText("");
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Ensure that there's a camera activity to handle the intent
            if (intent.resolveActivity(getPackageManager()) != null) {
                // Create the File where the photo should go
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                // Continue only if the File was successfully created
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(NhanDienBienSoXeActivity.this,
                            "net.example.android.fileprovider",
                            photoFile);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(intent, CAMERA_REQUEST_CODE);
                }
            }
        });
        btnGallery.setOnClickListener(v -> {
            tvBien.setText("");
            Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(gallery, GALLERY_REQUEST_CODE);
        });

        btnCheck.setOnClickListener(view -> {
            String bien = tvBien.getText().toString();
            if (appDao.findBymaBSX(bien) == null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(NhanDienBienSoXeActivity.this);

                builder.setTitle("Confirm");
                builder.setMessage("Biển số xe không có trong CSDL. Bạn có muốn thêm?");

                builder.setPositiveButton("YES", (dialog, which) -> {
                    Intent intent = new Intent(NhanDienBienSoXeActivity.this, ThongTinBienSoXeActivity.class);
                    intent.putExtra("data", new BienSoXe(bien, 0, 1));
                    startActivity(intent);
                    dialog.dismiss();
                });

                builder.setNegativeButton("NO", (dialog, which) -> dialog.dismiss());

                AlertDialog alert = builder.create();
                alert.show();
            } else {
                Toast.makeText(NhanDienBienSoXeActivity.this, "Biển số "+bien+" có trong CSDL. Chủ sở hữu: "+ appDao.findCSHbyID(appDao.findBymaBSX(bien).getIDChuSoHuu()).getName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(NhanDienBienSoXeActivity.this, ThongTinLichSuActivity.class);
                //intent.putExtra("data", appDao.findCSHbyID(appDao.findBymaBSX(bien).getIDChuSoHuu()));
                intent.putExtra("MaBSX", bien);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                File f = new File(currentPhotoPath);
                imageView.setImageURI(Uri.fromFile(f));
                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri contentUri = Uri.fromFile(f);
                mediaScanIntent.setData(contentUri);
                this.sendBroadcast(mediaScanIntent);
                uploadImageToFirebase(f.getName(), contentUri);
            }

        }

        if (requestCode == GALLERY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri contentUri = data.getData();
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String imageFileName = "JPEG_" + timeStamp + "." + getFileExt(contentUri);
                imageView.setImageURI(contentUri);
                uploadImageToFirebase(imageFileName, contentUri);
            }
        }


    }

    private void uploadImageToFirebase(String name, Uri contentUri) {
        final StorageReference image = storageReference.child(name);
        image.putFile(contentUri).addOnSuccessListener(taskSnapshot -> {
            image.getDownloadUrl().addOnSuccessListener(uri -> Log.d("tag", "onSuccess: Uploaded Image URl is " + uri.toString()));

            Toast.makeText(NhanDienBienSoXeActivity.this, "Image Is Uploaded.", Toast.LENGTH_SHORT).show();

            String key = myRef.push().getKey();
            assert key != null;
            myRef.child(key).child(name.substring(0, name.length() - 4)).setValue("null");
            final ValueEventListener valueEventListener = myRef.child(key).child(name.substring(0, name.length() - 4)).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String value = dataSnapshot.getValue(String.class);
                    if (value != null && !value.equals("null")) {
                        tvBien.setText(value);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(NhanDienBienSoXeActivity.this, "Upload Failed.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private String getFileExt(Uri contentUri) {
        ContentResolver c = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(c.getType(contentUri));
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

}

