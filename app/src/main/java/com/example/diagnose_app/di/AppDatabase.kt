package com.example.diagnose_app.di

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.diagnose_app.data.datasource.local.AccountDAO
import com.example.diagnose_app.data.datasource.local.CategoryDiseaseDao
import com.example.diagnose_app.data.datasource.local.DiseaseDao
import com.example.diagnose_app.data.datasource.local.EducationDao
import com.example.diagnose_app.data.datasource.local.PatientDao
import com.example.diagnose_app.data.datasource.local.PhysicianDao
import com.example.diagnose_app.data.datasource.local.RoomDao
import com.example.diagnose_app.data.datasource.local.SpecializationDao
import com.example.diagnose_app.data.models.AccountEntity
import com.example.diagnose_app.data.models.CategoryDiseaseEntity
import com.example.diagnose_app.data.models.DiseaseEntity
import com.example.diagnose_app.data.models.EducationEntity
import com.example.diagnose_app.data.models.PatientEntity
import com.example.diagnose_app.data.models.PhysicianEntity
import com.example.diagnose_app.data.models.RoomEntity
import com.example.diagnose_app.data.models.SpecializationEntity


@Database(
    entities = [
        AccountEntity::class,
        RoomEntity::class,
        EducationEntity::class,
        SpecializationEntity::class,
        PhysicianEntity::class,
        PatientEntity::class,
        CategoryDiseaseEntity::class,
        DiseaseEntity::class
    ],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun accountDao(): AccountDAO
    abstract fun educationDao(): EducationDao
    abstract fun specializationDao(): SpecializationDao
    abstract fun roomDao(): RoomDao
    abstract fun physicianDao(): PhysicianDao
    abstract fun patientDao(): PatientDao
    abstract fun categoryDiseaseDao() : CategoryDiseaseDao
    abstract fun diseaseDao(): DiseaseDao


    companion object {
        fun getDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "app_database"
            ).addMigrations().build()
        }
    }
}