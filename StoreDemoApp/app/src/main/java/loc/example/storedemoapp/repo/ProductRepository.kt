package loc.example.storedemoapp.repo

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import loc.example.storedemoapp.model.Product
import loc.example.storedemoapp.service.StoreService
import javax.inject.Inject

interface ProductRepository {
    fun fetchProducts(): Flow<Result<List<Product>>>
}

class ProductRepositoryImpl @Inject constructor(
    private val storeService: StoreService
) : ProductRepository {
    override fun fetchProducts(): Flow<Result<List<Product>>> = flow {
        try {
            val products = storeService.fetchProducts()
            emit(Result.success(products))
        } catch (e: Exception) {
            emit(Result.failure(exception = e))
        }
    }.flowOn(Dispatchers.IO)
}