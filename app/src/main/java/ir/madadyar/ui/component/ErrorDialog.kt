package ir.madadyar.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import ir.madadyar.ui.theme.*

@Composable
fun ErrorDialog(
    message: String,
    onDismiss: () -> Unit,
    onRetry: (() -> Unit)? = null
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "خطا",
                fontFamily = iransansFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = White
            )
        },
        text = {
            Text(
                text = message,
                fontFamily = iransansFontFamily,
                fontSize = 14.sp,
                color = White,
                textAlign = TextAlign.Right
            )
        },
        confirmButton = {
            if (onRetry != null) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TextButton(onClick = onDismiss) {
                        Text(
                            text = "بستن",
                            fontFamily = iransansFontFamily,
                            color = White
                        )
                    }
                    Button(
                        onClick = {
                            onRetry()
                            onDismiss()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = GreenAccent
                        )
                    ) {
                        Text(
                            text = "تلاش مجدد",
                            fontFamily = iransansFontFamily,
                            color = White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            } else {
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GreenAccent
                    )
                ) {
                    Text(
                        text = "باشه",
                        fontFamily = iransansFontFamily,
                        color = White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        },
        containerColor = DarkSurface,
        titleContentColor = White,
        textContentColor = White
    )
}

@Composable
fun SnackbarError(
    message: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.error
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = message,
                fontFamily = iransansFontFamily,
                color = White,
                fontSize = 14.sp
            )
        }
    }
}

