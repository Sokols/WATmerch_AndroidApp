package pl.sokols.watmerch.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import pl.sokols.watmerch.data.local.dao.OrderProductDao
import pl.sokols.watmerch.data.model.OrderProduct

@Database(entities = [OrderProduct::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun orderProductDao(): OrderProductDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "cart_products_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}