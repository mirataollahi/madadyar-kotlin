package ir.madadyar.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import ir.madadyar.ui.theme.*
import ir.madadyar.ui.viewmodel.AuthViewModel
import ir.madadyar.ui.component.ErrorDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    viewModel: AuthViewModel = viewModel(),
    onNavigateToLogin: () -> Unit,
    onNavigateToVerify: (String) -> Unit,
    onNavigateToHome: () -> Unit
) {
    var username by remember { mutableStateOf(TextFieldValue()) }
    var phoneNumber by remember { mutableStateOf(TextFieldValue()) }
    
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val registerSuccess by viewModel.registerSuccess.collectAsState()
    val isAuthenticated by viewModel.isAuthenticated.collectAsState()
    
    LaunchedEffect(registerSuccess) {
        registerSuccess?.let {
            viewModel.clearSuccess()
            onNavigateToVerify(it)
        }
    }
    
    LaunchedEffect(isAuthenticated) {
        if (isAuthenticated == true) {
            onNavigateToHome()
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("ثبت نام", fontFamily = iransansFontFamily) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = DarkSurface
                )
            )
        },
        containerColor = DarkSurfaceLight
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            
            // Register Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                shape = RoundedCornerShape(3.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurface),
                elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
            ) {
                Column(
                    modifier = Modifier.padding(15.dp)
                ) {
                    Text(
                        text = "ایجاد حساب",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Black,
                        color = White,
                        fontFamily = iransansFontFamily
                    )
                    
                    Spacer(modifier = Modifier.height(25.dp))
                    
                    Text(
                        text = "نام و نام خانوادگی خود را وارد کنید",
                        color = White,
                        fontFamily = iransansFontFamily
                    )
                    
                    Spacer(modifier = Modifier.height(15.dp))
                    
                    // Username Field
                    OutlinedTextField(
                        value = username,
                        onValueChange = { username = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("نام و نام خانوادگی", color = White.copy(alpha = 0.7f), fontFamily = iransansFontFamily) },
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = White) },
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = White,
                            unfocusedTextColor = White,
                            focusedContainerColor = androidx.compose.ui.graphics.Color.Transparent,
                            unfocusedContainerColor = androidx.compose.ui.graphics.Color.Transparent,
                            focusedIndicatorColor = White,
                            unfocusedIndicatorColor = White,
                            cursorColor = White
                        ),
                        singleLine = true,
                        shape = RoundedCornerShape(4.dp)
                    )
                    
                    Spacer(modifier = Modifier.height(10.dp))
                    
                    Text(
                        text = "موبایل را وارد کنید",
                        color = White,
                        fontFamily = iransansFontFamily
                    )
                    
                    Spacer(modifier = Modifier.height(15.dp))
                    
                    // Phone Number Field
                    OutlinedTextField(
                        value = phoneNumber,
                        onValueChange = { 
                            if (it.text.length <= 11 && it.text.all { char -> char.isDigit() }) {
                                phoneNumber = it
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("شماره همراه خود را وارد کنید", color = White.copy(alpha = 0.7f), fontFamily = iransansFontFamily) },
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = White) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = White,
                            unfocusedTextColor = White,
                            focusedContainerColor = androidx.compose.ui.graphics.Color.Transparent,
                            unfocusedContainerColor = androidx.compose.ui.graphics.Color.Transparent,
                            focusedIndicatorColor = White,
                            unfocusedIndicatorColor = White,
                            cursorColor = White
                        ),
                        singleLine = true,
                        shape = RoundedCornerShape(4.dp)
                    )
                    
                    Spacer(modifier = Modifier.height(15.dp))
                    
                    // Continue Button
                    Button(
                        onClick = {
                            if (username.text.isNotEmpty() && phoneNumber.text.length == 11) {
                                viewModel.register(username.text, phoneNumber.text)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        enabled = !isLoading && username.text.isNotEmpty() && phoneNumber.text.length == 11,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isLoading) DarkSurfaceLight else GreenAccent,
                            disabledContainerColor = DarkSurfaceLight
                        ),
                        shape = RoundedCornerShape(3.dp)
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(color = White)
                        } else {
                            Text(
                                text = "ادامه",
                                color = White,
                                fontWeight = FontWeight.Black,
                                fontSize = 16.sp,
                                fontFamily = iransansFontFamily
                            )
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Navigate to Login
            TextButton(onClick = onNavigateToLogin) {
                Text(
                    text = "حساب کاربری دارید ؟ وارد شوید",
                    color = White,
                    fontFamily = iransansFontFamily
                )
            }
            
            // Error Message
            errorMessage?.let {
                ErrorDialog(
                    message = it,
                    onDismiss = { viewModel.clearError() }
                )
            }
        }
    }
}

