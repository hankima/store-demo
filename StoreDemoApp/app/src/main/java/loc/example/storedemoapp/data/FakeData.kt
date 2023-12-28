package loc.example.storedemoapp.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import loc.example.storedemoapp.model.Product
import loc.example.storedemoapp.model.Rating

object FakeData {
    private const val FILE_PRODUCTS = "products.json"
    val recommendedDeals: List<Product>
        get() = (1..10).map {
            Product(
                id = it,
                title = "Product # $it",
                price = "12.99".toBigDecimal(),
                description = "This product has SKU # $it",
                category = "Foods",
                image = "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg",
                rating = Rating(rate = 3.5, count = 104)
            )
        }

    fun getRecommendedDeals(ctx: Context, gson: Gson): List<Product> =
        with(ctx.assets.open(FILE_PRODUCTS)) {
            val bytes = ByteArray(size = available())
            read(bytes)
            val json = String(bytes)
            val typeToken = object : TypeToken<List<Product>>() {}.type
            gson.fromJson<List<Product>>(json, typeToken) // List::class.java)
        }
}