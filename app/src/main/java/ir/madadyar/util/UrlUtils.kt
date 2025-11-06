package ir.madadyar.util

import ir.madadyar.data.api.ApiClient

fun String?.toFullImageUrl(): String? {
    if (this.isNullOrBlank()) return this
    return if (this.startsWith("/storage")) {
        ApiClient.IMAGE_BASE_URL + this
    } else {
        this
    }
}


