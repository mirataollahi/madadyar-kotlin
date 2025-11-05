package ir.madadyar.data.model

data class BookCategory(
    val id: Int,
    val name: String,
    val image: String?,
    val parent_id: Int? = null,
    val type: Int? = null // 0 = book, 1 = video (optional, may not be in API response)
)

