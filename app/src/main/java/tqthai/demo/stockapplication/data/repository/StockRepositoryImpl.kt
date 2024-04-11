package tqthai.demo.stockapplication.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import tqthai.demo.stockapplication.data.csv.CSVParser
import tqthai.demo.stockapplication.data.local.StockDatabase
import tqthai.demo.stockapplication.data.mapper.toCompanyListing
import tqthai.demo.stockapplication.data.mapper.toCompanyListingEntity
import tqthai.demo.stockapplication.data.remote.StockApi
import tqthai.demo.stockapplication.domain.model.CompanyListing
import tqthai.demo.stockapplication.domain.repository.StockRepository
import tqthai.demo.stockapplication.util.Resource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
    private val stockApi: StockApi,
    private val db: StockDatabase,
    private val companyListingParser: CSVParser<CompanyListing>
) : StockRepository {

    private val dao = db.stockDao
    override suspend fun getCompanyListing(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>> {
        return flow {
            emit(Resource.Loading(true))
            val localListing = dao.searchCompanyListing(query)
            emit(
                Resource.Success(localListing.map { it.toCompanyListing() })
            )
            val isDbEmpty = localListing.isEmpty() && query.isBlank()
            val shoudJustLoadFromCache = !isDbEmpty && !fetchFromRemote
            if(shoudJustLoadFromCache){
                emit(Resource.Loading(false))
                return@flow
            }
            val remoteListings = try {
                val response = stockApi.getListings()
                companyListingParser.parse(response.byteStream())
            } catch (ioException: IOException){
                ioException.printStackTrace()
                emit(Resource.Error(message = "Couldn't load data"))
                null
            } catch (httpException: HttpException){
                httpException.printStackTrace()
                emit(Resource.Error(message = "Couldn't load data"))
                null
            }
            remoteListings?.let { listings ->
                dao.clearCompanyListings()
                dao.insertCompanyListings( listings.map { it.toCompanyListingEntity() })
                emit(Resource.Success(data = dao.searchCompanyListing("").map {
                    it.toCompanyListing()
                }))
                emit(Resource.Loading(false))
            }
        }
    }
}