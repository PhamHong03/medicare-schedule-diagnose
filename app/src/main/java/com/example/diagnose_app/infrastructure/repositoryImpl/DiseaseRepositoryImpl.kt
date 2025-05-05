package com.example.diagnose_app.infrastructure.repositoryImpl

import android.util.Log
import com.example.diagnose_app.data.datasource.local.DiseaseDao
import com.example.diagnose_app.data.datasource.remote.DiseaseApiService
import com.example.diagnose_app.data.models.DiseaseEntity
import com.example.diagnose_app.domain.repository.DiseaseRepository

import javax.inject.Inject

class DiseaseRepositoryImpl @Inject constructor(
    private val diseaseDao: DiseaseDao,
    private val diseaseApiService: DiseaseApiService
): DiseaseRepository{
    override suspend fun getAllDisease(): List<DiseaseEntity> {
        return kotlin.runCatching {
            val diseases = diseaseApiService.getAllDisease()
            Log.d("DiseaseRepo", "Dữ liệu từ API: $diseases")
            diseaseDao.insertAll(diseases)
            diseases
        }.getOrElse {
            Log.e("DiseaseRepo", "Lỗi kết nối API: ${it.message}")
            val localDiseases = diseaseDao.getAllDisease()
            Log.d("DiseaseRepo", "Dữ liệu từ RoomDB: $localDiseases") // ✅ Log dữ liệu từ local
            localDiseases
        }
    }

    override suspend fun getDiseaseById(id: Int): DiseaseEntity {
        return diseaseDao.getLocalDiseaseById(id) ?: kotlin.runCatching {
            val response = diseaseApiService.getDiseaseById(id)
            if (response.isSuccessful) {
                response.body()?.also { diseaseDao.insertAll(listOf(it)) } ?: throw Exception("Không tìm thấy bệnh")
            } else {
                throw Exception("Lỗi API: ${response.code()}")
            }
        }.getOrElse {
            Log.e("DiseaseRepository", "Lỗi khi lấy disease by ID: ${it.message}")
            throw Exception("Không thể lấy bệnh")
        }
    }


}