package com.example.doan;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
    private AppDatabase appDatabase;
    private AppDao appDao;
    private ListView lvBsx;
    private int idx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Quản lý biển số xe");
        setContentView(R.layout.activity_quan_ly_bien_so_xe);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        lvBsx = findViewById(R.id.lv_bsx);

        arrayList = new ArrayList<BienSoXe>();
        appDatabase = AppDatabase.getInstance(this);
        appDao = appDatabase.appDao();
        arrayList.addAll(appDao.getAllBienSoXe());
        arrayAdapter = new ArrayAdapter<BienSoXe>(this, android.R.layout.simple_list_item_1, arrayList);
        lvBsx.setAdapter(arrayAdapter);


        lvBsx.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                BienSoXe bienSoXe = arrayAdapter.getItem(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(QuanLyBienSoXeActivity.this);

                builder.setTitle("Confirm");
                builder.setMessage("Bạn có muốn xóa biển số xe?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        appDao.deleteBienSoXe(bienSoXe);
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

        lvBsx.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(QuanLyBienSoXeActivity.this, ThongTinBienSoXeActivity.class);
                BienSoXe bienSoXe = arrayList.get(position);
                intent.putExtra("data", bienSoXe);
                idx = position;
                startActivityForResult(intent, REQUEST_EDIT_BSX);
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
            appDao.updateBienSoXe(bienSoXe.getMaBSX(), bienSoXe.getIDChuSoHuu());
            arrayAdapter.notifyDataSetChanged();
        }
    }
}
