package tqthai.demo.stockapplication.data.mapper

import tqthai.demo.stockapplication.data.local.CompanyListingEntity
import tqthai.demo.stockapplication.domain.model.CompanyListing

fun CompanyListingEntity.toCompanyListing(): CompanyListing{
    return CompanyListing(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}

fun CompanyListing.toCompanyListingEntity(): CompanyListingEntity {
    return CompanyListingEntity(name, symbol, exchange)
}