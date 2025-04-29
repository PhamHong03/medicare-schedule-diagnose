package com.example.diagnose_app.di

import com.example.diagnose_app.data.datasource.remote.ApiService
import com.example.diagnose_app.data.datasource.remote.EducationApiService
import com.example.diagnose_app.data.datasource.remote.PhysicianApiService
import com.example.diagnose_app.data.datasource.remote.RoomApiService
import com.example.diagnose_app.data.datasource.remote.SpecializationApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private val BASE_URL = "http://192.168.1.5:5000"

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder ()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().build())
            .build()
    }
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
    @Provides
    @Singleton
    fun provideEducationService(retrofit: Retrofit): EducationApiService {
        return retrofit.create(EducationApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideSpecializationService(retrofit: Retrofit): SpecializationApiService {
        return retrofit.create(SpecializationApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRoomService(retrofit: Retrofit): RoomApiService {
        return retrofit.create(RoomApiService::class.java)
    }

    @Provides
    @Singleton
    fun providePhysicianService(retrofit: Retrofit): PhysicianApiService {
        return retrofit.create(PhysicianApiService::class.java)
    }

}