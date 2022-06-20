package advancedprog2.messageappandroid.api;

import advancedprog2.messageappandroid.R;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppApi {
    Retrofit retrofit;
    WebAPI webAPI;

    public AppApi(){
        retrofit = new Retrofit.Builder()
                .baseUrl(ContextApplication.context.getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webAPI = retrofit.create(WebAPI.class);

    }
    // any methods needed



}
