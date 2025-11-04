package ir.madadyar.data.repository

import ir.madadyar.data.api.ApiClient
import ir.madadyar.data.model.LoginRequest
import ir.madadyar.data.model.RegisterRequest
import ir.madadyar.data.model.VerifyRequest
import ir.madadyar.util.ErrorHandler
import retrofit2.HttpException
import java.io.IOException

class AuthRepository {
    private val apiService = ApiClient.apiService
    
    suspend fun register(username: String, phoneNumber: String): Result<String> {
        return try {
            val response = apiService.register(RegisterRequest(username, phoneNumber))
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                val errorMsg = ErrorHandler.getErrorMessageFromStatusCode(response.code())
                Result.failure(Exception(errorMsg))
            }
        } catch (e: HttpException) {
            val errorMsg = ErrorHandler.getErrorMessageFromStatusCode(e.code())
            Result.failure(Exception(errorMsg))
        } catch (e: IOException) {
            Result.failure(Exception(ErrorHandler.getErrorMessage(e)))
        } catch (e: Exception) {
            Result.failure(Exception(ErrorHandler.getErrorMessage(e)))
        }
    }
    
    suspend fun login(phoneNumber: String): Result<String> {
        return try {
            val response = apiService.login(LoginRequest(phoneNumber))
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                val errorMsg = ErrorHandler.getErrorMessageFromStatusCode(response.code())
                Result.failure(Exception(errorMsg))
            }
        } catch (e: HttpException) {
            val errorMsg = ErrorHandler.getErrorMessageFromStatusCode(e.code())
            Result.failure(Exception(errorMsg))
        } catch (e: IOException) {
            Result.failure(Exception(ErrorHandler.getErrorMessage(e)))
        } catch (e: Exception) {
            Result.failure(Exception(ErrorHandler.getErrorMessage(e)))
        }
    }
    
    suspend fun verifyRegister(phoneNumber: String, code: String): Result<String> {
        return try {
            val response = apiService.verifyRegister(VerifyRequest(phoneNumber, code))
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                val errorMsg = ErrorHandler.getErrorMessageFromStatusCode(response.code())
                Result.failure(Exception(errorMsg))
            }
        } catch (e: HttpException) {
            val errorMsg = ErrorHandler.getErrorMessageFromStatusCode(e.code())
            Result.failure(Exception(errorMsg))
        } catch (e: IOException) {
            Result.failure(Exception(ErrorHandler.getErrorMessage(e)))
        } catch (e: Exception) {
            Result.failure(Exception(ErrorHandler.getErrorMessage(e)))
        }
    }
    
    suspend fun verifyLogin(phoneNumber: String, code: String): Result<String> {
        return try {
            val response = apiService.verifyLogin(VerifyRequest(phoneNumber, code))
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                val errorMsg = ErrorHandler.getErrorMessageFromStatusCode(response.code())
                Result.failure(Exception(errorMsg))
            }
        } catch (e: HttpException) {
            val errorMsg = ErrorHandler.getErrorMessageFromStatusCode(e.code())
            Result.failure(Exception(errorMsg))
        } catch (e: IOException) {
            Result.failure(Exception(ErrorHandler.getErrorMessage(e)))
        } catch (e: Exception) {
            Result.failure(Exception(ErrorHandler.getErrorMessage(e)))
        }
    }
}

