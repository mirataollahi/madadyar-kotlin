package ir.madadyar.data.model

data class Video(
    val id: Int,
    val name: String,
    val image: String,
    val link: String,
    val description: String,
    val cat_id: Int,
    val cat_name: String,
    val cat_image: String?
)

