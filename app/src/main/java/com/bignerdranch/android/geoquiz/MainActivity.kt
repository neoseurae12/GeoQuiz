package com.bignerdranch.android.geoquiz

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bignerdranch.android.geoquiz.databinding.ActivityMainBinding

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true),
    )

    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.trueButton.setOnClickListener { view: View ->
            checkAnswer(userAnswer = true)

            questionBank[currentIndex].isAnswered = true
            updateAnswerButtons()

            grade()
        }

        binding.falseButton.setOnClickListener { view: View ->
            checkAnswer(userAnswer = false)

            questionBank[currentIndex].isAnswered = true
            updateAnswerButtons()

            grade()
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
        currentIndex = (currentIndex - 1) % questionBank.size

        updateQuestion()
        updateAnswerButtons()
        updateNavigationButtons()
    }

    private fun nextQuestion() {
        currentIndex = (currentIndex + 1) % questionBank.size

        updateQuestion()
        updateAnswerButtons()
        updateNavigationButtons()
    }

    private fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].textResId
        binding.questionTextView.setText(questionTextResId)
    }

    private fun updateAnswerButtons() {
        val isAnswered = questionBank[currentIndex].isAnswered
        if (isAnswered) {
            binding.trueButton.isEnabled = false
            binding.falseButton.isEnabled = false
        } else {
            binding.trueButton.isEnabled = true
            binding.falseButton.isEnabled = true
        }
    }

    private fun updateNavigationButtons() {
        val lastIndex = questionBank.size - 1
        when (currentIndex) {
            0 -> {
                binding.previousButton.visibility = View.INVISIBLE
                binding.nextButton.visibility = View.VISIBLE
            }
            lastIndex -> {
                binding.previousButton.visibility = View.VISIBLE
                binding.nextButton.visibility = View.INVISIBLE
            }
            else -> {
                binding.previousButton.visibility = View.VISIBLE
                binding.nextButton.visibility = View.VISIBLE
            }
        }
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = questionBank[currentIndex].answer

        val messageResId = if (userAnswer == correctAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
            .show()
    }
}