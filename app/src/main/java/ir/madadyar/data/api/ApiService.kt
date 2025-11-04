package ir.madadyar.data.api

import ir.madadyar.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    
    // User endpoints
    @POST("register")
    suspend fun register(@Body request: RegisterRequest): Response<String>
    
    @POST("login")
    suspend fun login(@Body request: LoginRequest): Response<String>
    
    @POST("verify-register")
    suspend fun verifyRegister(@Body request: VerifyRequest): Response<String>
    
    @POST("verify-login")
    suspend fun verifyLogin(@Body request: VerifyRequest): Response<String>
    
    // Book endpoints
    @GET("books")
    suspend fun getBooks(@Query("page") page: Int): Response<BooksResponse>
    
    @GET("books/{id}")
    suspend fun getBookById(@Path("id") id: Int): Response<BookDetailResponse>
    
    @GET("books/get-by-cat-id/{catId}")
    suspend fun getBooksByCategory(
        @Path("catId") catId: Int,
        @Query("page") page: Int
    ): Response<BooksResponse>
    
    @GET("books/search/{query}")
    suspend fun searchBooks(
        @Path("query") query: String,
        @Query("page") page: Int
    ): Response<BooksResponse>
    
    // Video endpoints
    @GET("videos")
    suspend fun getVideos(): Response<List<Video>>
    
    @GET("videos/{id}")
    suspend fun getVideoById(@Path("id") id: Int): Response<VideoDetailResponse>
    
    @GET("videos/get-by-cat-id/{catId}")
    suspend fun getVideosByCategory(@Path("catId") catId: Int): Response<List<Video>>
    
    @GET("videos/search/{query}")
    suspend fun searchVideos(@Path("query") query: String): Response<List<Video>>
    
    // Category endpoints
    @GET("book-categories")
    suspend fun getBookCategories(): Response<List<BookCategory>>
    
    @GET("book-categories/main")
    suspend fun getMainBookCategories(): Response<List<BookCategory>>
    
    @GET("book-categories/{catId}")
    suspend fun getBookCategoriesByParent(@Path("catId") catId: Int): Response<List<BookCategory>>
    
    @GET("video-categories")
    suspend fun getVideoCategories(): Response<List<VideoCategory>>
    
    @GET("video-categories/{catId}")
    suspend fun getVideoCategoriesByParent(@Path("catId") catId: Int): Response<List<VideoCategory>>
}

