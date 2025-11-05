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
    
    suspend fun register(username: String, phoneNumber: String): Result<Pair<ir.madadyar.data.model.User, Int>> {
        return try {
            val response = apiService.register(RegisterRequest(username, phoneNumber))
            if (response.isSuccessful && response.body() != null) {
                val apiResponse = response.body()!!
                if (apiResponse.code == 200 && apiResponse.status && apiResponse.data != null) {
                    val data = apiResponse.data!!
                    Result.success(Pair(data.user, data.code))
                } else {
                    Result.failure(Exception(apiResponse.message))
                }
            } else {
                Result.failure(Exception("خطا در ارتباط با سرور"))
            }
        } catch (e: HttpException) {
            Result.failure(Exception(ErrorHandler.getErrorMessage(e)))
        } catch (e: IOException) {
            Result.failure(Exception(ErrorHandler.getErrorMessage(e)))
        } catch (e: Exception) {
            Result.failure(Exception(ErrorHandler.getErrorMessage(e)))
        }
    }
    
    suspend fun login(phoneNumber: String): Result<Pair<ir.madadyar.data.model.User, Int>> {
        return try {
            val response = apiService.login(LoginRequest(phoneNumber))
            if (response.isSuccessful && response.body() != null) {
                val apiResponse = response.body()!!
                if (apiResponse.code == 200 && apiResponse.status && apiResponse.data != null) {
                    val data = apiResponse.data!!
                    Result.success(Pair(data.user, data.code))
                } else {
                    Result.failure(Exception(apiResponse.message))
                }
            } else {
                Result.failure(Exception("خطا در ارتباط با سرور"))
            }
        } catch (e: HttpException) {
            Result.failure(Exception(ErrorHandler.getErrorMessage(e)))
        } catch (e: IOException) {
            Result.failure(Exception(ErrorHandler.getErrorMessage(e)))
        } catch (e: Exception) {
            Result.failure(Exception(ErrorHandler.getErrorMessage(e)))
        }
    }
    
    suspend fun verifyRegister(phoneNumber: String, code: String): Result<Pair<ir.madadyar.data.model.User, String>> {
        return try {
            val response = apiService.verifyRegister(VerifyRequest(phoneNumber, code))
            if (response.isSuccessful && response.body() != null) {
                val apiResponse = response.body()!!
                if (apiResponse.code == 200 && apiResponse.status && apiResponse.data != null) {
                    val data = apiResponse.data!!
                    Result.success(Pair(data.user, data.token))
                } else {
                    Result.failure(Exception(apiResponse.message))
                }
            } else {
                Result.failure(Exception("خطا در ارتباط با سرور"))
            }
        } catch (e: HttpException) {
            Result.failure(Exception(ErrorHandler.getErrorMessage(e)))
        } catch (e: IOException) {
            Result.failure(Exception(ErrorHandler.getErrorMessage(e)))
        } catch (e: Exception) {
            Result.failure(Exception(ErrorHandler.getErrorMessage(e)))
        }
    }
    
    suspend fun verifyLogin(phoneNumber: String, code: String): Result<Pair<ir.madadyar.data.model.User, String>> {
        return try {
            val response = apiService.verifyLogin(VerifyRequest(phoneNumber, code))
            if (response.isSuccessful && response.body() != null) {
                val apiResponse = response.body()!!
                if (apiResponse.code == 200 && apiResponse.status && apiResponse.data != null) {
                    val data = apiResponse.data!!
                    Result.success(Pair(data.user, data.token))
                } else {
                    Result.failure(Exception(apiResponse.message))
                }
            } else {
                Result.failure(Exception("خطا در ارتباط با سرور"))
            }
        } catch (e: HttpException) {
            Result.failure(Exception(ErrorHandler.getErrorMessage(e)))
        } catch (e: IOException) {
            Result.failure(Exception(ErrorHandler.getErrorMessage(e)))
        } catch (e: Exception) {
            Result.failure(Exception(ErrorHandler.getErrorMessage(e)))
        }
    }
}

