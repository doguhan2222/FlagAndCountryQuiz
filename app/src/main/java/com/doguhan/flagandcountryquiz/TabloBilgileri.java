package com.doguhan.flagandcountryquiz;
import android.provider.BaseColumns;

public class TabloBilgileri {

    public static final class NoteEntry implements BaseColumns {
        public static final String TABLO_ADI = "tum_bilgiler";
        public static final String ID = "id";
        public static final String TOPLAM_SORU = "toplam_soru";
        public static final String DOGRU_SORU = "dogru_soru";
        public static final String YANLIS_SORU = "yanlis_soru";

    }
}