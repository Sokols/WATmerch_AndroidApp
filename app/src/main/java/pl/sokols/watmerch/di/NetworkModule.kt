package pl.sokols.watmerch.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import pl.sokols.watmerch.data.remote.interceptors.AddCookiesInterceptor
import pl.sokols.watmerch.data.remote.interceptors.AuthInterceptor
import pl.sokols.watmerch.data.remote.interceptors.ReceivedCookiesInterceptor
import pl.sokols.watmerch.data.remote.services.category.CategoryHelper
import pl.sokols.watmerch.data.remote.services.category.CategoryHelperImpl
import pl.sokols.watmerch.data.remote.services.category.CategoryService
import pl.sokols.watmerch.data.remote.services.product.ProductHelper
import pl.sokols.watmerch.data.remote.services.product.ProductHelperImpl
import pl.sokols.watmerch.data.remote.services.product.ProductService
import pl.sokols.watmerch.data.remote.services.user.UserHelper
import pl.sokols.watmerch.data.remote.services.user.UserHelperImpl
import pl.sokols.watmerch.data.remote.services.user.UserService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    private val URL = "http://10.0.2.2:8081/"

    @Provides
    @Singleton
    fun provideOkHttpClient(
        addCookiesInterceptor: AddCookiesInterceptor,
        receivedCookiesInterceptor: ReceivedCookiesInterceptor,
        authInterceptor: AuthInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(addCookiesInterceptor)
            .addInterceptor(receivedCookiesInterceptor)
            .addInterceptor(authInterceptor)
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(URL)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideUserService(retrofit: Retrofit): UserService =
        retrofit.create(UserService::class.java)

    @Provides
    @Singleton
    fun provideUserHelper(userHelper: UserHelperImpl): UserHelper = userHelper

    @Provides
    @Singleton
    fun provideProductService(retrofit: Retrofit): ProductService =
        retrofit.create(ProductService::class.java)

    @Provides
    @Singleton
    fun provideProductHelper(productHelper: ProductHelperImpl): ProductHelper = productHelper

    @Provides
    @Singleton
    fun provideCategoryService(retrofit: Retrofit): CategoryService =
        retrofit.create(CategoryService::class.java)

    @Provides
    @Singleton
    fun provideCategoryHelper(categoryHelper: CategoryHelperImpl): CategoryHelper = categoryHelper
}