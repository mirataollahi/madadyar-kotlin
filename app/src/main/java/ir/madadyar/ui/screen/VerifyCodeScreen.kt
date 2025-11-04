package ir.madadyar.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
fun VerifyCodeScreen(
    phoneNumber: String,
    isRegister: Boolean = true,
    viewModel: AuthViewModel = viewModel(),
    onNavigateBack: () -> Unit,
    onNavigateToHome: () -> Unit
) {
    var code by remember { mutableStateOf(TextFieldValue()) }
    
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val isAuthenticated by viewModel.isAuthenticated.collectAsState()
    
    LaunchedEffect(isAuthenticated) {
        if (isAuthenticated == true) {
            onNavigateToHome()
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isRegister) "ثبت نام" else "ورود", fontFamily = iransansFontFamily) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
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
                        text = "کد ارسال شده را وارد کنید",
                        color = White,
                        fontFamily = iransansFontFamily
                    )
                    
                    Spacer(modifier = Modifier.height(15.dp))
                    
                    OutlinedTextField(
                        value = code,
                        onValueChange = { 
                            if (it.text.length <= 4 && it.text.all { char -> char.isDigit() }) {
                                code = it
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("کد ارسال شده", color = White.copy(alpha = 0.7f), fontFamily = iransansFontFamily) },
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
                    
                    Button(
                        onClick = {
                            if (code.text.length == 4) {
                                if (isRegister) {
                                    viewModel.verifyRegister(phoneNumber, code.text)
                                } else {
                                    viewModel.verifyLogin(phoneNumber, code.text)
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        enabled = !isLoading && code.text.length == 4,
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

