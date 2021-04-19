package pl.sokols.watmerch.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Merch(
//    @PrimaryKey(autoGenerate = true)
    @PrimaryKey
    val id: Int = 0,
    val name: String = "",
    val price: Int = 0,
) : Parcelable {
    @Ignore
    constructor() : this(0)
}