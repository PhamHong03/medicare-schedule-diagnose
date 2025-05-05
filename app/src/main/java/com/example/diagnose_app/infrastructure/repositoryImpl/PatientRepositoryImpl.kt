package com.example.diagnose_app.infrastructure.repositoryImpl

import android.util.Log
import com.example.diagnose_app.data.datasource.local.PatientDao
import com.example.diagnose_app.data.datasource.remote.PatientApiService
import com.example.diagnose_app.data.models.PatientEntity
import com.example.diagnose_app.domain.repository.PatientRepository


class PatientRepositoryImpl(
    private val patientDao: PatientDao,
    private val patientApiService: PatientApiService
) : PatientRepository{
    override suspend fun insertPatient(patientEntity: PatientEntity) {
        patientDao.insertPatient(patientEntity)
        try {
            val response = patientApiService.insertPatient(patientEntity)
            if (response.isSuccessful) {
                Log.d("PatientRepo", "Dữ liệu đã gửi lên flask")
            }else {
                Log.e("PatientRepo", "Lỗi dữ liệu lên server: ${response.message()}")
            }
        }catch (e:Exception) {
            Log.e("Patients", "Lỗi kết nối API: ${e}")
        }
    }

    override suspend fun getAllPatient(): List<PatientEntity> {
        return kotlin.runCatching {
            val patients = patientApiService.getAllPatients()
            patientDao.insertAll(patients)
            patients
        }.getOrElse {
            Log.e("PatientRepo", "Lỗi kết nối API: ${it.message}")
            patientDao.getAllPatient()
        }
    }

    override suspend fun deletePatient(id: Int) {
        try {
            val response = patientApiService.deletePatient(id)
            if(response.isSuccessful) {
                Log.d("PhysicianRepo", "Xóa thành công")
            }else{
                Log.d("PhysicianRepo", "Lỗi gửi dữ liệu lên server:  ${response.code()} - \${response.errorBody()?.string()")
            }
        }catch (e:Exception) {
            Log.e("PhysicianRepo", " Lỗi kết nối API: ${e.message}")
        }
    }

    override suspend fun getPatientByAccountId(accountId: Int): PatientEntity? {
        return try {
            val patient = patientApiService.getPatientByAccountId(accountId)
            patient?.let { patientDao.insertPatient(it) } // Chỉ lưu nếu không null
            patient
        } catch (e: Exception) {
            Log.e("PatientRepo", "Lỗi kết nối API: ${e.message}")
            patientDao.getPatientByAccountId(accountId) // Lấy dữ liệu từ cache
        }
    }
    override suspend fun getPatientById(patientId: Int): PatientEntity? {
        return patientDao.getPatientById(patientId)  // Gọi hàm Dao để lấy bệnh nhân
    }
}