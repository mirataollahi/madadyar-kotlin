package ir.madadyar.data.model

import com.google.gson.annotations.SerializedName

data class Book(
    val id: Int,
    val name: String,
    val image: String,
    val file: String?,
    val description: String,
    @SerializedName("book_category_id")
    val book_category_id: Int?,
    val price: Int?,
    val deleted_at: String?,
    val created_at: String?,
    val updated_at: String?,
    val category: BookCategory?
) {
    // Computed properties for backward compatibility
    val cat_id: Int
        get() = book_category_id ?: category?.id ?: 0
    
    val cat_name: String
        get() = category?.name ?: ""
    
    val cat_image: String?
        get() = category?.image
}

