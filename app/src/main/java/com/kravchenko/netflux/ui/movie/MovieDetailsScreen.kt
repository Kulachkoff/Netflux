package com.kravchenko.netflux.ui.movie

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.PlayCircleFilled
import androidx.compose.material.icons.filled.PlayCircleOutline
import androidx.compose.material.icons.filled.SmartDisplay
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.graphics.withSave
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import coil.compose.rememberAsyncImagePainter
import com.kravchenko.netflux.R
import com.kravchenko.netflux.models.Cast
import com.kravchenko.netflux.models.Movie
import com.kravchenko.netflux.models.MovieVideo
import com.kravchenko.netflux.ui.theme.BackgroundBlack
import com.kravchenko.netflux.ui.theme.DarkGrey
import com.kravchenko.netflux.ui.theme.LightGrey
import com.kravchenko.netflux.ui.theme.PrimaryGreen
import com.kravchenko.netflux.ui.theme.inter
import com.kravchenko.netflux.utils.DateFormatter
import com.kravchenko.netflux.utils.VideoType

@Composable
fun MovieDetailsScreen(viewModel: MovieDetailsViewModel, movieId: Long) {
    val movieDetailsState by viewModel.movieDetailsState.collectAsState()

    LaunchedEffect(movieId) {
        if (movieDetailsState !is MovieDetailsState.Success) {
            viewModel.fetchMovieDetails(movieId)
        }
    }

    when (movieDetailsState) {
        is MovieDetailsState.Loading -> {
            LoadingScreen("Loading movie details...")
        }
        is MovieDetailsState.Success -> {
            val movie = (movieDetailsState as MovieDetailsState.Success).movie
            MovieDetailsContent(movie)
        }
        is MovieDetailsState.Error -> {
            ErrorScreen((movieDetailsState as MovieDetailsState.Error).message) {
                viewModel.fetchMovieDetails(movieId)
            }
        }
    }
}

@Composable
fun LoadingScreen(
    text: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundBlack),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(color = PrimaryGreen)
        Text(
            text = text,
            color = LightGrey,
            fontSize = 16.sp,
            fontFamily = inter,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

@Composable
fun ErrorScreen(message: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundBlack),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = message,
            color = LightGrey,
            fontSize = 16.sp,
            fontFamily = inter
        )
        Button(onClick = onRetry) {
            Text(text = "Retry")
        }
    }
}

@Composable
fun MovieDetailsContent(movie: Movie) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(BackgroundBlack)
    ) {
        MovieSplashScreen(movie, scrollState)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(BackgroundBlack)
                .padding(start = 12.dp, top = 20.dp, end = 12.dp)
        ) {
            Text(
                text = movie.title,
                color = LightGrey,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = inter
            )

            Spacer(modifier = Modifier.height(12.dp))
            MovieGenres(movie)

            Spacer(modifier = Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                MovieRating(movie.voteRating.toFloat() / 2f)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${movie.voteCount} votes",
                    color = DarkGrey,
                    fontSize = 12.sp,
                    fontFamily = inter,
                    modifier = Modifier
                        .alignByBaseline()
                        .offset(y = 2.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    imageVector = Icons.Filled.CalendarToday,
                    contentDescription = "Release Date",
                    tint = DarkGrey,
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .size(18.dp)
                )
                Text(
                    text = DateFormatter.formatDate(movie.releaseDate),
                    color = DarkGrey,
                    fontSize = 12.sp,
                    fontFamily = inter
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
            MovieOverview(movie)

            Spacer(modifier = Modifier.height(18.dp))
            Text(
                text = "Videos",
                color = LightGrey,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = inter
            )

            Spacer(modifier = Modifier.height(12.dp))
            movie.videos?.let { MovieVideos(it) }

            Spacer(modifier = Modifier.height(18.dp))
            Text(
                text = "Cast",
                color = LightGrey,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = inter
            )

            Spacer(modifier = Modifier.height(12.dp))
            MovieCast(movie)
        }
    }
}

@Composable
fun MovieSplashScreen(
    movie: Movie,
    scrollState: ScrollState
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .graphicsLayer {
                translationY = scrollState.value * 0.5f
            }
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.FillWidth,
            painter = rememberAsyncImagePainter("https://image.tmdb.org/t/p/w780${movie.backdropPath}"),
            contentDescription = null
        )
    }
}

@Composable
fun MovieGenres(movie: Movie) {
    Row {
        for (genre in movie.genres) {
            Text(
                text = genre.name,
                color = DarkGrey,
                fontSize = 12.sp,
                fontFamily = inter
            )
            if (genre != movie.genres.last()) {
                Text(
                    text = " | ",
                    color = DarkGrey,
                    fontSize = 12.sp,
                    fontFamily = inter
                )
            }
        }
    }
}

@Composable
fun MovieRating(rating: Float) {
    Row {
        for (i in 1..5) {
            val starColor = when {
                rating >= i -> PrimaryGreen
                rating < i - 1 -> DarkGrey
                else -> null
            }

            if (starColor != null) {
                Icon(
                    imageVector = Icons.Filled.StarRate,
                    contentDescription = null,
                    tint = starColor,
                    modifier = Modifier.size(18.dp)
                )
            } else {
                PartialStar(filledPercentage = (rating - (i - 1)) * 100)
            }
        }
    }
}

@Composable
fun PartialStar(filledPercentage: Float) {
    val starPainter = rememberVectorPainter(Icons.Filled.StarRate)
    Canvas(modifier = Modifier.size(18.dp)) {
        drawContext.canvas.withSave {
            with(starPainter) {
                draw(size = this@Canvas.size, colorFilter = ColorFilter.tint(DarkGrey))
            }
            clipRect(right = size.width * (filledPercentage / 100f)) {
                with(starPainter) {
                    draw(size = this@Canvas.size, colorFilter = ColorFilter.tint(PrimaryGreen))
                }
            }
        }
    }
}

@Composable
fun MovieOverview(movie: Movie) {
    Text(
        modifier = Modifier
            .fillMaxWidth(),
        text = if (movie.overview == "") "Unfortunately, there is no overview available for this movie." else movie.overview,
        color = LightGrey,
        fontSize = 16.sp,
        fontFamily = inter
    )
}

@Composable
fun MovieVideos(movieVideos: List<MovieVideo>) {
    if (movieVideos.isNotEmpty()) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            items(movieVideos.filter { it.type == VideoType.TRAILER }) { movieVideo ->
                VideoThumbnail(movieVideo)
            }
        }
    }
}

@Composable
fun VideoThumbnail(video: MovieVideo) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .padding(end = 12.dp)
            .clickable {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.youtube.com/watch?v=${video.key}")
                )
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                ContextCompat.startActivity(context, intent, null)
            }
    ) {
        Image(
            modifier = Modifier
                .width(220.dp)
                .height(110.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop,
            painter = rememberAsyncImagePainter("https://img.youtube.com/vi/${video.key}/0.jpg"),
            contentDescription = null
        )
        Image(
            painter = painterResource(id = R.drawable.ic_youtube),
            contentDescription = "Play",
            modifier = Modifier
                .size(48.dp)
                .align(Alignment.Center)
        )
    }
}



@Composable
fun MovieCast(movie: Movie) {
    if (movie.cast.isNotEmpty()) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            items(movie.cast.sortedBy { it.order }) { cast ->
                if (cast.order != null && cast.order < 10) {
                    CastPerson(cast)
                }
            }
        }
    }
}

@Composable
fun CastPerson(cast: Cast) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(end = 12.dp)
            .width(120.dp)
    ) {
        Image(
            modifier = Modifier
                .width(120.dp)
                .height(180.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.FillWidth,
            painter = rememberAsyncImagePainter("https://image.tmdb.org/t/p/w500${cast.profilePath}"),
            contentDescription = null
        )
        Text(
            text = cast.name,
            color = LightGrey,
            fontSize = 12.sp,
            fontFamily = inter,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}
