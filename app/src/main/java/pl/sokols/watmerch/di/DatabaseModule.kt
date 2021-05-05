package pl.sokols.watmerch.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pl.sokols.watmerch.data.local.dao.OrderProductDao
import pl.sokols.watmerch.data.local.database.AppDatabase
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        AppDatabase.getDatabase(context)


    @Provides
    fun provideOrderProductDao(appDatabase: AppDatabase): OrderProductDao =
        appDatabase.orderProductDao()
}