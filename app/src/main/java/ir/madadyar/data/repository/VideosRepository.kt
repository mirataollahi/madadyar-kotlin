package ir.madadyar.data.repository

import ir.madadyar.data.api.ApiClient
import ir.madadyar.data.model.Video
import ir.madadyar.data.model.VideoCategory
import ir.madadyar.util.ErrorHandler
import retrofit2.HttpException
import java.io.IOException

class VideosRepository {
    private val apiService = ApiClient.apiService
    
    suspend fun getVideos(page: Int = 1): Result<List<Video>> {
        return try {
            val response = apiService.getVideos(page)
            if (response.isSuccessful && response.body() != null) {
                val apiResponse = response.body()!!
                if (apiResponse.code == 200 && apiResponse.status && apiResponse.data != null) {
                    val videosData = apiResponse.data!!
                    val paginatedVideos = videosData.videos
                    Result.success(paginatedVideos.data)
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
    
    suspend fun getVideoById(id: Int): Result<Video> {
        return try {
            val response = apiService.getVideoById(id)
            if (response.isSuccessful && response.body() != null) {
                val apiResponse = response.body()!!
                if (apiResponse.code == 200 && apiResponse.status && apiResponse.data != null) {
                    Result.success(apiResponse.data!!.video)
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
    
    suspend fun getVideosByCategory(catId: Int): Result<List<Video>> {
        return try {
            val response = apiService.getVideosByCategory(catId)
            if (response.isSuccessful && response.body() != null) {
                val apiResponse = response.body()!!
                if (apiResponse.code == 200 && apiResponse.status && apiResponse.data != null) {
                    val videosData = apiResponse.data!!
                    Result.success(videosData.data)
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
    
    suspend fun searchVideos(query: String): Result<List<Video>> {
        return try {
            val response = apiService.searchVideos(query)
            if (response.isSuccessful && response.body() != null) {
                val apiResponse = response.body()!!
                if (apiResponse.code == 200 && apiResponse.status && apiResponse.data != null) {
                    val videosData = apiResponse.data!!
                    Result.success(videosData.data)
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
    
    suspend fun getVideoCategories(): Result<List<VideoCategory>> {
        return try {
            val response = apiService.getVideoCategories()
            if (response.isSuccessful && response.body() != null) {
                val apiResponse = response.body()!!
                if (apiResponse.code == 200 && apiResponse.status && apiResponse.data != null) {
                    Result.success(apiResponse.data!!.categories)
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
    
    suspend fun getVideoCategoriesByParent(catId: Int): Result<List<VideoCategory>> {
        return try {
            val response = apiService.getVideoCategoriesByParent(catId)
            if (response.isSuccessful && response.body() != null) {
                val apiResponse = response.body()!!
                if (apiResponse.code == 200 && apiResponse.status && apiResponse.data != null) {
                    Result.success(apiResponse.data!!.categories)
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

