package com.doguhan.flagandcountryquiz;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.Locale;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    ImageView resim;
    ImageButton geriGelB;
    Button cevapB1, cevapB2, cevapB3;
    String rastegeleKod, rastgeleCevapIsmi1, rastgeleCevapIsmi2, cevapUlkeIsmi, cevapKontrol, imageUrl, resimTipi;
    Random rnd;
    List<UlkeIsimleri> liste;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tanimla();
        veriCekmeIslemleri();
        geriGel();
    }

    public void tanimla() {
        resim = findViewById(R.id.resim);
        cevapB1 = findViewById(R.id.cevapButon1);
        cevapB2 = findViewById(R.id.cevapButon2);
        cevapB3 = findViewById(R.id.cevapButon3);
        geriGelB = findViewById(R.id.geriTus);
    }

    public void geriGel() {
        geriGelB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AnaEkran.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void veriCekmeIslemleri() {
        String url = "https://gist.githubusercontent.com/";
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        NetworkApi networkApi = retrofit.create(NetworkApi.class);
        Call<List<UlkeIsimleri>> call = networkApi.ulkeIsimleriniAl();

        call.enqueue(new Callback<List<UlkeIsimleri>>() {
            @Override
            public void onResponse(Call<List<UlkeIsimleri>> call, Response<List<UlkeIsimleri>> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                liste = response.body();
                resimGoster(liste);
            }

            @Override
            public void onFailure(Call<List<UlkeIsimleri>> call, Throwable t) {
                Log.i("denemefail", t.getMessage());
            }
        });
    }

    public void resimGoster(List<UlkeIsimleri> liste) {
        rnd = new Random();
        UlkeIsimleri rastgeleKodModel = liste.get(rnd.nextInt(liste.size()));
        rastegeleKod = rastgeleKodModel.getCode().toLowerCase(Locale.ENGLISH);
        Log.i("cevapBayrakKodu", rastegeleKod);

        imageUrl = "https://flagcdn.com/160x120/";
        resimTipi = ".png";
        //gelen ülke koduna göre bayrağı çekmek için string birleştirir
        imageUrl = imageUrl.concat(rastegeleKod).concat(resimTipi);
        Glide.with(getApplicationContext())
                .load(imageUrl)
                .into(resim);
        bayrakIsmiBul(liste);
    }

    public void bayrakIsmiBul(List<UlkeIsimleri> liste) {
        //Burada bayrakla eşlesen ülkenin ismini buluyoruz
        for (UlkeIsimleri ulkeIsimleri : liste) {

            if (ulkeIsimleri.getCode().equals(rastegeleKod.toUpperCase(Locale.ENGLISH))) {
                cevapUlkeIsmi = ulkeIsimleri.getName();
                Log.i("cevapUlkeIsmi", cevapUlkeIsmi);
                cevapYazdirVeKontrol(cevapUlkeIsmi, liste);
            }
        }
    }

    public void cevapYazdirVeKontrol(String cevap, List<UlkeIsimleri> liste) {
        rnd = new Random();
        // 1 ile 3 arasında hangi şıkka cevabın yazılmasın sağlayacak rastgele sayıyıy buluyor
        int rastgeleSayi = rnd.nextInt(3) + 1;
        //2 tane rastgele cevap buluyoruz
        UlkeIsimleri rastgeleCevapModel = liste.get(rnd.nextInt(liste.size()));
        UlkeIsimleri rastgeleCevapModel2 = liste.get(rnd.nextInt(liste.size()));
        rastgeleCevapIsmi1 = rastgeleCevapModel.getName();
        rastgeleCevapIsmi2 = rastgeleCevapModel2.getName();

        switch (rastgeleSayi) {
            case 1:
                cevapB1.setText(cevap);
                cevapB2.setText(rastgeleCevapIsmi1);
                cevapB3.setText(rastgeleCevapIsmi2);
                break;
            case 2:
                cevapB2.setText(cevap);
                cevapB1.setText(rastgeleCevapIsmi1);
                cevapB3.setText(rastgeleCevapIsmi2);
                break;
            case 3:
                cevapB3.setText(cevap);
                cevapB2.setText(rastgeleCevapIsmi1);
                cevapB1.setText(rastgeleCevapIsmi2);
                break;
        }
        cevapB1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cevapKontrol = cevapB1.getText().toString();
                if (cevapKontrol.equals(cevapUlkeIsmi)) {
                    DatabaseHelper db = new DatabaseHelper(MainActivity.this);
                    db.dogruEkle(2);
                    db.yanlisSoruEkle(1);
                    db.tumSorularaEkle(2);
                    db.close();
                    resimGoster(liste);
                } else {
                    Toast.makeText(MainActivity.this, "Cevabınız Yanlış Lütfen Tekrar Deneyin", Toast.LENGTH_SHORT);
                    DatabaseHelper db = new DatabaseHelper(MainActivity.this);
                    db.yanlisSoruEkle(2);
                    db.dogruEkle(1);
                    db.tumSorularaEkle(2);
                    db.close();
                }
            }
        });
        cevapB2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cevapKontrol = cevapB2.getText().toString();
                if (cevapKontrol.equals(cevapUlkeIsmi)) {
                    DatabaseHelper db = new DatabaseHelper(MainActivity.this);
                    db.dogruEkle(2);
                    db.yanlisSoruEkle(1);
                    db.tumSorularaEkle(2);
                    db.close();
                    resimGoster(liste);
                } else {
                    Toast.makeText(MainActivity.this, "Cevabınız Yanlış Lütfen Tekrar Deneyin", Toast.LENGTH_SHORT);
                    DatabaseHelper db = new DatabaseHelper(MainActivity.this);
                    db.yanlisSoruEkle(2);
                    db.dogruEkle(1);
                    db.tumSorularaEkle(2);
                    db.close();
                }
            }
        });
        cevapB3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cevapKontrol = cevapB3.getText().toString();
                if (cevapKontrol.equals(cevapUlkeIsmi)) {
                    DatabaseHelper db = new DatabaseHelper(MainActivity.this);
                    db.dogruEkle(2);
                    db.yanlisSoruEkle(1);
                    db.tumSorularaEkle(2);
                    resimGoster(liste);
                } else {
                    Toast.makeText(MainActivity.this, "Cevabınız Yanlış Lütfen Tekrar Deneyin", Toast.LENGTH_SHORT);
                    DatabaseHelper db = new DatabaseHelper(MainActivity.this);
                    db.yanlisSoruEkle(2);
                    db.dogruEkle(1);
                    db.tumSorularaEkle(2);
                    db.close();
                }
            }
        });

    }

}
