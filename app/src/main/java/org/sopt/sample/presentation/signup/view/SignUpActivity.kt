package org.sopt.sample.presentation.signup.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import org.sopt.sample.R
import org.sopt.sample.databinding.ActivitySignUpBinding
import org.sopt.sample.presentation.login.view.LoginActivity
import org.sopt.sample.presentation.main.view.MainActivity.Companion.idPattern
import org.sopt.sample.presentation.main.view.MainActivity.Companion.pwPattern
import org.sopt.sample.presentation.signup.viewModel.SignUpViewModel
import java.util.regex.Pattern


class SignUpActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignUpBinding
    private val viewModel by lazy { SignUpViewModel() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.setTextWatcher(binding)
        observeId()
        observePw()
        activateBtn()
        signUp()
    }

    private fun observeId() {
        viewModel.userPw.observe(this) {
            val pwPattern = Pattern.compile(pwPattern)
            val matcher = pwPattern.matcher(it)

            if (matcher.matches() || it == "") {
                binding.layoutEtPw.boxStrokeColor = getColor(R.color.blue_500)
            } else {
                binding.layoutEtPw.boxStrokeColor = getColor(R.color.red_500)
            }
            setTextWatcher()
        }
    }

    private fun observePw() {
        viewModel.userId.observe(this) {
            val idPattern = Pattern.compile(idPattern)
            val matcher = idPattern.matcher(it)

            if (matcher.matches() || it == "") {
                binding.layoutEtId.boxStrokeColor = getColor(R.color.blue_500)
            } else {
                binding.layoutEtId.boxStrokeColor = getColor(R.color.red_500)
            }
            setTextWatcher()
        }
    }

    private fun activateBtn() {
        binding.etName.addTextChangedListener {
            setTextWatcher()
        }
    }

    private fun setTextWatcher() {
        val idPattern = Pattern.compile(idPattern)
        val idMatcher = idPattern.matcher(binding.etId.text.toString())

        val pwPattern = Pattern.compile(pwPattern)
        val pwMatcher = pwPattern.matcher(binding.etPw.text.toString())

        if (idMatcher.matches() && pwMatcher.matches()
            && binding.etName.text.toString().isNotEmpty()
        ) {
            binding.btnSignUp.setBackgroundColor(getColor(R.color.blue_700))
            binding.btnSignUp.isClickable = true
        } else {
            binding.btnSignUp.setBackgroundColor(getColor(R.color.grey_200))
            binding.btnSignUp.isClickable = false
        }
    }

    private fun signUp() {
        binding.btnSignUp.setOnClickListener {
            viewModel.signUp(
                binding.etId.text.toString(),
                binding.etPw.text.toString(), binding.etName.text.toString()
            )

            viewModel.signUpResult.observe(this) {
                signUpSuccess()
            }

            viewModel.errorMessage.observe(this) {
                signUpFail(it)
            }
        }

    }

    private fun signUpSuccess() {
        val intent = Intent(this@SignUpActivity.parent, LoginActivity::class.java)
        startActivity(intent)
        Toast.makeText(
            this@SignUpActivity,
            "회원가입 성공, 로그인 해주세요!", Toast.LENGTH_SHORT
        ).show()
        finish()
    }

    private fun signUpFail(errorCode: Int) {
        Toast.makeText(
            this@SignUpActivity,
            "회원가입 실패, 상태 코드 $errorCode", Toast.LENGTH_SHORT
        ).show()
    }
}