package ir.madadyar.data.model

data class BookCategory(
    val id: Int,
    val name: String,
    val image: String,
    val type: Int // 0 = book, 1 = video
)

