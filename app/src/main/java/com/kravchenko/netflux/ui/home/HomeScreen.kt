package com.kravchenko.netflux.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.kravchenko.netflux.R
import com.kravchenko.netflux.models.Movie
import com.kravchenko.netflux.ui.landing.ImageCarousel
import com.kravchenko.netflux.ui.movie.LoadingScreen
import com.kravchenko.netflux.ui.navigation.Routes
import com.kravchenko.netflux.ui.theme.BackgroundBlack
import com.kravchenko.netflux.ui.theme.LightGrey
import com.kravchenko.netflux.ui.theme.inter

@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel) {
    val homeState by viewModel.homeState.collectAsState()
    val scrollState = rememberScrollState()

    when (homeState) {
        is HomeState.Loading -> {
            LoadingScreen("Loading...")
        }
        is HomeState.Success -> {
            HomeContent(
                navController = navController,
                homeState = homeState,
                scrollState = scrollState
            )
        }
        is HomeState.Error -> {
            val message = (homeState as HomeState.Error).message
            Text(text = message, color = Color.Red)
        }
    }
}

@Composable
fun HomeContent(
    navController: NavController,
    homeState: HomeState,
    scrollState: ScrollState
) {
    val state = homeState as HomeState.Success
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        ImageCarousel(
            posters = state.posters,
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .graphicsLayer {
                    translationY = scrollState.value * 0.5f
                },
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(BackgroundBlack)
                .padding(start = 12.dp, top = 20.dp)
        ) {
            MovieCategorySection(
                title = "Now Playing",
                movies = state.nowPlayingMovies,
                navController = navController
            )
            MovieCategorySection(
                title = "Popular",
                movies = state.popularMovies,
                navController = navController
            )
            MovieCategorySection(
                title = "Top Rated",
                movies = state.topRatedMovies,
                navController = navController
            )
            MovieCategorySection(
                title = "Upcoming",
                movies = state.upcomingMovies,
                navController = navController
            )
        }
    }
}

@Composable
fun MovieCategorySection(
    title: String,
    movies: List<Movie>,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp)
    ) {
        Text(
            text = title,
            color = LightGrey,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = inter
        )
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        ) {
            items(movies) { movie ->
                MovieItem(
                    movie = movie,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun MovieItem(
    movie: Movie,
    navController: NavController
) {
    Image(
        modifier = Modifier
            .padding(end = 12.dp)
            .clip(RoundedCornerShape(8.dp))
            .width(140.dp)
            .height(210.dp)
            .clickable(
                enabled = true,
                onClick = {
                    navController.navigate(Routes.MovieDetails.createRoute(movie.id))
                }
            ),
        contentScale = ContentScale.FillHeight,
        painter = rememberAsyncImagePainter("https://image.tmdb.org/t/p/w500${movie.posterPath}"),
        contentDescription = movie.title,
    )
}