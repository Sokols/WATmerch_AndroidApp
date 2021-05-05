package pl.sokols.watmerch.data.remote.services.category

import pl.sokols.watmerch.data.model.Category
import retrofit2.http.GET

interface CategoryService {

    @GET("api/categories")
    suspend fun getCategories(): List<Category>
}