package com.doguhan.flagandcountryquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    int tumSoruSayisi, dogruSoruSayisi, yanlisSoruSayisi;

    public static final String VERITABANI_ISMI = "information.db";
    private static final int VERITABANI_VERSIYON = 1;

    private static final String TABLO_YARAT =
            "CREATE TABLE " + TabloBilgileri.NoteEntry.TABLO_ADI + " (" +
                    TabloBilgileri.NoteEntry.ID + " INTEGER PRIMARY KEY, " +
                    TabloBilgileri.NoteEntry.TOPLAM_SORU + " INTEGER, " +
                    TabloBilgileri.NoteEntry.DOGRU_SORU + " INTEGER, " +
                    TabloBilgileri.NoteEntry.YANLIS_SORU + " INTEGER " +
                    ")";

    public DatabaseHelper(Context context) {
        super(context, VERITABANI_ISMI, null, VERITABANI_VERSIYON);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLO_YARAT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TabloBilgileri.NoteEntry.TABLO_ADI);
        onCreate(db);
    }


    public void dogruEkle(int belirtec) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        Cursor res = db.rawQuery("select * from " + TabloBilgileri.NoteEntry.TABLO_ADI, null);

        while (res.moveToNext()) {
            dogruSoruSayisi = res.getInt(2);
        }
        //Eğer belirteç 1 ise veriyi değiştirme sabit tut eğer 2 ise veriyi +1 artır ve güncelle
        if (belirtec==1){
            cv.put(TabloBilgileri.NoteEntry.DOGRU_SORU, dogruSoruSayisi);

            // notunu güncelleyelim
              db.update(TabloBilgileri.NoteEntry.TABLO_ADI, cv, TabloBilgileri.NoteEntry.DOGRU_SORU + "=" + dogruSoruSayisi, null);
        }else{
            int yeniDogruSoruSayisi = dogruSoruSayisi + 1;
            cv.put(TabloBilgileri.NoteEntry.DOGRU_SORU, yeniDogruSoruSayisi);

            // notunu güncelleyelim
              db.update(TabloBilgileri.NoteEntry.TABLO_ADI, cv, TabloBilgileri.NoteEntry.DOGRU_SORU + "=" + dogruSoruSayisi, null);
        }
        db.close();
    }

    public void yanlisSoruEkle(int belirtec) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        Cursor res = db.rawQuery("select * from " + TabloBilgileri.NoteEntry.TABLO_ADI, null);

        while (res.moveToNext()) {
            yanlisSoruSayisi = res.getInt(3);
        }
        //Eğer belirteç 1 ise veriyi değiştirme sabit tut eğer 2 ise veriyi +1 artır ve güncelle
        if (belirtec==1){
            cv.put(TabloBilgileri.NoteEntry.YANLIS_SORU, yanlisSoruSayisi);
            // notunu güncelleyelim
             db.update(TabloBilgileri.NoteEntry.TABLO_ADI, cv, TabloBilgileri.NoteEntry.YANLIS_SORU + "=" + yanlisSoruSayisi, null);
        }else{
            int yeniYanlisSoruSayisi = yanlisSoruSayisi + 1;

            cv.put(TabloBilgileri.NoteEntry.YANLIS_SORU, yeniYanlisSoruSayisi);
            // notunu güncelleyelim
             db.update(TabloBilgileri.NoteEntry.TABLO_ADI, cv, TabloBilgileri.NoteEntry.YANLIS_SORU + "=" + yanlisSoruSayisi, null);
        }
        db.close();
    }

    public void tumSorularaEkle(int belirtec) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        Cursor res = db.rawQuery("select * from " + TabloBilgileri.NoteEntry.TABLO_ADI, null);
        while (res.moveToNext()) {
            tumSoruSayisi = res.getInt(1);   //0 is the number of id column in your database table
        }
        //Eğer belirteç 1 ise veriyi değiştirme sabit tut eğer 2 ise veriyi +1 artır ve güncelle
        if (belirtec==1){
            cv.put(TabloBilgileri.NoteEntry.TOPLAM_SORU, tumSoruSayisi);
            // notunu güncelleyelim
             db.update(TabloBilgileri.NoteEntry.TABLO_ADI, cv, TabloBilgileri.NoteEntry.TOPLAM_SORU + "=" + tumSoruSayisi, null);
        }else{
            int yeniTumSoruSayisi = tumSoruSayisi + 1;
            cv.put(TabloBilgileri.NoteEntry.TOPLAM_SORU, yeniTumSoruSayisi);
            // notunu güncelleyelim
             db.update(TabloBilgileri.NoteEntry.TABLO_ADI, cv, TabloBilgileri.NoteEntry.TOPLAM_SORU + "=" + tumSoruSayisi, null);
        }
        db.close();
    }

}