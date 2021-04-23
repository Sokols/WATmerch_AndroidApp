package pl.sokols.watmerch.data.model

import java.time.LocalDate

data class UserDetails(
    val id: Int,
    val avatar: String?,
    val birthDate: LocalDate?,
    val companyName: String?,
    val firstName: String,
    val lastName: String,
    val nip: String?,
    val phoneNumber: String
)
