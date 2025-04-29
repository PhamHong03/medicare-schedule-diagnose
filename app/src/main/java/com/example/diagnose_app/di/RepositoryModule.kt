package com.example.diagnose_app.di

import com.example.diagnose_app.data.datasource.remote.ApiService
import com.example.diagnose_app.domain.repository.AccountRepository
import com.example.diagnose_app.domain.repository.FirebaseAuthRepository
import com.example.diagnose_app.infrastructure.repositoryImpl.AccountRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAccountRepository(
        database: AppDatabase,
        apiService: ApiService,
        firebaseAuthRepository: FirebaseAuthRepository
    ): AccountRepository {
        return AccountRepositoryImpl(database.accountDao(), apiService, firebaseAuthRepository)
    }
}