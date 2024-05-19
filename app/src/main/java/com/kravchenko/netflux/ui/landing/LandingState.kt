package com.kravchenko.netflux.ui.landing

import android.graphics.drawable.Drawable
import com.kravchenko.netflux.models.Movie

sealed class LandingState {
    data object Loading : LandingState()
    data class Success(val posters: List<Drawable>) : LandingState()
    data class Error(val message: String) : LandingState()
}