package com.example.aplikasicrudproduk;

import android.app.DatePickerDialog;
import android.content.ContentValues;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddActivity extends AppCompatActivity {

    DBHelper helper;
    EditText KodeBarang, NamaBarang, Harga, Jumlah, TglExp;
    Spinner SpJB;
    long id;
    DatePickerDialog datePickerDialog;
    SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        helper = new DBHelper(this);

        id = getIntent().getLongExtra(DBHelper.row_id, 0);

        KodeBarang = (EditText)findViewById(R.id.KodeBarang_Add);
        NamaBarang = (EditText)findViewById(R.id.NamaBarang_Add);
        Harga = (EditText)findViewById(R.id.Harga_Add);
        Jumlah = (EditText)findViewById(R.id.Jumlah_Add);
        TglExp = (EditText)findViewById(R.id.TglExp_Add);
        SpJB = (Spinner)findViewById(R.id.spJB_Add);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        TglExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });
    }

    private void showDateDialog() {
        Calendar calendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);
                TglExp.setText(dateFormatter.format(newDate.getTime()));
            }
        },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_add:
                String kodeBarang = KodeBarang.getText().toString().trim();
                String namaBarang = NamaBarang.getText().toString().trim();
                String jb = SpJB.getSelectedItem().toString().trim();
                String harga = Harga.getText().toString().trim();
                String jumlah = Jumlah.getText().toString().trim();
                String tglExp = TglExp.getText().toString().trim();

                ContentValues values = new ContentValues();
                values.put(DBHelper.row_kodeBarang, kodeBarang);
                values.put(DBHelper.row_namaBarang, namaBarang);
                values.put(DBHelper.row_jb, jb);
                values.put(DBHelper.row_harga, harga);
                values.put(DBHelper.row_jumlah, jumlah);
                values.put(DBHelper.row_tanggalExp, tglExp);

                if (kodeBarang.equals("") || namaBarang.equals("") || harga.equals("") || jumlah.equals("") || tglExp.equals("")){
                    Toast.makeText(AddActivity.this, "Data Barang Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                }else{
                    helper.insertData(values);
                    Toast.makeText(AddActivity.this, "Data Barang Telah Tersimpan", Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
        return super.onOptionsItemSelected(item);
    }
}
