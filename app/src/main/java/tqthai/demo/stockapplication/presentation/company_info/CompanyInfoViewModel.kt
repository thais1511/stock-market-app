package tqthai.demo.stockapplication.presentation.company_info

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import tqthai.demo.stockapplication.domain.repository.StockRepository
import tqthai.demo.stockapplication.util.Resource
import javax.inject.Inject

@HiltViewModel
class CompanyInfoViewModel @Inject constructor(
    private val saveStateHandle: SavedStateHandle,
    private val repository: StockRepository,
): ViewModel(){

    var state by mutableStateOf(CompanyInfoState())

    init {
        viewModelScope.launch {
            val symbol = saveStateHandle.get<String>("symbol") ?: return@launch
            state = state.copy(loading = true)

            val companyInfoResult = async { repository.getCompanyInfo(symbol) }
            val intradayInfoResult = async { repository.getIntradayInfo(symbol) }

            when(val companyInfo = companyInfoResult.await()){
                is Resource.Success -> {
                    state = state.copy(
                        company = companyInfo.data,
                        loading = false,
                        error = null
                    )
                }
                is Resource.Error -> {
                    state = state.copy(
                        loading = false,
                        error = companyInfo.message
                    )
                }
                else -> Unit
            }
            when(val intradayInfo = intradayInfoResult.await()){
                is Resource.Success -> {
                    state = state.copy(
                        stockInfos = intradayInfo.data ?: emptyList(),
                        loading = false,
                        error = null
                    )
                }
                is Resource.Error -> {
                    state = state.copy(
                        loading = false,
                        error = intradayInfo.message
                    )
                }
                else -> Unit
            }

        }
    }
}