package com.example.demonotes.di

import com.example.demonotes.api.AuthInterceptor
import com.example.demonotes.api.NoteApi
import com.example.demonotes.api.UserApi
import com.example.demonotes.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.Retrofit.Builder
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofitBuilder():Retrofit.Builder{
         return Retrofit.Builder()
             .addConverterFactory(GsonConverterFactory.create())
             .baseUrl(BASE_URL)

    }

    @Singleton
    @Provides
    fun provideUserResponse(retrofitBuilder: Retrofit.Builder):UserApi{
        return retrofitBuilder.build().create(UserApi::class.java)
    }

    @Singleton
    @Provides
    fun provideOkhttpClient(authInterceptor: AuthInterceptor):OkHttpClient{
        return OkHttpClient.Builder().addNetworkInterceptor(authInterceptor).build()

    }
    @Singleton
    @Provides
    fun provideNotesApi(retrofitBuilder: Builder,okHttpClient: OkHttpClient):NoteApi{
        return retrofitBuilder
            .client(okHttpClient)
            .build()
            .create(NoteApi::class.java)
    }
}