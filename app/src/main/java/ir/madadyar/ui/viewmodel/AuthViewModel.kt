package ir.madadyar.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import ir.madadyar.data.repository.AuthRepository
import ir.madadyar.util.AuthManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(application: Application) : AndroidViewModel(application) {
    private val authRepository = AuthRepository()
    
    private val _isAuthenticated = MutableStateFlow<Boolean?>(null)
    val isAuthenticated: StateFlow<Boolean?> = _isAuthenticated.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()
    
    private val _loginSuccess = MutableStateFlow<String?>(null)
    val loginSuccess: StateFlow<String?> = _loginSuccess.asStateFlow()
    
    private val _registerSuccess = MutableStateFlow<String?>(null)
    val registerSuccess: StateFlow<String?> = _registerSuccess.asStateFlow()
    
    init {
        checkAuth()
    }
    
    fun checkAuth() {
        viewModelScope.launch {
            _isAuthenticated.value = AuthManager.isAuthenticated(getApplication())
        }
    }
    
    fun register(username: String, phoneNumber: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            
            val result = authRepository.register(username, phoneNumber)
            result.fold(
                onSuccess = { (user, verificationCode) ->
                    // Store user and verification code if needed
                    _registerSuccess.value = phoneNumber
                    _isLoading.value = false
                },
                onFailure = { exception ->
                    _errorMessage.value = exception.message ?: "خطایی رخ داده است"
                    _isLoading.value = false
                }
            )
        }
    }
    
    fun login(phoneNumber: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            
            val result = authRepository.login(phoneNumber)
            result.fold(
                onSuccess = { (user, verificationCode) ->
                    // Store user and verification code if needed
                    _loginSuccess.value = phoneNumber
                    _isLoading.value = false
                },
                onFailure = { exception ->
                    _errorMessage.value = exception.message ?: "خطایی رخ داده است"
                    _isLoading.value = false
                }
            )
        }
    }
    
    fun verifyRegister(phoneNumber: String, code: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            
            val result = authRepository.verifyRegister(phoneNumber, code)
            result.fold(
                onSuccess = { (user, token) ->
                    AuthManager.saveToken(getApplication(), token)
                    _isAuthenticated.value = true
                    _isLoading.value = false
                },
                onFailure = { exception ->
                    _errorMessage.value = exception.message ?: "کد وارد شده صحیح نمیباشد"
                    _isLoading.value = false
                }
            )
        }
    }
    
    fun verifyLogin(phoneNumber: String, code: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            
            val result = authRepository.verifyLogin(phoneNumber, code)
            result.fold(
                onSuccess = { (user, token) ->
                    AuthManager.saveToken(getApplication(), token)
                    _isAuthenticated.value = true
                    _isLoading.value = false
                },
                onFailure = { exception ->
                    _errorMessage.value = exception.message ?: "کد وارد شده صحیح نمیباشد"
                    _isLoading.value = false
                }
            )
        }
    }
    
    fun clearError() {
        _errorMessage.value = null
    }
    
    fun clearSuccess() {
        _loginSuccess.value = null
        _registerSuccess.value = null
    }
}

