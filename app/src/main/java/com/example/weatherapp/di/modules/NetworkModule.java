package com.example.weatherapp.di.modules;



import android.content.Context;

import com.example.weatherapp.remote.WeatherService;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.logging.HttpLoggingInterceptor;

@Module(includes = { ViewModelModule.class,  ContextModule.class })
public abstract class NetworkModule {

    @Provides
    static public HttpLoggingInterceptor provideInterceptor()  {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.level(HttpLoggingInterceptor.Level.BODY);

        return logging;
    }


    @Provides
    static public Cache provideCache(File cacheFile ){
        return new Cache(cacheFile, 10 * 1000 * 1000); //10MB Cahe
    }


    @Provides
    static public File provideCacheFile(@Named("application.context") Context context )  {
        return new File(context.getExternalCacheDir(), "okhttp_cache");
    }



    @Provides
    static public OkHttpClient provideOkHttpClient(HttpLoggingInterceptor logging, Cache cache ){

        return new OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .cache(cache)
            .build();
    }



    @Provides
    @Singleton
    //http://api.openweathermap.org/data/2.5/weather?lat=35&lon=139&appid=b394d23c308439258c1981c22a2c73b2
    static Retrofit provideRetrofit( ) {
        return new Retrofit.Builder()
                .client(new OkHttpClient().newBuilder()
                        .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                        .build())
                //.baseUrl("https://jsonplaceholder.typicode.com")
                .baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    static WeatherService provideWeatherService(Retrofit retrofit) {
        return retrofit.create(WeatherService.class);
    }


}
