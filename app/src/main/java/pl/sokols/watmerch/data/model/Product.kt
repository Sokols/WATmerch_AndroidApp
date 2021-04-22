package pl.sokols.watmerch.data.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Product(
    @PrimaryKey
    val barcode: Int = 0,
    val name: String = "",
    val price: Float = 0.0f,
    val vat: Float? = 0.0f,
//    @Embedded
//    val category: Category? = null
) : Parcelable {
    @Ignore
    constructor() : this(0)
}