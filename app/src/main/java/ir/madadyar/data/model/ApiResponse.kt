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
    val active: Boolean? = false
)

data class PaginatedBooks(
    val current_page: Int? = null,
    val from: Int? = null,
    val last_page: Int? = null,
    val per_page: Int? = null,
    val to: Int? = null,
    val total: Int? = null,
    val first_page_url: String? = null,
    val last_page_url: String? = null,
    val next_page_url: String? = null,
    val prev_page_url: String? = null,
    val path: String? = null,
    val links: List<PaginationLink>? = null,
    val data: List<Book>? = null
) {
    // Helper to get last_page safely
    fun getLastPage(): Int = last_page ?: 1
}

data class PaginatedVideos(
    val current_page: Int? = null,
    val from: Int? = null,
    val last_page: Int? = null,
    val per_page: Int? = null,
    val to: Int? = null,
    val total: Int? = null,
    val first_page_url: String? = null,
    val last_page_url: String? = null,
    val next_page_url: String? = null,
    val prev_page_url: String? = null,
    val path: String? = null,
    val links: List<PaginationLink>? = null,
    val data: List<Video>? = null
)

// ==================== BOOK RESPONSES ====================
data class BooksResponseData(
    val books: PaginatedBooks? = null
)

data class BooksListResponseData(
    val data: List<Book>? = null,
    val totalPage: Int? = null
)

data class BookDetailResponseData(
    val book: Book? = null
)

// ==================== VIDEO RESPONSES ====================
data class VideosResponseData(
    val videos: PaginatedVideos? = null
)

data class VideosListResponseData(
    val data: List<Video>? = null
)

data class VideoDetailResponseData(
    val video: Video? = null
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

