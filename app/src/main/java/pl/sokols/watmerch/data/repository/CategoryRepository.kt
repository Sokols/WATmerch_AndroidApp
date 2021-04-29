package pl.sokols.watmerch.data.repository

import pl.sokols.watmerch.data.remote.services.CategoryService

class CategoryRepository(private val categoryService: CategoryService) {

    suspend fun getCategories() = categoryService.getCategories()
}