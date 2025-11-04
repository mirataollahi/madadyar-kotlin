package ir.madadyar.data.repository

import ir.madadyar.data.api.ApiClient
import ir.madadyar.data.model.Book
import ir.madadyar.data.model.BookCategory
import ir.madadyar.util.ErrorHandler
import retrofit2.HttpException
import java.io.IOException

class BooksRepository {
    private val apiService = ApiClient.apiService
    
    suspend fun getBooks(page: Int): Result<Pair<List<Book>, Int>> {
        return try {
            val response = apiService.getBooks(page)
            if (response.isSuccessful && response.body() != null) {
                val body = response.body()!!
                Result.success(Pair(body.data, body.totalPage))
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
    
    suspend fun getBookById(id: Int): Result<Book> {
        return try {
            val response = apiService.getBookById(id)
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
    
    suspend fun getBooksByCategory(catId: Int, page: Int): Result<Pair<List<Book>, Int>> {
        return try {
            val response = apiService.getBooksByCategory(catId, page)
            if (response.isSuccessful && response.body() != null) {
                val body = response.body()!!
                Result.success(Pair(body.data, body.totalPage))
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
    
    suspend fun searchBooks(query: String, page: Int): Result<Pair<List<Book>, Int>> {
        return try {
            val response = apiService.searchBooks(query, page)
            if (response.isSuccessful && response.body() != null) {
                val body = response.body()!!
                Result.success(Pair(body.data, body.totalPage))
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
    
    suspend fun getBookCategories(): Result<List<BookCategory>> {
        return try {
            val response = apiService.getBookCategories()
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
    
    suspend fun getMainBookCategories(): Result<List<BookCategory>> {
        return try {
            val response = apiService.getMainBookCategories()
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
    
    suspend fun getBookCategoriesByParent(catId: Int): Result<List<BookCategory>> {
        return try {
            val response = apiService.getBookCategoriesByParent(catId)
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

