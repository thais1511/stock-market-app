package tqthai.demo.stockapplication.domain.repository

import kotlinx.coroutines.flow.Flow
import tqthai.demo.stockapplication.domain.model.CompanyListing
import tqthai.demo.stockapplication.util.Resource

interface StockRepository {
    suspend fun getCompanyListing(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>>


}