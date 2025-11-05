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
                val apiResponse = response.body()!!
                if (apiResponse.code == 200 && apiResponse.status && apiResponse.data != null) {
                    try {
                        val booksData = apiResponse.data!!
                        val paginatedBooks = booksData.books
                        if (paginatedBooks != null) {
                            val booksList = paginatedBooks.data ?: emptyList()
                            val lastPage = paginatedBooks.getLastPage()
                            Result.success(Pair(booksList, lastPage))
                        } else {
                            Result.failure(Exception("ساختار پاسخ سرور نامعتبر است"))
                        }
                    } catch (e: Exception) {
                        Result.failure(Exception(ErrorHandler.getErrorMessage(e)))
                    }
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
    
    suspend fun getBookById(id: Int): Result<Book> {
        return try {
            val response = apiService.getBookById(id)
            if (response.isSuccessful && response.body() != null) {
                val apiResponse = response.body()!!
                if (apiResponse.code == 200 && apiResponse.status && apiResponse.data != null) {
                    try {
                        val book = apiResponse.data!!.book
                        if (book != null) {
                            Result.success(book)
                        } else {
                            Result.failure(Exception("اطلاعات کتاب یافت نشد"))
                        }
                    } catch (e: Exception) {
                        Result.failure(Exception(ErrorHandler.getErrorMessage(e)))
                    }
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
    
    suspend fun getBooksByCategory(catId: Int, page: Int): Result<Pair<List<Book>, Int>> {
        return try {
            val response = apiService.getBooksByCategory(catId, page)
            if (response.isSuccessful && response.body() != null) {
                val apiResponse = response.body()!!
                if (apiResponse.code == 200 && apiResponse.status && apiResponse.data != null) {
                    try {
                        val booksData = apiResponse.data!!
                        Result.success(Pair(booksData.data ?: emptyList(), booksData.totalPage ?: 1))
                    } catch (e: Exception) {
                        Result.failure(Exception(ErrorHandler.getErrorMessage(e)))
                    }
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
    
    suspend fun searchBooks(query: String, page: Int): Result<Pair<List<Book>, Int>> {
        return try {
            val response = apiService.searchBooks(query, page)
            if (response.isSuccessful && response.body() != null) {
                val apiResponse = response.body()!!
                if (apiResponse.code == 200 && apiResponse.status && apiResponse.data != null) {
                    try {
                        val booksData = apiResponse.data!!
                        Result.success(Pair(booksData.data ?: emptyList(), booksData.totalPage ?: 1))
                    } catch (e: Exception) {
                        Result.failure(Exception(ErrorHandler.getErrorMessage(e)))
                    }
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
    
    suspend fun getBookCategories(): Result<List<BookCategory>> {
        return try {
            val response = apiService.getBookCategories()
            if (response.isSuccessful && response.body() != null) {
                val apiResponse = response.body()!!
                if (apiResponse.code == 200 && apiResponse.status && apiResponse.data != null) {
                    try {
                        Result.success(apiResponse.data!!.categories ?: emptyList())
                    } catch (e: Exception) {
                        Result.failure(Exception(ErrorHandler.getErrorMessage(e)))
                    }
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
    
    suspend fun getMainBookCategories(): Result<List<BookCategory>> {
        return try {
            val response = apiService.getMainBookCategories()
            if (response.isSuccessful && response.body() != null) {
                val apiResponse = response.body()!!
                if (apiResponse.code == 200 && apiResponse.status && apiResponse.data != null) {
                    try {
                        Result.success(apiResponse.data!!.categories ?: emptyList())
                    } catch (e: Exception) {
                        Result.failure(Exception(ErrorHandler.getErrorMessage(e)))
                    }
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
    
    suspend fun getBookCategoriesByParent(catId: Int): Result<List<BookCategory>> {
        return try {
            val response = apiService.getBookCategoriesByParent(catId)
            if (response.isSuccessful && response.body() != null) {
                val apiResponse = response.body()!!
                if (apiResponse.code == 200 && apiResponse.status && apiResponse.data != null) {
                    try {
                        Result.success(apiResponse.data!!.categories ?: emptyList())
                    } catch (e: Exception) {
                        Result.failure(Exception(ErrorHandler.getErrorMessage(e)))
                    }
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

