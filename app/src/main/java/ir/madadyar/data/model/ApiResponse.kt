package ir.madadyar.data.model

data class BooksResponse(
    val data: List<Book>,
    val totalPage: Int
)

data class VideoResponse(
    val data: List<Video>
)

data class BookDetailResponse(
    val data: Book
)

data class VideoDetailResponse(
    val data: Video
)

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

