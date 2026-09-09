package com.example.nit3213finalproject.ui.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class LoginViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun studentId_withLetters_shouldBeInvalid() {

        val studentId = "s8065045"

        val isValid =
            studentId.matches(Regex("^\\d+$"))

        assertEquals(false, isValid)
    }

    @Test
    fun studentId_withNumbersOnly_shouldBeValid() {

        val studentId = "8065045"

        val isValid =
            studentId.matches(Regex("^\\d+$"))

        assertEquals(true, isValid)
    }

    @Test
    fun firstName_shouldRemainCaseSensitive() {

        val correctName = "Wongsakorn"
        val wrongName = "wongsakorn"

        assertEquals(false, correctName == wrongName)
    }
}