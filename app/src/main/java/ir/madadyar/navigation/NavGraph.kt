package ir.madadyar.navigation

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.padding
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.material3.Text
import ir.madadyar.ui.screen.*
import ir.madadyar.ui.component.AppScaffold

sealed class Screen(val route: String) {
    object Landing : Screen("landing")
    object Register : Screen("register")
    object Login : Screen("login")
    data class VerifyCode(val phoneNumber: String = "", val isRegister: Boolean = true) : 
        Screen("verify_code/{phoneNumber}/{isRegister}") {
        companion object {
            fun createRoute(phoneNumber: String, isRegister: Boolean) = 
                "verify_code/$phoneNumber/$isRegister"
        }
    }
    object Home : Screen("home")
    data class BooksList(val categoryId: Int? = null) : 
        Screen("books_list/{categoryId}") {
        companion object {
            fun createRoute(categoryId: Int? = null) = 
                if (categoryId != null) "books_list/$categoryId" else "books_list"
        }
    }
    data class VideosList(val categoryId: Int? = null) : 
        Screen("videos_list/{categoryId}") {
        companion object {
            fun createRoute(categoryId: Int? = null) = 
                if (categoryId != null) "videos_list/$categoryId" else "videos_list"
        }
    }
    data class BookDetail(val bookId: Int) : Screen("book_detail/{bookId}") {
        companion object {
            fun createRoute(bookId: Int) = "book_detail/$bookId"
        }
    }
    data class VideoDetail(val videoId: Int) : Screen("video_detail/{videoId}") {
        companion object {
            fun createRoute(videoId: Int) = "video_detail/$videoId"
        }
    }
    object About : Screen("about")
    object Contact : Screen("contact")
}

@Composable
fun NavGraph(navController: NavHostController, startDestination: String) {
    AppScaffold(
        navController = navController,
        onClickProfile = { /* TODO: navigate to profile when available */ },
        onClickSearch = { navController.navigate("search") }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = androidx.compose.ui.Modifier.padding(paddingValues)
        ) {
        composable(Screen.Landing.route) {
            LandingScreen(
                onNavigateAway = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Landing.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Screen.Register.route) {
            RegisterScreen(
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route)
                },
                onNavigateToVerify = { phoneNumber ->
                    navController.navigate(
                        Screen.VerifyCode.createRoute(phoneNumber, true)
                    )
                },
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Screen.Login.route) {
            LoginScreen(
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                },
                onNavigateToVerify = { phoneNumber ->
                    navController.navigate(
                        Screen.VerifyCode.createRoute(phoneNumber, false)
                    )
                },
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
        
        composable(
            route = "verify_code/{phoneNumber}/{isRegister}",
            arguments = listOf(
                androidx.navigation.navArgument("phoneNumber") { 
                    type = androidx.navigation.NavType.StringType 
                },
                androidx.navigation.navArgument("isRegister") { 
                    type = androidx.navigation.NavType.BoolType 
                }
            )
        ) { backStackEntry ->
            val phoneNumber = backStackEntry.arguments?.getString("phoneNumber") ?: ""
            val isRegister = backStackEntry.arguments?.getBoolean("isRegister") ?: true
            
            VerifyCodeScreen(
                phoneNumber = phoneNumber,
                isRegister = isRegister,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Screen.Home.route) {
            HomeScreen(
                onOpenDrawer = { /* Handle drawer */ },
                onNavigateToBooksList = { categoryId ->
                    navController.navigate(
                        Screen.BooksList.createRoute(categoryId)
                    )
                },
                onNavigateToVideosList = { categoryId ->
                    navController.navigate(
                        Screen.VideosList.createRoute(categoryId)
                    )
                },
                onNavigateToBookDetail = { book ->
                    navController.navigate(
                        Screen.BookDetail.createRoute(book.id)
                    )
                },
                onNavigateToVideoDetail = { videoId ->
                    navController.navigate(
                        Screen.VideoDetail.createRoute(videoId)
                    )
                }
            )
        }
        
        composable("books_list") {
            BooksListScreen(
                initialCategoryId = null,
                onBack = { navController.popBackStack() },
                onNavigateToBookDetail = { id ->
                    navController.navigate(Screen.BookDetail.createRoute(id))
                }
            )
        }
        
        composable(
            route = "books_list/{categoryId}",
            arguments = listOf(
                androidx.navigation.navArgument("categoryId") { 
                    type = androidx.navigation.NavType.IntType
                }
            )
        ) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getInt("categoryId")
            BooksListScreen(
                initialCategoryId = categoryId,
                onBack = { navController.popBackStack() },
                onNavigateToBookDetail = { id ->
                    navController.navigate(Screen.BookDetail.createRoute(id))
                }
            )
        }
        
        composable("videos_list") {
            VideosListScreen(
                initialCategoryId = null,
                onBack = { navController.popBackStack() },
                onNavigateToVideoDetail = { id ->
                    navController.navigate(Screen.VideoDetail.createRoute(id))
                }
            )
        }
        
        composable(
            route = "videos_list/{categoryId}",
            arguments = listOf(
                androidx.navigation.navArgument("categoryId") { 
                    type = androidx.navigation.NavType.IntType
                }
            )
        ) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getInt("categoryId")
            VideosListScreen(
                initialCategoryId = categoryId,
                onBack = { navController.popBackStack() },
                onNavigateToVideoDetail = { id ->
                    navController.navigate(Screen.VideoDetail.createRoute(id))
                }
            )
        }
        
        composable(
            route = "book_detail/{bookId}",
            arguments = listOf(
                androidx.navigation.navArgument("bookId") { 
                    type = androidx.navigation.NavType.IntType 
                }
            )
        ) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getInt("bookId") ?: 0
            BookDetailScreen(bookId = bookId, onBack = { navController.popBackStack() })
        }
        
        composable(
            route = "video_detail/{videoId}",
            arguments = listOf(
                androidx.navigation.navArgument("videoId") { 
                    type = androidx.navigation.NavType.IntType 
                }
            )
        ) { backStackEntry ->
            val videoId = backStackEntry.arguments?.getInt("videoId") ?: 0
            VideoDetailScreen(videoId = videoId, onBack = { navController.popBackStack() })
        }
        
        composable("search") {
            SearchScreen(
                onOpenBook = { id -> navController.navigate(Screen.BookDetail.createRoute(id)) },
                onOpenVideo = { id -> navController.navigate(Screen.VideoDetail.createRoute(id)) }
            )
        }
    }
    }
}

