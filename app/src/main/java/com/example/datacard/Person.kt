package com.example.datacard

import android.os.Build
import androidx.annotation.RequiresApi
import java.io.Serializable
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class Person(val name: String, val surname: String, val date: LocalDate, val phone: String, val photoUri: String): Serializable {
    companion object {
        @RequiresApi(Build.VERSION_CODES.O)
        private val formatDate = DateTimeFormatter.ofPattern("dd.MM.yyyy")

        @RequiresApi(Build.VERSION_CODES.O)
        fun fromString(date: String): LocalDate {
            return LocalDate.parse(date, formatDate)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getAge(currentDate: LocalDate = LocalDate.now()): Int {
        var age = currentDate.year - date.year
        if (currentDate.monthValue < date.monthValue ||
            (currentDate.monthValue == date.monthValue && currentDate.dayOfMonth < date.dayOfMonth)) {
            age--
        }
        return age
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getMonthsAndDaysToNextBirthday(currentDate: LocalDate = LocalDate.now()): String {
        val nextBirthday = date.withYear(currentDate.year)
        val monthsUntilBirthday = if (nextBirthday.isBefore(currentDate)) nextBirthday.plusYears(1) else nextBirthday

        val monthsUntil = currentDate.until(monthsUntilBirthday, ChronoUnit.MONTHS)

        val daysUntilBirthday = currentDate.until(monthsUntilBirthday, ChronoUnit.DAYS)
        val remainingDays = daysUntilBirthday.toInt() - (monthsUntil * 30)

        return "$monthsUntil месяцев и $remainingDays дней"
    }
}