package dev.jay.ultimatepokedex.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.Strictness;

import dev.jay.ultimatepokedex.interceptor.AuthInterceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API {
    public static final String BASE_URL = "http://10.0.2.2:9090/";

    public static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .setStrictness(Strictness.LENIENT)
            .create();

    private static final HttpLoggingInterceptor HTTP_LOGGING_INTERCEPTOR = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY);

    private static final AuthInterceptor AUTH_INTERCEPTOR = new AuthInterceptor();

    private static final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient.Builder()
            .addInterceptor(HTTP_LOGGING_INTERCEPTOR)
            .addInterceptor(AUTH_INTERCEPTOR)
            .build();

    private static final Retrofit RETROFIT = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(OK_HTTP_CLIENT)
            .build();

    public static UserApi userApi() {
        return RETROFIT.create(UserApi.class);
    }
    public static PokemonApi pokemonApi() {
        return RETROFIT.create(PokemonApi.class);
    }
}
