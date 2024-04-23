package tqthai.demo.stockapplication.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tqthai.demo.stockapplication.data.csv.CSVParser
import tqthai.demo.stockapplication.data.csv.CompanyListingParser
import tqthai.demo.stockapplication.data.csv.IntradayInfoParser
import tqthai.demo.stockapplication.data.repository.StockRepositoryImpl
import tqthai.demo.stockapplication.domain.model.CompanyListing
import tqthai.demo.stockapplication.domain.model.IntradayInfo
import tqthai.demo.stockapplication.domain.repository.StockRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule() {

    @Binds
    @Singleton
    abstract fun provideCompanyListingsParser(
        companyListingParser: CompanyListingParser
    ): CSVParser<CompanyListing>

    @Binds
    @Singleton
    abstract fun provideIntradayInfoParser(
        intradayInfoParser: IntradayInfoParser
    ): CSVParser<IntradayInfo>

    @Binds
    @Singleton
    abstract fun provideStockRepositoryImpl(
        stockRepositoryImpl: StockRepositoryImpl
    ): StockRepository
}
