package com.example.doan;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class QuanLyLichSuActivity extends AppCompatActivity {
    public static final int REQUEST_ADD_LS = 555;
    public static final int REQUEST_EDIT_LS = 666;
    private ArrayAdapter<LichSuVaoRa> arrayAdapter;
    private ArrayList<LichSuVaoRa> arrayList;
    private AppDatabase appDatabase;
    private AppDao appDao;
    private ListView lvLS;
    private int idx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Quản lý lịch sử");
        setContentView(R.layout.activity_quan_ly_lich_su);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        lvLS = findViewById(R.id.lv_ls);

        appDatabase = AppDatabase.getInstance(this);
        appDao = appDatabase.appDao();

        arrayList = new ArrayList<LichSuVaoRa>();
        arrayList.addAll(appDao.getAllLichSu());

        for(LichSuVaoRa x:arrayList) {
            Log.d("qq", x.toString());
        }

        arrayAdapter = new ArrayAdapter<LichSuVaoRa>(this, android.R.layout.simple_list_item_1, arrayList);
        lvLS.setAdapter(arrayAdapter);


        lvLS.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                LichSuVaoRa lichSuVaoRa = arrayAdapter.getItem(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(QuanLyLichSuActivity.this);

                builder.setTitle("Confirm");
                builder.setMessage("Bạn có muốn xóa lịch sử?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        appDao.deleteLichSu(lichSuVaoRa);
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

        lvLS.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(QuanLyLichSuActivity.this, ThongTinLichSuActivity.class);
                idx = position;
                LichSuVaoRa lichSuVaoRa = arrayList.get(position);
                intent.putExtra("data", lichSuVaoRa);
                startActivityForResult(intent, REQUEST_EDIT_LS);
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

    public void addLichSu(View view) {
        Intent intent = new Intent(QuanLyLichSuActivity.this, ThongTinLichSuActivity.class);
        startActivityForResult(intent, REQUEST_ADD_LS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("qq", "qqqqq--");

        if (requestCode == REQUEST_ADD_LS && resultCode == RESULT_OK && data != null) {
            LichSuVaoRa lichSuVaoRa = (LichSuVaoRa) data.getSerializableExtra("data");
            arrayList.add(lichSuVaoRa);
            appDao.insertLichSu(lichSuVaoRa);
            arrayAdapter.notifyDataSetChanged();
        }
        if (requestCode == REQUEST_EDIT_LS && resultCode == RESULT_OK && data != null) {
            LichSuVaoRa lichSuVaoRa = (LichSuVaoRa) data.getSerializableExtra("data");
            arrayList.set(idx, lichSuVaoRa);
            appDao.updateLichSu(lichSuVaoRa.getID(), lichSuVaoRa.getMaBSX(), lichSuVaoRa.getTgianvao(), lichSuVaoRa.getTgianra());
            arrayAdapter.notifyDataSetChanged();
        }
    }
}