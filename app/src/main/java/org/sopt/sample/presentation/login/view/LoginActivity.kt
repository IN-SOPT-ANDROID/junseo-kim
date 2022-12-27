package org.sopt.sample.presentation.login.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import org.sopt.sample.databinding.ActivityLoginBinding
import org.sopt.sample.presentation.login.viewmodel.LoginViewModel
import org.sopt.sample.presentation.main.view.MainActivity
import org.sopt.sample.presentation.signup.view.SignUpActivity

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    private val viewModel by viewModels<LoginViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAdapter()
    }

    private fun initAdapter() {
        binding.btnLogin.setOnClickListener {
            viewModel.login(binding.etId.text.toString(), binding.etPw.text.toString())
        }
        viewModel.loginResult.observe(this) {
            loginSuccess(it.result.id)
        }
        viewModel.errorMessage.observe(this) {
            loginFail(it)
        }
        clickSignUpListener()
    }

    fun loginSuccess(userId: Int) {
        Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(MainActivity.userId, userId)
        startActivity(intent)
    }

    private fun loginFail(errorCode: Int) {
        if (errorCode in 400..499) {
            Toast.makeText(
                this,
                "상태 코드 : $errorCode, 클라이언트 요청 오류 발생",
                Toast.LENGTH_SHORT
            ).show()
        } else if (errorCode >= 500) {
            Toast.makeText(
                this,
                "상태 코드 : $errorCode, 서버 응답 오류 발생",
                Toast.LENGTH_SHORT
            ).show()
        } else
            Toast.makeText(
                this,
                "상태 코드 : $errorCode, 오류 발생",
                Toast.LENGTH_SHORT
            ).show()
    }

    private fun clickSignUpListener() {
        binding.btnSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}