package ir.madadyar.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Phone
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
import ir.madadyar.ui.component.AuthGradientBackground
import ir.madadyar.ui.component.AuthHeader
import ir.madadyar.ui.component.PrimaryButton
import ir.madadyar.ui.viewmodel.AuthViewModel
import ir.madadyar.ui.component.ErrorDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    viewModel: AuthViewModel = viewModel(),
    onNavigateToRegister: () -> Unit,
    onNavigateToVerify: (String) -> Unit,
    onNavigateToHome: () -> Unit
) {
    var phoneNumber by remember { mutableStateOf(TextFieldValue()) }
    
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val loginSuccess by viewModel.loginSuccess.collectAsState()
    val isAuthenticated by viewModel.isAuthenticated.collectAsState()
    
    LaunchedEffect(loginSuccess) {
        loginSuccess?.let {
            viewModel.clearSuccess()
            onNavigateToVerify(it)
        }
    }
    
    LaunchedEffect(isAuthenticated) {
        if (isAuthenticated == true) {
            onNavigateToHome()
        }
    }
    
    Scaffold(containerColor = Color.Transparent) { padding ->
        AuthGradientBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AuthHeader(title = "خوش آمدید", subtitle = "برای ادامه شماره موبایل خود را وارد کنید")
            Spacer(modifier = Modifier.height(16.dp))
            
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurface),
                elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
            ) {
                Column(
                    modifier = Modifier.padding(15.dp)
                ) {
                    Text(text = "ورود", fontSize = 18.sp, fontWeight = FontWeight.Black, color = White, fontFamily = iransansFontFamily)
                    Spacer(modifier = Modifier.height(10.dp))
                    
                    Text(
                        text = "موبایل را وارد کنید",
                        color = White,
                        fontFamily = iransansFontFamily
                    )
                    
                    Spacer(modifier = Modifier.height(15.dp))
                    
                    OutlinedTextField(
                        value = phoneNumber,
                        onValueChange = { 
                            if (it.text.length <= 11 && it.text.all { char -> char.isDigit() }) {
                                phoneNumber = it
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("شماره همراه خود را وارد کنید", color = White.copy(alpha = 0.7f), fontFamily = iransansFontFamily) },
                        leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null, tint = White) },
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
                        shape = RoundedCornerShape(8.dp),
                        supportingText = {
                            val valid = phoneNumber.text.length == 11
                            if (!valid && phoneNumber.text.isNotEmpty()) {
                                Text("فرمت شماره صحیح نیست", color = MaterialTheme.colorScheme.error, fontFamily = iransansFontFamily)
                            } else {
                                Text("مثال: 09123456789", color = White.copy(alpha = 0.6f), fontFamily = iransansFontFamily)
                            }
                        }
                    )
                    
                    Spacer(modifier = Modifier.height(15.dp))
                    
                    PrimaryButton(
                        text = "ادامه",
                        enabled = phoneNumber.text.length == 11,
                        isLoading = isLoading,
                        onClick = { if (phoneNumber.text.length == 11) viewModel.login(phoneNumber.text) }
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            TextButton(onClick = onNavigateToRegister) {
                Text(
                    text = "حساب کاربری ندارید ؟  ثبت نام کنید",
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
        } }
    }
}

