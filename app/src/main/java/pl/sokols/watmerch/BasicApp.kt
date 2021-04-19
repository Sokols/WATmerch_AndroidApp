package pl.sokols.watmerch

import android.app.Application
import pl.sokols.watmerch.data.database.AppDatabase
import pl.sokols.watmerch.data.repository.MerchRepository

class BasicApp : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { MerchRepository(database.merchDao()) }
}