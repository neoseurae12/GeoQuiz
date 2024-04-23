package com.bignerdranch.android.geoquiz

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"

const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"
const val IS_CHEATER_KEY = "IS_CHEATER_KEY"

private val questionBank = listOf(
    Question(R.string.question_australia, true),
    Question(R.string.question_oceans, true),
    Question(R.string.question_mideast, false),
    Question(R.string.question_africa, false),
    Question(R.string.question_americas, true),
    Question(R.string.question_asia, true),
)

class QuizViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {

    private var currentIndex: Int
        get() = savedStateHandle.get(CURRENT_INDEX_KEY) ?: 0
        set(value) = savedStateHandle.set(CURRENT_INDEX_KEY, value)


    private var totalScore = 0

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId

    val currentQuestionIsAnswered: Boolean
        get() = questionBank[currentIndex].isAnswered

    var isCheater: Boolean
        get() = savedStateHandle.get(IS_CHEATER_KEY) ?: false
        set(value) = savedStateHandle.set(IS_CHEATER_KEY, value)

    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    fun moveToPrevious() {
        currentIndex = (currentIndex - 1) % questionBank.size
    }

    fun answer() {
        questionBank[currentIndex].isAnswered = true
    }

    fun grade(): Double {
        Log.d(TAG, "Total Score: $totalScore")

        val grade = 100.0 * totalScore / questionBank.size
        Log.d(TAG, "Grade(%): $grade")

        return grade
    }

    fun score(isCorrect: Boolean) {
        val score = if (isCorrect) 1 else 0
        totalScore += score
    }

    fun isFirstQuestion(): Boolean {
        return currentIndex == 0
    }

    fun isLastQuestion(): Boolean {
        return currentIndex == (questionBank.size - 1)
    }

    fun isAllAnswered(): Boolean {
        return questionBank.all { it.isAnswered }
    }
}