package pl.autohouse.autohousemobileapp


import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import pl.autohouse.autohousemobileapp.navigation.graphs.RootNavGraph
import pl.autohouse.autohousemobileapp.repository.Repository
import pl.autohouse.autohousemobileapp.repository.SettingsRepository
import pl.autohouse.autohousemobileapp.ui.theme.AutoHouseMobileAppTheme
import java.io.IOException
import java.util.*


class MainActivity : ComponentActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        lateinit var navController: NavHostController

        super.onCreate(savedInstanceState)



        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(SettingsRepository.getInstance(this), repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

        viewModel.getDevices()
        viewModel.getRooms()


        setContent {
            AutoHouseMobileAppTheme {

                navController = rememberNavController()
                RootNavGraph(navController = navController, viewModel)
            }
        }
    }
}


