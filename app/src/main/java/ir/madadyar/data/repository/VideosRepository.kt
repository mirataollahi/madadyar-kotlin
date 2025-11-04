package ir.madadyar.data.repository

import ir.madadyar.data.api.ApiClient
import ir.madadyar.data.model.Video
import ir.madadyar.data.model.VideoCategory
import ir.madadyar.util.ErrorHandler
import retrofit2.HttpException
import java.io.IOException

class VideosRepository {
    private val apiService = ApiClient.apiService
    
    suspend fun getVideos(): Result<List<Video>> {
        return try {
            val response = apiService.getVideos()
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
    
    suspend fun getVideoById(id: Int): Result<Video> {
        return try {
            val response = apiService.getVideoById(id)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!.data)
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
    
    suspend fun getVideosByCategory(catId: Int): Result<List<Video>> {
        return try {
            val response = apiService.getVideosByCategory(catId)
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
    
    suspend fun searchVideos(query: String): Result<List<Video>> {
        return try {
            val response = apiService.searchVideos(query)
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
    
    suspend fun getVideoCategories(): Result<List<VideoCategory>> {
        return try {
            val response = apiService.getVideoCategories()
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
    
    suspend fun getVideoCategoriesByParent(catId: Int): Result<List<VideoCategory>> {
        return try {
            val response = apiService.getVideoCategoriesByParent(catId)
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

