package com.example.diagnose_app.infrastructure.repositoryImpl

import android.util.Log
import com.example.diagnose_app.data.datasource.local.CategoryDiseaseDao
import com.example.diagnose_app.data.datasource.remote.CategoryDiseaseApiService
import com.example.diagnose_app.data.models.CategoryDiseaseEntity
import com.example.diagnose_app.domain.repository.CategoryDiseaseRepository
import javax.inject.Inject

class CategoryDiseaseRepositoryImpl @Inject constructor(
    private val categoryDiseaseDao: CategoryDiseaseDao,
    private val categoryDiseaseApiService: CategoryDiseaseApiService
) : CategoryDiseaseRepository{
    override suspend fun getAllCategoryDisease(): List<CategoryDiseaseEntity> {
        return runCatching {
            val categoryDisease = categoryDiseaseApiService.getAllCategoryDisease()
            Log.d("DiseaseRepo", "Dữ liệu từ API: $categoryDisease")
            categoryDiseaseDao.insertAll(categoryDisease)
            categoryDisease
        }.getOrElse {
            Log.e("CategoryRepo", "Lỗi kết nối API: ${it.message}")
            categoryDiseaseDao.getAllCategoryDisease()
        }
    }
}