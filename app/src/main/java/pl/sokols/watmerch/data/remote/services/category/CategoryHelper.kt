package pl.sokols.watmerch.data.remote.services.category

import pl.sokols.watmerch.data.model.Category

interface CategoryHelper {

    suspend fun getCategories(): List<Category>

}