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

// ==================== PAGINATION MODELS ====================
data class PaginationLink(
    val url: String?,
    val label: String,
    val active: Boolean
)

data class PaginatedBooks(
    val current_page: Int,
    val from: Int,
    val last_page: Int,
    val per_page: Int,
    val to: Int,
    val total: Int,
    val first_page_url: String,
    val last_page_url: String,
    val next_page_url: String?,
    val prev_page_url: String?,
    val path: String,
    val links: List<PaginationLink>,
    val data: List<Book>
)

data class PaginatedVideos(
    val current_page: Int,
    val from: Int,
    val last_page: Int,
    val per_page: Int,
    val to: Int,
    val total: Int,
    val first_page_url: String,
    val last_page_url: String,
    val next_page_url: String?,
    val prev_page_url: String?,
    val path: String,
    val links: List<PaginationLink>,
    val data: List<Video>
)

// ==================== BOOK RESPONSES ====================
data class BooksResponseData(
    val books: PaginatedBooks
)

data class BooksListResponseData(
    val data: List<Book>,
    val totalPage: Int
)

data class BookDetailResponseData(
    val book: Book
)

// ==================== VIDEO RESPONSES ====================
data class VideosResponseData(
    val videos: PaginatedVideos
)

data class VideosListResponseData(
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

