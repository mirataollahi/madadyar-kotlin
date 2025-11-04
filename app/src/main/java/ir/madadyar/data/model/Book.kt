package ir.madadyar.data.model

data class Book(
    val id: Int,
    val name: String,
    val image: String,
    val file: String?,
    val description: String,
    val cat_id: Int,
    val cat_name: String,
    val cat_image: String?
)

