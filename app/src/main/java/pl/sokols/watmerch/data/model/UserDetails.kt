package pl.sokols.watmerch.data.model

import java.util.*

data class UserDetails(
    val id: Int,
    val avatar: String?,
    val birthDate: Date?,
    val companyName: String?,
    val firstName: String,
    val lastName: String,
    val nip: String?,
    val phoneNumber: String
)
