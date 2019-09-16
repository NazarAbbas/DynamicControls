package commdel.com;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/*
* Retrofit rest client to process the rest apis in a smart way.
* */
public class RestClient {
    DataServiceApi api = null;

    public RestClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().readTimeout(120, TimeUnit.SECONDS)
                .connectTimeout(120, TimeUnit.SECONDS).retryOnConnectionFailure(false).build();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .baseUrl(DataServiceApi.BASE_URL)
                .build();

        api = retrofit.create(DataServiceApi.class);
    }

    public DataServiceApi getApi() {
        return api;
    }


}