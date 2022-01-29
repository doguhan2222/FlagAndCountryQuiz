package com.doguhan.flagandcountryquiz;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AnaEkran extends AppCompatActivity {
    TextView tumSoruTV, dogruSoruTv, yanlisSoruTV;
    Button baslaButon;
    SQLiteDatabase db;
    int tumSoruSayisi, dogruSoruSayisi, yanlisSoruSayisi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ana_ekran);
        tanimla();
        quizBaslat();
        dataOku();
    }

    public void tanimla() {
        tumSoruTV = findViewById(R.id.toplamSoruTV);
        dogruSoruTv = findViewById(R.id.dogruSoruTV);
        yanlisSoruTV = findViewById(R.id.yanlisSoruTV);
        baslaButon = findViewById(R.id.anaEkranBaslaButon);
    }

    public void quizBaslat() {
        baslaButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AnaEkran.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    //Ana ekranda verileri göstermemizi sağlar
    public void dataOku() {
        DatabaseHelper databaseHelper = new DatabaseHelper(AnaEkran.this);
        db = databaseHelper.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TabloBilgileri.NoteEntry.TABLO_ADI, null);

        while (res.moveToNext()) {
            tumSoruSayisi = res.getInt(1);   //0 id
            dogruSoruSayisi = res.getInt(2);
            yanlisSoruSayisi = res.getInt(3);
        }
        tumSoruTV.setText(String.valueOf(tumSoruSayisi));
        dogruSoruTv.setText(String.valueOf(dogruSoruSayisi));
        yanlisSoruTV.setText(String.valueOf(yanlisSoruSayisi));
        db.close();
    }
}