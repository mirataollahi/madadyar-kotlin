package ir.madadyar.data.model

data class VideoCategory(
    val id: Int,
    val name: String,
    val image: String?,
    val parent_id: Int? = null
)

