package pl.sokols.watmerch.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Category(
    @PrimaryKey
    val id: Int,
    val name: String
) : Parcelable{
    @Ignore
    constructor() : this(0, "")
}
