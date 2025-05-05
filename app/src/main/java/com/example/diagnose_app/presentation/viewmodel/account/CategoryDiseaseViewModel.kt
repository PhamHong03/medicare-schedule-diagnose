package com.example.diagnose_app.presentation.viewmodel.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diagnose_app.data.models.CategoryDiseaseEntity
import com.example.diagnose_app.domain.repository.CategoryDiseaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryDiseaseViewModel @Inject constructor(
    private val categoryDiseaseRepository: CategoryDiseaseRepository
): ViewModel(){
    private val _categoryDisease = MutableStateFlow<List<CategoryDiseaseEntity>>(emptyList())
    val categoryDisease = _categoryDisease

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        fetchCategoryDisease()
    }
    fun fetchCategoryDisease(){
        viewModelScope.launch {
            _isLoading.value = true
            _categoryDisease.value = categoryDiseaseRepository.getAllCategoryDisease()
            _isLoading.value = false
        }
    }
}