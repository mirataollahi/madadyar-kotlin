package ir.madadyar.data.model

// Base API Response Structure
data class ApiResponse<T>(
    val status: Boolean,
    val code: Int,
    val message: String,
    val data: T?
)

// ==================== AUTHENTICATION RESPONSES ====================
data class LoginResponseData(
    val user: User,
    val code: Int
)

data class VerifyResponseData(
    val user: User,
    val token: String
)

// ==================== BOOK RESPONSES ====================
data class BooksResponseData(
    val data: List<Book>,
    val totalPage: Int
)

data class BookDetailResponseData(
    val book: Book
)

// ==================== VIDEO RESPONSES ====================
data class VideosResponseData(
    val data: List<Video>
)

data class VideoDetailResponseData(
    val video: Video
)

// ==================== CATEGORY RESPONSES ====================
data class CategoriesResponseData(
    val categories: List<BookCategory>
)

data class VideoCategoriesResponseData(
    val categories: List<VideoCategory>
)

// ==================== REQUEST MODELS ====================
data class LoginRequest(
    val phone_number: String
)

data class RegisterRequest(
    val username: String,
    val phone_number: String
)

data class VerifyRequest(
    val phone_number: String,
    val verification_code: String
)

