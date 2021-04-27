package pl.sokols.watmerch.data.model.response

data class UserResponse(
    val statusCode: Int,
    val message: String,
    val details: String
)