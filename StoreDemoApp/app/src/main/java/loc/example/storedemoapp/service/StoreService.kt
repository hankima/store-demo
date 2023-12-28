package loc.example.storedemoapp.service

import loc.example.storedemoapp.model.Product
import retrofit2.http.GET

interface StoreService {
    @GET(value = "products")
    suspend fun fetchProducts(): List<Product>
}