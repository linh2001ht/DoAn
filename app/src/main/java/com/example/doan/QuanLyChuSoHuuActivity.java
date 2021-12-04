package com.example.doan;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class QuanLyChuSoHuuActivity extends AppCompatActivity {
    public static final int REQUEST_ADD_CHS = 111;
    public static final int REQUEST_EDIT_CSH = 222;
    private ArrayAdapter<ChuSoHuu> arrayAdapter;
    private ArrayList<ChuSoHuu> arrayList;
    private AppDatabase appDatabase;
    private AppDao appDao;
    private ListView lvCSH;
    private int idx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Quản lý chủ sở hữu");
        setContentView(R.layout.activity_quan_ly_chu_so_huu);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        lvCSH = findViewById(R.id.lv_csh);
        appDatabase = AppDatabase.getInstance(this);
        appDao = appDatabase.appDao();
        arrayList = new ArrayList<ChuSoHuu>();
        arrayList.addAll(appDao.getAllChuSoHuu());
        arrayAdapter = new ArrayAdapter<ChuSoHuu>(this, android.R.layout.simple_list_item_1, arrayList);
        lvCSH.setAdapter(arrayAdapter);



        lvCSH.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                ChuSoHuu chuSoHuu = arrayAdapter.getItem(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(QuanLyChuSoHuuActivity.this);

                builder.setTitle("Confirm");
                builder.setMessage("Bạn có muốn xóa chủ sở hữu?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        appDao.deleteChuSoHuu(chuSoHuu);
                        arrayList.remove(position);
                        arrayAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
                return true;
            }
        });

        lvCSH.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(QuanLyChuSoHuuActivity.this, ThongTinChuSoHuuActivity.class);
                ChuSoHuu chuSoHuu = arrayAdapter.getItem(position);
                intent.putExtra("data", chuSoHuu);
                idx = position;
                startActivityForResult(intent, REQUEST_EDIT_CSH);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }




    @Override
    public boolean onCreateOptionsMenu( Menu menu) {
        getMenuInflater().inflate( R.menu.menu, menu);

        MenuItem myActionMenuItem = menu.findItem( R.id.action_search);
        SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return true;
    }

    public void addChuSoHuu(View view) {
        Intent intent = new Intent(QuanLyChuSoHuuActivity.this, ThongTinChuSoHuuActivity.class);
        startActivityForResult(intent, REQUEST_ADD_CHS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ADD_CHS && resultCode == RESULT_OK && data != null) {
            ChuSoHuu chuSoHuu = (ChuSoHuu) data.getSerializableExtra("data");
            arrayList.add(chuSoHuu);
            appDao.insertChuSoHuu(chuSoHuu);
            arrayAdapter.notifyDataSetChanged();
        }
        if (requestCode == REQUEST_EDIT_CSH && resultCode == RESULT_OK && data != null) {
            ChuSoHuu chuSoHuu = (ChuSoHuu) data.getSerializableExtra("data");
            arrayList.set(idx, chuSoHuu);
            appDao.updateChuSoHuu(chuSoHuu.getIDchusohuu(), chuSoHuu.getName(), chuSoHuu.getGioitinh(), chuSoHuu.getNgaysinh(), chuSoHuu.getPhone(), chuSoHuu.getAddress());
            arrayAdapter.notifyDataSetChanged();
        }
    }
}