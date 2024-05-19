package com.kravchenko.netflux.ui.landing

import android.graphics.drawable.Drawable
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.kravchenko.netflux.ui.movie.ErrorScreen
import com.kravchenko.netflux.ui.movie.LoadingScreen
import com.kravchenko.netflux.ui.movie.MovieDetailsState
import com.kravchenko.netflux.ui.navigation.Routes
import com.kravchenko.netflux.ui.theme.PrimaryBlack90
import com.kravchenko.netflux.ui.theme.PrimaryGreen
import com.kravchenko.netflux.ui.theme.inter
import kotlinx.coroutines.delay

@Composable
fun LandingScreen(navController: NavController, viewModel: LandingViewModel) {
    val landingState by viewModel.landingState.collectAsState()

    when (landingState) {
        is LandingState.Loading -> {
            LoadingScreen("Loading...")
        }
        is LandingState.Success -> {
            val posters = (landingState as LandingState.Success).posters
            LandingContent(
                navController = navController,
                posters = posters
            )
        }
        is LandingState.Error -> {
            ErrorScreen((landingState as LandingState.Error).message) {
                viewModel.fetchPopularMovies()
            }
        }
    }
}

@Composable
fun LandingContent(
    navController: NavController,
    posters: List<Drawable>
) {
    ImageCarousel(
        modifier = Modifier.fillMaxSize(),
        posters = posters
    )
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.weight(1f))
        AuthorizationSelection(navController = navController)
    }
}

@Composable
fun AuthorizationSelection(navController: NavController) {
    Card(
        shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp),
        colors = CardDefaults.cardColors(PrimaryBlack90),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(start = 25.dp, top = 30.dp, end = 25.dp, bottom = 50.dp)
        ) {
            Text(
                modifier = Modifier,
                text = "Watch movies anytime anywhere",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                fontFamily = inter,
            )
            Text(
                text = "Explore a vast collection of blockbuster movies, timeless classics, and the latest releases.",
                color = Color.White,
                fontSize = 13.sp,
                modifier = Modifier.padding(top = 10.dp),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold,
                fontFamily = inter
            )
            AuthorizationButton(
                text = "Login",
                textColor = Color.Black,
                colors = ButtonDefaults.buttonColors(PrimaryGreen),
                onClick = { navController.navigate(Routes.Login.route) }
            )
            AuthorizationButton(
                text = "Sign Up",
                textColor = PrimaryGreen,
                colors = ButtonDefaults.outlinedButtonColors(Color.Transparent),
                onClick = { navController.navigate(Routes.Signup.route) },
                borderStroke = BorderStroke(2.dp, PrimaryGreen)
            )
        }
    }
}

@Composable
fun AuthorizationButton(
    modifier: Modifier = Modifier,
    text: String,
    colors: ButtonColors,
    borderStroke: BorderStroke? = null,
    textColor: Color,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        shape = RoundedCornerShape(4.dp),
        colors = colors,
        onClick = onClick,
        border = borderStroke
    ) {
        Text(
            modifier = Modifier.padding(vertical = 10.dp),
            text = text,
            color = textColor,
            fontFamily = inter,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ImageCarousel(
    modifier: Modifier = Modifier,
    posters: List<Drawable>
) {
    var currentIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(posters) {
        while (true) {
            delay(3000L)
            currentIndex = (currentIndex + 1) % posters.size
        }
    }

    Box(modifier = modifier) {
        Crossfade(
            targetState = currentIndex,
            animationSpec = tween(durationMillis = 2000),
            label = "Image Carousel",
        ) { index ->
            Image(
                painter = rememberAsyncImagePainter(posters[index]),
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

