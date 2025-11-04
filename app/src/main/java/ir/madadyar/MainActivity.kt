package ir.madadyar

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ir.madadyar.navigation.NavGraph
import ir.madadyar.navigation.Screen
import ir.madadyar.ui.theme.MadadyarTheme
import ir.madadyar.ui.viewmodel.AuthViewModel
import ir.madadyar.util.AuthManager
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        // Enable RTL layout
        window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL
        
        setContent {
            MadadyarTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val authViewModel: AuthViewModel = viewModel()
                    
                    // Check authentication status
                    var isAuthenticated by remember { mutableStateOf<Boolean?>(null) }
                    val scope = rememberCoroutineScope()
                    
                    LaunchedEffect(Unit) {
                        scope.launch {
                            isAuthenticated = AuthManager.isAuthenticated(this@MainActivity)
                            
                            // Load token into API client
                            AuthManager.getToken(this@MainActivity).collect { token ->
                                if (token != null) {
                                    ir.madadyar.data.api.ApiClient.setToken(token)
                                }
                            }
                        }
                    }
                    
                    // Determine start destination
                    val startDestination = when (isAuthenticated) {
                        true -> Screen.Home.route
                        false -> Screen.Register.route
                        null -> Screen.Landing.route
                    }
                    
                    NavGraph(
                        navController = navController,
                        startDestination = startDestination
                    )
                }
            }
        }
    }
}
