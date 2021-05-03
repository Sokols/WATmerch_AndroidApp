package pl.sokols.watmerch.data.model

import java.util.*

data class UserDetails(
    val id: Int? = Int.MIN_VALUE,
    val avatar: String? = null,
    val birthDate: String? = null,
    val company: String? = null,
    val firstName: String = "",
    val lastName: String = "",
    val nip: String? = null,
    val phoneNumber: String = ""
)