package pl.sokols.watmerch.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Merch(
    val id: Int,
    val name: String,
    val price: Int,
) : Parcelable