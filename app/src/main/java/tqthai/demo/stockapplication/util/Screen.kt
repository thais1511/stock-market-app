package tqthai.demo.stockapplication.util

sealed class Screen(val route: String = "") {
    object CompanyListing : Screen("company_listing")
    object CompanyInfo: Screen("company_info")
}