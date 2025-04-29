package com.example.diagnose_app.presentation.viewmodel.account

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.example.diagnose_app.data.datasource.remote.ApiService
import com.example.diagnose_app.data.models.AccountEntity
import com.example.diagnose_app.domain.repository.AccountRepository
import com.example.diagnose_app.domain.repository.FirebaseAuthRepository
import com.example.diagnose_app.domain.usecase.TokenRequest
import com.example.diagnose_app.domain.usecase.register.RegisterRequest
import com.example.diagnose_app.domain.usecase.register.RegisterUsecase

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val registerUsecase: RegisterUsecase,
    private val repository: AccountRepository,
    private val firebaseAuthRepository: FirebaseAuthRepository,
    private val apiService: ApiService
) : ViewModel() {

    private val _account = MutableStateFlow<AccountEntity?>(null)
    val account: StateFlow<AccountEntity?> = _account

    private val _userRole = MutableStateFlow<String?>(null)
    val userRole: StateFlow<String?> = _userRole

    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    private val _authState = MutableStateFlow<Result<String>?>(null)
    val authState: StateFlow<Result<String>?> = _authState

    private val _loginState = MutableStateFlow<Result<String>?>(null)
    val loginState: StateFlow<Result<String>?> = _loginState

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _accountList = MutableStateFlow<List<AccountEntity>>(emptyList())
    val accountList = _accountList

    private val _isDoctorRegistered = mutableStateOf<Boolean?>(null)
    val isDoctorRegistered: State<Boolean?> = _isDoctorRegistered

    fun checkDoctorRegistration(accountId: Int) {
        viewModelScope.launch {
            val result = repository.getPhysicianByAccountId(accountId)
            _isDoctorRegistered.value = result
        }
    }


    fun setAccount(account: AccountEntity) {
        _account.value = account
    }

    fun getAccountId(): Int? {
        return _account.value?.id
    }

    init {
        fetchAccount()
    }

    fun fetchAccount() {
        viewModelScope.launch {
            _isLoading.value = true
            _accountList.value = repository.getAllAccount()
            _isLoading.value = false
        }
    }

    fun registerAccount(accountRequest: RegisterRequest) {
        viewModelScope.launch {
            try {
                val response = registerUsecase.execute(accountRequest)
                if (response.isSuccessful) {
                    Log.d("AuthViewModel", "Server register success!")
                    _authState.value = Result.success("Đăng ký thành công!")
                    _userRole.value = accountRequest.role
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("AuthViewModel", "Server register failed: $errorBody")
                    _authState.value = Result.failure(Exception("Đăng ký thất bại: $errorBody"))
                }
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Exception: ${e.message}")
                _authState.value = Result.failure(e)
            }
        }
    }

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = repository.loginUser(email, password)
                Log.e("AuthViewModel", "response from server: $response")
                if (response != null) {
                    val account = AccountEntity(
                        id = response.account.id,
                        email = response.account.email,
                        role = response.account.role,
                        password = response.account.password ?: ""
                    )
                    repository.insertAccount(account)
                    setAccount(account)
                    _userRole.value = response.account.role
                    _loginState.value = Result.success("Đăng nhập thành công!")
                } else {
                    _loginState.value = Result.failure(Exception("Sai email hoặc mật khẩu"))
                }
            } catch (e: Exception) {
                _loginState.value = Result.failure(e)
            }
        }
    }


    fun fetchUserRole(uid: String) {
        FirebaseFirestore.getInstance().collection("users").document(uid)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val userRole = document.getString("role")
                    _userRole.value = userRole
                }
            }
            .addOnFailureListener { e ->
                Log.e("AuthViewModel", "⚠️ Lỗi khi lấy role: ${e.message}")
            }
    }

    fun resetLoginState() {
        _loginState.value = null
    }

    private fun fetchAccountInfoAndSaveToRoom(uid: String) {
        val docRef = FirebaseFirestore.getInstance().collection("users").document(uid)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val accountId = document.getLong("account_id")?.toInt() ?: return@addOnSuccessListener
                    val account = AccountEntity(
                        id = accountId,  // Lấy ID từ server để tránh trùng lặp
                        email = document.getString("email") ?: "",
                        role = document.getString("role") ?: "",
                        password = ""
                    )

                    viewModelScope.launch {
                        val existingAccount = repository.getAccountById(accountId)
                        if (existingAccount == null) {
                            repository.insertAccount(account)
                            Log.d("AuthViewModel", "Lưu tài khoản vào Room thành công!")
                        } else {
                            Log.d("AuthViewModel", "Tài khoản đã tồn tại, không cần lưu lại!")
                        }
                    }
                } else {
                    Log.e("AuthViewModel", "Không tìm thấy tài khoản với UID: $uid")
                }
            }
            .addOnFailureListener { e ->
                Log.e("AuthViewModel", "Lỗi lấy thông tin tài khoản từ Firestore: ${e.message}")
            }
    }


    fun saveAccountId(context: Context, accountId: Int) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().putInt("account_id", accountId).apply()
    }

    fun getAccountId(context: Context): Int? {
        val sharedPref = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        return sharedPref.getInt("account_id", -1).takeIf { it != -1 }
    }

}
