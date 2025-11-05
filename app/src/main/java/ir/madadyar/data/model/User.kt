package ir.madadyar.data.model

data class User(
    val id: Int,
    val username: String?,
    val name: String?,
    val phone_number: String,
    val password: String? = null,
    val two_factor_secret: String? = null,
    val two_factor_recovery_codes: String? = null,
    val remember_token: String? = null,
    val is_active: Int? = null,
    val created_at: String? = null,
    val updated_at: String? = null
)

