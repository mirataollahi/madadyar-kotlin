package ir.madadyar.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.madadyar.ui.theme.DarkSurface
import ir.madadyar.ui.theme.DarkSurfaceLight
import ir.madadyar.ui.theme.GreenAccent
import ir.madadyar.ui.theme.White
import ir.madadyar.ui.theme.iransansFontFamily

@Composable
fun AuthGradientBackground(content: @Composable BoxScope.() -> Unit) {
	Box(
		modifier = Modifier
			.fillMaxSize()
			.background(
				brush = Brush.verticalGradient(
					colors = listOf(DarkSurfaceLight, DarkSurface)
				)
			)
	) { content() }
}

@Composable
fun AuthHeader(title: String, subtitle: String? = null) {
	Column(
		modifier = Modifier
			.fillMaxWidth()
			.padding(top = 24.dp, bottom = 8.dp),
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Text(
			text = title,
			fontFamily = iransansFontFamily,
			fontWeight = FontWeight.Black,
			fontSize = 22.sp,
			color = White
		)
		subtitle?.let {
			Spacer(modifier = Modifier.height(6.dp))
			Text(
				text = it,
				fontFamily = iransansFontFamily,
				fontSize = 13.sp,
				color = White.copy(alpha = 0.75f)
			)
		}
	}
}

@Composable
fun PrimaryButton(
	text: String,
	enabled: Boolean,
	isLoading: Boolean,
	onClick: () -> Unit,
	modifier: Modifier = Modifier
) {
	Button(
		onClick = onClick,
		modifier = modifier
			.fillMaxWidth()
			.height(50.dp),
		enabled = enabled && !isLoading,
		colors = ButtonDefaults.buttonColors(
			containerColor = if (isLoading) DarkSurfaceLight else GreenAccent,
			disabledContainerColor = DarkSurfaceLight
		),
		shape = RoundedCornerShape(8.dp)
	) {
		if (isLoading) {
			CircularProgressIndicator(color = White)
		} else {
			Text(
				text = text,
				fontFamily = iransansFontFamily,
				fontWeight = FontWeight.Bold,
				fontSize = 16.sp,
				color = White
			)
		}
	}
}


