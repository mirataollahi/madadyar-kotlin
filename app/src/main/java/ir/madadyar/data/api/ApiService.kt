package ir.madadyar.data.api

import ir.madadyar.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    
    // ==================== AUTHENTICATION ENDPOINTS ====================
    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<ApiResponse<LoginResponseData>>
    
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<ApiResponse<LoginResponseData>>
    
    @POST("auth/verify-register")
    suspend fun verifyRegister(@Body request: VerifyRequest): Response<ApiResponse<VerifyResponseData>>
    
    @POST("auth/verify-login")
    suspend fun verifyLogin(@Body request: VerifyRequest): Response<ApiResponse<VerifyResponseData>>
    
    // ==================== BOOK ENDPOINTS ====================
    @GET("books")
    suspend fun getBooks(@Query("page") page: Int): Response<ApiResponse<BooksResponseData>>
    
    @GET("books/{id}")
    suspend fun getBookById(@Path("id") id: Int): Response<ApiResponse<BookDetailResponseData>>
    
    @GET("books/get-by-cat-id/{catId}")
    suspend fun getBooksByCategory(
        @Path("catId") catId: Int,
        @Query("page") page: Int
    ): Response<ApiResponse<BooksResponseData>>
    
    @GET("books/search/{q}")
    suspend fun searchBooks(
        @Path("q") query: String,
        @Query("page") page: Int
    ): Response<ApiResponse<BooksResponseData>>
    
    // ==================== VIDEO ENDPOINTS ====================
    @GET("videos")
    suspend fun getVideos(): Response<ApiResponse<VideosResponseData>>
    
    @GET("videos/{id}")
    suspend fun getVideoById(@Path("id") id: Int): Response<ApiResponse<VideoDetailResponseData>>
    
    @GET("videos/get-by-cat-id/{catId}")
    suspend fun getVideosByCategory(@Path("catId") catId: Int): Response<ApiResponse<VideosResponseData>>
    
    @GET("videos/search/{q}")
    suspend fun searchVideos(@Path("q") query: String): Response<ApiResponse<VideosResponseData>>
    
    // ==================== BOOK CATEGORY ENDPOINTS ====================
    @GET("book-categories")
    suspend fun getBookCategories(): Response<ApiResponse<CategoriesResponseData>>
    
    @GET("book-categories/main-categories")
    suspend fun getMainBookCategories(): Response<ApiResponse<CategoriesResponseData>>
    
    @GET("book-categories/get-by-cat-id/{catId}")
    suspend fun getBookCategoriesByParent(@Path("catId") catId: Int): Response<ApiResponse<CategoriesResponseData>>
    
    // ==================== VIDEO CATEGORY ENDPOINTS ====================
    @GET("video-categories")
    suspend fun getVideoCategories(): Response<ApiResponse<VideoCategoriesResponseData>>
    
    @GET("video-categories/get-by-cat-id/{catId}")
    suspend fun getVideoCategoriesByParent(@Path("catId") catId: Int): Response<ApiResponse<VideoCategoriesResponseData>>
}

