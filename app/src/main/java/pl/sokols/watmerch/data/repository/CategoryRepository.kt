package pl.sokols.watmerch.data.repository

import pl.sokols.watmerch.data.remote.services.category.CategoryHelper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepository @Inject constructor(private val categoryHelper: CategoryHelper) {

    suspend fun getCategories() = categoryHelper.getCategories()
}