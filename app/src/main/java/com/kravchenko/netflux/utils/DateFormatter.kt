package com.kravchenko.netflux.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter

object DateFormatter {
    fun formatDate(dateString: String): String {
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val outputFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        val date = LocalDate.parse(dateString, inputFormatter)
        return date.format(outputFormatter)
    }
}