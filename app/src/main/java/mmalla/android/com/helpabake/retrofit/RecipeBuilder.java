package mmalla.android.com.helpabake.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * RecipeBuilder is used to get the recipes using Retrofit
 */
public final class RecipeBuilder {

    static RemoteRecipeService remoteRecipeService;
    public static final String ENDPOINT = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";

    public static RemoteRecipeService retrieve(){
        Gson gson = new GsonBuilder().create();

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();

        remoteRecipeService = new Retrofit.Builder()
                .baseUrl(ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .callFactory(httpClientBuilder.build())
                .build().create(RemoteRecipeService.class);

        return remoteRecipeService;
    }

}
