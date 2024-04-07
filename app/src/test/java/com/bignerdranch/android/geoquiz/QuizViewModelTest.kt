package com.bignerdranch.android.geoquiz

import androidx.lifecycle.SavedStateHandle
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class QuizViewModelTest {
    @Test
    fun providesExpectedQuestionText() {
        // given (set-up)
        val savedStateHandle = SavedStateHandle()
        val quizViewModel = QuizViewModel(savedStateHandle)
        // (when (test))
        // then (verify)
        assertEquals(R.string.question_australia, quizViewModel.currentQuestionText)
    }

    @Test
    fun wrapsAroundQuestionBank() {
        // given
        val savedStateHandle = SavedStateHandle(mapOf(CURRENT_INDEX_KEY to 5))
        val quizViewModel = QuizViewModel(savedStateHandle)
        // when
        assertEquals(R.string.question_asia, quizViewModel.currentQuestionText)
        quizViewModel.moveToNext()
        // then
        assertEquals(R.string.question_australia, quizViewModel.currentQuestionText)
    }

    @Test
    fun matchesWithCurrentQuestionAnswer() {
        // given
        val savedStateHandle = SavedStateHandle(mapOf(CURRENT_INDEX_KEY to 3))
        val quizViewModel = QuizViewModel(savedStateHandle)
        // when
        assertFalse(quizViewModel.currentQuestionAnswer)
        quizViewModel.moveToNext()
        // then
        assertTrue(quizViewModel.currentQuestionAnswer)
    }
}