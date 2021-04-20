package pl.sokols.watmerch.data.model

data class LoginResponse(
    val statusCode: Int,
    val message: String,
    val details: String
)