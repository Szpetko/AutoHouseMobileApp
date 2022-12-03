package pl.autohouse.autohousemobileapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import pl.autohouse.autohousemobileapp.navigation.graphs.RootNavGraph
import pl.autohouse.autohousemobileapp.ui.theme.AutoHouseMobileAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        lateinit var navController: NavHostController

        super.onCreate(savedInstanceState)
        setContent {
            AutoHouseMobileAppTheme {
                navController = rememberNavController()
                RootNavGraph(navController = navController)
            }
        }
    }
}
