package com.example.diagnose_app.presentation.viewmodel.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diagnose_app.data.models.DiseaseEntity
import com.example.diagnose_app.domain.repository.DiseaseRepository

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiseaseViewModel @Inject constructor(
    private val diseaseRepository: DiseaseRepository
): ViewModel() {
    private val _disease = MutableStateFlow<List<DiseaseEntity>>(emptyList())
    val disease = _disease

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        fetchDisease()
    }
    fun fetchDisease(){
        viewModelScope.launch {
            _isLoading.value = true
            _disease.value = diseaseRepository.getAllDisease()
            _isLoading.value = false
        }
    }

}