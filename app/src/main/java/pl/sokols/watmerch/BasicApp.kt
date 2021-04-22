package pl.sokols.watmerch

import android.app.Application
import pl.sokols.watmerch.data.database.AppDatabase
import pl.sokols.watmerch.data.repository.CartProductsRepository
import pl.sokols.watmerch.data.repository.RemoteRepository

class BasicApp : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val merchRepository by lazy { CartProductsRepository(database.merchDao()) }
    val userRepository by lazy { RemoteRepository.getRemoteRepository() }
}