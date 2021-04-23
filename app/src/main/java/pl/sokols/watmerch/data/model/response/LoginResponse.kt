package pl.sokols.watmerch.data.model.response

data class LoginResponse(
    val statusCode: Int,
    val message: String,
    val details: String
)