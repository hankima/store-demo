package loc.example.storedemoapp.model

data class HomeUiState(
    val isLoading: Boolean = true,
    val username: String = "",
    val recommendedDeals: List<Product> = emptyList(),
    val error: Throwable? = null
)
