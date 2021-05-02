package pl.sokols.watmerch.data.remote.services.category

import pl.sokols.watmerch.data.model.Category
import javax.inject.Inject

class CategoryHelperImpl @Inject constructor(private val categoryService: CategoryService) :
    CategoryHelper {
    override suspend fun getCategories(): List<Category> = categoryService.getCategories()
}