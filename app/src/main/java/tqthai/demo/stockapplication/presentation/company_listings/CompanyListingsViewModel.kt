package tqthai.demo.stockapplication.presentation.company_listings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import tqthai.demo.stockapplication.domain.repository.StockRepository
import tqthai.demo.stockapplication.util.Resource
import javax.inject.Inject

@HiltViewModel
class CompanyListingsViewModel @Inject constructor(
    val stockRepository: StockRepository
): ViewModel() {
    var state by mutableStateOf(CompanyListingsState())

    private var searchJob : Job? = null

    init {
        getCompanyListings()
    }

    fun onEvent(event: CompanyListingsEvent){
        when(event){
            is CompanyListingsEvent.Refresh -> {
                getCompanyListings(fetchFromRemote = true)

            }
            is CompanyListingsEvent.OnSearchQueryChange -> {
                state = state.copy(searchQuery = event.query)
                searchJob?.cancel()
                getCompanyListings()
            }
        }
    }

    fun getCompanyListings(
        fetchFromRemote: Boolean = false,
        query: String = ""
    ){
        viewModelScope.launch {
            stockRepository.getCompanyListing(fetchFromRemote = fetchFromRemote, query)
                .collect { result ->
                    when(result){
                        is Resource.Error -> {

                        }
                        is Resource.Loading -> {
                            state = state.copy(isLoading = true)
                        }
                        is Resource.Success -> {
                            result.data?.let { data ->
                                state = state.copy(companies = data)
                            }
                        }
                    }

                }
        }
    }
}