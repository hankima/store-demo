package loc.example.storedemoapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import loc.example.storedemoapp.model.HomeUiState
import loc.example.storedemoapp.repo.ProductRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val prodRepo: ProductRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        observeUiState()
        observeUser()
    }

    private fun observeUiState() {
        prodRepo.fetchProducts().onEach { result ->
            result.onSuccess { products ->
                _uiState.update {
                    it.copy(isLoading = false, recommendedDeals = products)
                }
            }
            result.onFailure { err ->
                _uiState.update {
                    it.copy(isLoading = false, error = err)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun observeUser() =
        _uiState.update {
            it.copy(username = "Bob")
        }
}