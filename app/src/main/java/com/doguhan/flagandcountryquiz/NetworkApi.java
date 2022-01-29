package com.doguhan.flagandcountryquiz;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface NetworkApi {
    String urlIsım = "keeguon/2310008/raw/bdc2ce1c1e3f28f9cab5b4393c7549f38361be4e/countries.json";

    @GET(urlIsım)
    Call<List<UlkeIsimleri>> ulkeIsimleriniAl();

}
