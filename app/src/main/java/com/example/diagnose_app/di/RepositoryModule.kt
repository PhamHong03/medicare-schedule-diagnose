package com.example.diagnose_app.di

import com.example.diagnose_app.data.datasource.remote.ApiService
import com.example.diagnose_app.data.datasource.remote.CategoryDiseaseApiService
import com.example.diagnose_app.data.datasource.remote.DiseaseApiService
import com.example.diagnose_app.data.datasource.remote.EducationApiService
import com.example.diagnose_app.data.datasource.remote.PatientApiService
import com.example.diagnose_app.data.datasource.remote.PhysicianApiService
import com.example.diagnose_app.data.datasource.remote.RoomApiService
import com.example.diagnose_app.data.datasource.remote.SpecializationApiService
import com.example.diagnose_app.domain.repository.AccountRepository
import com.example.diagnose_app.domain.repository.CategoryDiseaseRepository
import com.example.diagnose_app.domain.repository.DiseaseRepository
import com.example.diagnose_app.domain.repository.EducationRepository
import com.example.diagnose_app.domain.repository.FirebaseAuthRepository
import com.example.diagnose_app.domain.repository.PatientRepository
import com.example.diagnose_app.domain.repository.PhysicianRepository
import com.example.diagnose_app.domain.repository.RoomRepository
import com.example.diagnose_app.domain.repository.SpecializationRepository
import com.example.diagnose_app.infrastructure.repositoryImpl.AccountRepositoryImpl
import com.example.diagnose_app.infrastructure.repositoryImpl.CategoryDiseaseRepositoryImpl
import com.example.diagnose_app.infrastructure.repositoryImpl.DiseaseRepositoryImpl
import com.example.diagnose_app.infrastructure.repositoryImpl.EducationRepositoryImpl
import com.example.diagnose_app.infrastructure.repositoryImpl.PatientRepositoryImpl
import com.example.diagnose_app.infrastructure.repositoryImpl.PhysicianRepositoryImpl
import com.example.diagnose_app.infrastructure.repositoryImpl.RoomRepositoryImpl
import com.example.diagnose_app.infrastructure.repositoryImpl.SpecializationRepositoryImpl
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


    @Provides
    @Singleton
    fun provideEducationRepository(
        database: AppDatabase,
        educationService: EducationApiService
    ): EducationRepository{
        return EducationRepositoryImpl(database.educationDao(), educationService)
    }

    @Provides
    @Singleton
    fun provideSpecializationRepository(
        database: AppDatabase,
        specializationApiService: SpecializationApiService
    ): SpecializationRepository {
        return SpecializationRepositoryImpl(database.specializationDao(), specializationApiService)
    }
    @Provides
    @Singleton
    fun providedRoomRepository(
        database: AppDatabase,
        roomApiService: RoomApiService
    ): RoomRepository {
        return RoomRepositoryImpl(database.roomDao(), roomApiService)
    }


    @Provides
    @Singleton
    fun providePhysicianRepository(
        database: AppDatabase,
        physicianApiService: PhysicianApiService
    ): PhysicianRepository {
        return PhysicianRepositoryImpl(database.physicianDao(), physicianApiService)
    }
    @Provides
    @Singleton
    fun providePatientRepository(
        database: AppDatabase,
        patientApiService: PatientApiService
    ): PatientRepository {
        return PatientRepositoryImpl(database.patientDao(),patientApiService)
    }

    @Provides
    @Singleton
    fun provideCategoryDiseaseRepository(
        database: AppDatabase,
        categoryDiseaseApiService: CategoryDiseaseApiService
    ): CategoryDiseaseRepository {
        return CategoryDiseaseRepositoryImpl(database.categoryDiseaseDao(), categoryDiseaseApiService)
    }

    @Provides
    @Singleton
    fun provideDiseaseRepository(
        database: AppDatabase,
        diseaseApiService: DiseaseApiService
    ): DiseaseRepository {
        return DiseaseRepositoryImpl(database.diseaseDao(), diseaseApiService)
    }

}