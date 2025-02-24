package cat.copernic.grup4.gamedex.Category.Data

import cat.copernic.grup4.gamedex.Core.Model.Category
import retrofit2.Response

class CategoryRepository {
    suspend fun addCategory(category: Category): Response<Category> {
        return RetrofitInstance.api.addCategory(category)
    }
    suspend fun getAllCategory(): Response<List<Category>> {
        return RetrofitInstance.api.getAllCategory()

    }
}
