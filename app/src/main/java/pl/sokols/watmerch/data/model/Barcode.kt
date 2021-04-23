package pl.sokols.watmerch.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Barcode(
    @PrimaryKey
    val barcode: Int
)
