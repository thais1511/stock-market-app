package tqthai.demo.stockapplication.util

sealed class Screen(val route: String = "") {
    object CompanyListingScreen : Screen("company_listing")
    object CompanyInfoScreen : Screen("company_info")
}