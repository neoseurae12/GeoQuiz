package com.bignerdranch.android.geoquiz

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bignerdranch.android.geoquiz.databinding.ActivityMainBinding

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val quizViewModel: QuizViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(TAG, "Got a QuizViewModel: $quizViewModel")

        binding.trueButton.setOnClickListener { view: View ->
            val isCorrect = checkAnswer(userAnswer = true)
            quizViewModel.score(isCorrect)

            quizViewModel.answer()
            updateAnswerButtons()

            if (quizViewModel.isAllAnswered()) {
                showGrade()
            }
        }

        binding.falseButton.setOnClickListener { view: View ->
            val isCorrect = checkAnswer(userAnswer = false)
            quizViewModel.score(isCorrect)

            quizViewModel.answer()
            updateAnswerButtons()

            if (quizViewModel.isAllAnswered()) {
                showGrade()
            }
        }

        binding.cheatButton.setOnClickListener {
            // Start CheatActivity
            val answerIsTrue = quizViewModel.currentQuestionAnswer
            val intent = CheatActivity.newIntent(this@MainActivity, answerIsTrue)
            startActivity(intent)
        }

        binding.previousButton.setOnClickListener {
            previousQuestion()
        }

        binding.nextButton.setOnClickListener {
            nextQuestion()
        }

        binding.questionTextView.setOnClickListener {
            nextQuestion()
        }

        updateQuestion()
        updateAnswerButtons()
        updateNavigationButtons()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    private fun previousQuestion() {
        quizViewModel.moveToPrevious()

        updateQuestion()
        updateAnswerButtons()
        updateNavigationButtons()
    }

    private fun nextQuestion() {
        quizViewModel.moveToNext()

        updateQuestion()
        updateAnswerButtons()
        updateNavigationButtons()
    }

    private fun updateQuestion() {
        val questionTextResId = quizViewModel.currentQuestionText
        binding.questionTextView.setText(questionTextResId)
    }

    private fun updateAnswerButtons() {
        val isAnswered = quizViewModel.currentQuestionIsAnswered
        if (isAnswered) {
            binding.trueButton.isEnabled = false
            binding.falseButton.isEnabled = false
        } else {
            binding.trueButton.isEnabled = true
            binding.falseButton.isEnabled = true
        }
    }

    private fun updateNavigationButtons() {
        updatePreviousButton()
        updateNextButton()
    }

    private fun updatePreviousButton() {
        if (quizViewModel.isFirstQuestion())
            binding.previousButton.visibility = View.INVISIBLE
        else
            binding.previousButton.visibility = View.VISIBLE
    }

    private fun updateNextButton() {
        if (quizViewModel.isLastQuestion())
            binding.nextButton.visibility = View.INVISIBLE
        else
            binding.nextButton.visibility = View.VISIBLE
    }

    private fun checkAnswer(userAnswer: Boolean): Boolean {
        val correctAnswer = quizViewModel.currentQuestionAnswer

        val isCorrect = userAnswer == correctAnswer

        val messageResId: Int = if (isCorrect) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
            .show()

        return isCorrect
    }

    private fun showGrade() {
        val grade = quizViewModel.grade()
        val message = String.format(getString(R.string.percentage_score), grade)
        Toast.makeText(this, message, Toast.LENGTH_SHORT)
            .show()
    }
}