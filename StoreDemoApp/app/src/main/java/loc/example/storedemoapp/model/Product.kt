package loc.example.storedemoapp.model

import java.math.BigDecimal

data class Product(
    val id: Int,
    val title: String,
    val price: BigDecimal,
    val description: String,
    val category: String,
    val image: String,
    val rating: Rating
)

data class Rating(
    val rate: Double,
    val count: Int
)
