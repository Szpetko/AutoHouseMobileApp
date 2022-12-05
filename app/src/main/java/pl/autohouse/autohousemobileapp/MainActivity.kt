package pl.autohouse.autohousemobileapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import pl.autohouse.autohousemobileapp.navigation.graphs.RootNavGraph
import pl.autohouse.autohousemobileapp.repository.Repository
import pl.autohouse.autohousemobileapp.ui.theme.AutoHouseMobileAppTheme

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        lateinit var navController: NavHostController

        super.onCreate(savedInstanceState)

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        viewModel.getDevices()
        viewModel.getRooms()

//        viewModel.myResponse.observe(this, Observer { response ->
//            Log.d("Response", response[0].deviceId.toString())
//            Log.d("Response", response[0].name)
//            Log.d("Response", response[0].type)
//            Log.d("Response", response[0].pinAddress.toString())
//        })


        setContent {
            AutoHouseMobileAppTheme {

                navController = rememberNavController()
                RootNavGraph(navController = navController, viewModel)
            }
        }
    }
}
