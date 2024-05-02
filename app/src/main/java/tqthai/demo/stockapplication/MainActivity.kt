package tqthai.demo.stockapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import tqthai.demo.stockapplication.presentation.company_info.CompanyInfoScreen
import tqthai.demo.stockapplication.presentation.company_listings.CompanyListingsScreen
import tqthai.demo.stockapplication.ui.theme.StockApplicationTheme
import tqthai.demo.stockapplication.util.Screen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StockApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navHostController = rememberNavController()
                    NavHost(navController = navHostController, startDestination = Screen.CompanyListing.route) {
                        composable(route = Screen.CompanyListing.route) {
                            CompanyListingsScreen() { symbol ->
                                navHostController.navigate(Screen.CompanyInfo.route + "&symbol=$symbol")
                            }
                        }
                        composable(route = Screen.CompanyInfo.route + "&symbol={symbol}",
                            arguments = listOf(
                                navArgument(name = "symbol"){
                                    type = NavType.StringType
                                    defaultValue = ""
                                    nullable = false
                                }
                            )
                        ){ entry ->
                            val symbol = entry.arguments?.getString("symbol") ?: ""
                            CompanyInfoScreen(
                                symbol = symbol
                            )
                        }
                    }
                }
            }
        }

    }
}