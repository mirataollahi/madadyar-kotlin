package ir.madadyar.data.model

import com.google.gson.annotations.SerializedName

data class Video(
    val id: Int,
    val name: String,
    val image: String,
    val file: String?,
    val description: String,
    @SerializedName("video_category_id")
    val video_category_id: Int?,
    val deleted_at: String?,
    val created_at: String?,
    val updated_at: String?,
    val category: VideoCategory?
) {
    // Computed properties for backward compatibility
    val cat_id: Int
        get() = video_category_id ?: category?.id ?: 0
    
    val cat_name: String
        get() = category?.name ?: ""
    
    val cat_image: String?
        get() = category?.image
    
    // Backward compatibility for 'link' field
    val link: String
        get() = file ?: ""
}

