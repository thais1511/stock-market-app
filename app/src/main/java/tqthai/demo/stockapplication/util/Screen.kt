package tqthai.demo.stockapplication.util

sealed class Screen(val route: String = "") {
    object CompanyListingScreen : Screen("company_listing")
    data class CompanyInfoScreen(val company: String) : Screen("company_info/")
}