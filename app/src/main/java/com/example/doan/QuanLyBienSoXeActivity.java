package com.example.doan;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import java.util.ArrayList;


public class QuanLyBienSoXeActivity extends AppCompatActivity {
    public static final int REQUEST_ADD_BSX = 333;
    public static final int REQUEST_EDIT_BSX = 444;
    private ArrayAdapter<BienSoXe> arrayAdapter;
    private ArrayList<BienSoXe> arrayList;
    private AppDao appDao;
    private int idx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Quản lý biển số xe");
        setContentView(R.layout.activity_quan_ly_bien_so_xe);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        ListView lvBsx = findViewById(R.id.lv_bsx);

        arrayList = new ArrayList<>();
        AppDatabase appDatabase = AppDatabase.getInstance(this);
        appDao = appDatabase.appDao();
        arrayList.addAll(appDao.getAllBienSoXe());
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
        lvBsx.setAdapter(arrayAdapter);


        lvBsx.setOnItemLongClickListener((adapterView, view, position, id) -> {
            BienSoXe bienSoXe = arrayAdapter.getItem(position);
            AlertDialog.Builder builder = new AlertDialog.Builder(QuanLyBienSoXeActivity.this);

            builder.setTitle("Confirm");
            builder.setMessage("Bạn có muốn xóa biển số xe?");

            builder.setPositiveButton("YES", (dialog, which) -> {
                appDao.deleteBienSoXe(bienSoXe);
                arrayList.remove(position);
                arrayAdapter.notifyDataSetChanged();
                dialog.dismiss();
            });

            builder.setNegativeButton("NO", (dialog, which) -> dialog.dismiss());

            AlertDialog alert = builder.create();
            alert.show();
            return true;
        });

        lvBsx.setOnItemClickListener((adapterView, view, position, id) -> {
            Intent intent = new Intent(QuanLyBienSoXeActivity.this, ThongTinBienSoXeActivity.class);
            BienSoXe bienSoXe = arrayList.get(position);
            intent.putExtra("data", bienSoXe);
            idx = position;
            startActivityForResult(intent, REQUEST_EDIT_BSX);
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
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
                arrayList.clear();
                arrayList.addAll(appDao.getBienSoXes(s));
                arrayAdapter.notifyDataSetChanged();
                return true;
            }
        });
        return true;
    }

    public void addBienSoXe(View view) {
        Intent intent = new Intent(QuanLyBienSoXeActivity.this, ThongTinBienSoXeActivity.class);
        startActivityForResult(intent, REQUEST_ADD_BSX);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ADD_BSX && resultCode == RESULT_OK && data != null) {
            BienSoXe bienSoXe = (BienSoXe) data.getSerializableExtra("data");
            arrayList.add(bienSoXe);
            appDao.insertBienSoXe(bienSoXe);
            arrayAdapter.notifyDataSetChanged();
        }
        if (requestCode == REQUEST_EDIT_BSX && resultCode == RESULT_OK && data != null) {
            BienSoXe bienSoXe = (BienSoXe) data.getSerializableExtra("data");
            arrayList.set(idx, bienSoXe);
            appDao.updateBienSoXe(bienSoXe.getMaBSX(), bienSoXe.getLoaixe(), bienSoXe.getIDChuSoHuu());
            arrayAdapter.notifyDataSetChanged();
        }
    }
}
