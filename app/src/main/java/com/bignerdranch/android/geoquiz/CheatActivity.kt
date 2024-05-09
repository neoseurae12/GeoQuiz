package com.bignerdranch.android.geoquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bignerdranch.android.geoquiz.databinding.ActivityCheatBinding

private const val EXTRA_ANSWER_IS_TRUE = "com.bignerdranch.android.geoquiz.answer_is_true"
const val EXTRA_ANSWER_SHOWN = "com.bignerdranch.android.geoquiz.answer_shown"

class CheatActivity : AppCompatActivity() {

    lateinit var binding: ActivityCheatBinding

    // CheatActivity 내 UI 상태 데이터 보존을 위한 ViewModel (화면 회전 등)
    private val cheatViewModel: QuizViewModel by viewModels()

    private var answerIsTrue = false
    private var answerIsShown = false

    companion object {
        fun newIntent(packageContext: Context, answerIsTrue: Boolean, answerIsShown: Boolean): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
                putExtra(EXTRA_ANSWER_SHOWN, answerIsShown)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCheatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // MainActivity 로부터 넘겨 받는 정보들
            // 처음 CheatActivity 로 들어올 때, CheatActivity 에서 나갔다가 다시 들어올 때 필요
        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)  // 해당 문제의 정답
        answerIsShown = intent.getBooleanExtra(EXTRA_ANSWER_SHOWN, false)   // 이전에 cheat 를 한 번이라도 했는가 여부

        binding.showAnswerButton.setOnClickListener {
            cheatViewModel.isCheater = true

            showAnswer()

            setAnswerShownResult(true)
        }

        // 해당 문제에 한하여, 이미 한 번이라도 cheat 를 했다면 나갔다 다시 들어와도, 화면을 회전해도, 정답 텍스트를 보여줌
        if (answerIsShown || cheatViewModel.isCheater) {
            showAnswer()

            setAnswerShownResult(true)
        }
    }

    // answerTextView 에 정답(TRUE/FALSE)을 노출하기
    private fun showAnswer() {
        val answerText = when {
            answerIsTrue -> R.string.true_button
            else -> R.string.false_button
        }
        binding.answerTextView.setText(answerText)
    }

    // 정답이 노출되었음을 MainActivity 에 알림
    private fun setAnswerShownResult(isAnswerShown: Boolean) {
        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
        }
        setResult(Activity.RESULT_OK, data)
    }
}