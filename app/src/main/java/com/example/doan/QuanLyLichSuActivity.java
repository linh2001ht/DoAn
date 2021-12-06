package com.example.doan;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import java.util.ArrayList;

public class QuanLyLichSuActivity extends AppCompatActivity {
    public static final int REQUEST_ADD_LS = 555;
    public static final int REQUEST_EDIT_LS = 666;
    private ArrayAdapter<LichSuVaoRa> arrayAdapter;
    private ArrayList<LichSuVaoRa> arrayList;
    private AppDao appDao;
    private TextView tvSumIn, tvSumIn1, tvSumIn2, tvSumOut, tvSumOut1, tvSumOut2, tvSum;
    private int idx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Quản lý lịch sử");
        setContentView(R.layout.activity_quan_ly_lich_su);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        AppDatabase appDatabase = AppDatabase.getInstance(this);
        appDao = appDatabase.appDao();

        ListView lvLS = findViewById(R.id.lv_ls);
        tvSum = findViewById(R.id.tv_sumall);
        tvSumIn = findViewById(R.id.tv_sumin);
        tvSumIn1 = findViewById(R.id.tv_sumin1);
        tvSumIn2 = findViewById(R.id.tv_sumin2);
        tvSumOut = findViewById(R.id.tv_sumout);
        tvSumOut1 = findViewById(R.id.tv_sumout1);
        tvSumOut2 = findViewById(R.id.tv_sumout2);

        arrayList = new ArrayList<>();

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
        lvLS.setAdapter(arrayAdapter);

        arrayAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                int sumin1 = 0, sumin2 = 0, sumout1 = 0, sumout2 = 0, sumall = 0;
                for(LichSuVaoRa lichSuVaoRa:arrayList) {
                    BienSoXe bienSoXe = appDao.findBymaBSX(lichSuVaoRa.getMaBSX());
                    if (bienSoXe.getLoaixe() == 0) {
                        if (lichSuVaoRa.getTgianvao() != null) {
                            sumin1 += 1;
                            if (lichSuVaoRa.getTgianra() != null) {
                                sumout1 += 1;
                                sumall += lichSuVaoRa.getGiave();
                            }
                        }
                    } else {
                        if (lichSuVaoRa.getTgianvao() != null) {
                            sumin2 += 1;
                            if (lichSuVaoRa.getTgianra() != null) {
                                sumout2 += 1;
                                sumall += lichSuVaoRa.getGiave();
                            }
                        }
                    }
                }
                tvSum.setText(""+sumall);
                tvSumIn.setText(""+(sumin1+sumin2));
                tvSumIn1.setText(""+sumin1);
                tvSumIn2.setText(""+sumin2);
                tvSumOut.setText(""+(sumout1+sumout2));
                tvSumOut1.setText(""+sumout1);
                tvSumOut2.setText(""+sumout2);
            }
        });

        arrayList.addAll(appDao.getAllLichSu());
        arrayAdapter.notifyDataSetChanged();


        lvLS.setOnItemLongClickListener((adapterView, view, position, id) -> {
            LichSuVaoRa lichSuVaoRa = arrayAdapter.getItem(position);
            AlertDialog.Builder builder = new AlertDialog.Builder(QuanLyLichSuActivity.this);

            builder.setTitle("Confirm");
            builder.setMessage("Bạn có muốn xóa lịch sử?");

            builder.setPositiveButton("YES", (dialog, which) -> {
                appDao.deleteLichSu(lichSuVaoRa);
                arrayList.remove(position);
                arrayAdapter.notifyDataSetChanged();
                dialog.dismiss();
            });

            builder.setNegativeButton("NO", (dialog, which) -> dialog.dismiss());

            AlertDialog alert = builder.create();
            alert.show();
            return true;
        });

        lvLS.setOnItemClickListener((adapterView, view, position, id) -> {
            Intent intent = new Intent(QuanLyLichSuActivity.this, ThongTinLichSuActivity.class);
            idx = position;
            LichSuVaoRa lichSuVaoRa = arrayList.get(position);
            intent.putExtra("data", lichSuVaoRa);
            startActivityForResult(intent, REQUEST_EDIT_LS);
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
                arrayList.addAll(appDao.getLichSus(s));
                arrayAdapter.notifyDataSetChanged();
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

        if (requestCode == REQUEST_ADD_LS && resultCode == RESULT_OK && data != null) {
            LichSuVaoRa lichSuVaoRa = (LichSuVaoRa) data.getSerializableExtra("data");
            arrayList.add(lichSuVaoRa);
            appDao.insertLichSu(lichSuVaoRa);
            arrayAdapter.notifyDataSetChanged();
        }
        if (requestCode == REQUEST_EDIT_LS && resultCode == RESULT_OK && data != null) {
            LichSuVaoRa lichSuVaoRa = (LichSuVaoRa) data.getSerializableExtra("data");
            arrayList.set(idx, lichSuVaoRa);
            appDao.updateLichSu(lichSuVaoRa.getID(), lichSuVaoRa.getMaBSX(), lichSuVaoRa.getGiave(), lichSuVaoRa.getAnhBSXvao(), lichSuVaoRa.getTgianvao(), lichSuVaoRa.getAnhBSXra(), lichSuVaoRa.getTgianra());
            arrayAdapter.notifyDataSetChanged();
        }
    }



}