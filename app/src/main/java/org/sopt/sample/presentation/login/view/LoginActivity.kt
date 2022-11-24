package org.sopt.sample.presentation.login.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import org.sopt.sample.data.remote.api.AuthService
import org.sopt.sample.data.remote.api.ServicePool
import org.sopt.sample.data.remote.model.RequestLoginDTO
import org.sopt.sample.data.remote.model.ResponseLoginDTO
import org.sopt.sample.databinding.ActivityLoginBinding
import org.sopt.sample.presentation.login.viewmodel.LoginViewModel
import org.sopt.sample.presentation.main.view.MainActivity
import org.sopt.sample.presentation.signup.view.SignUpActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    lateinit var loginService: AuthService
    private val viewModel by viewModels<LoginViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            viewModel.login(binding.idEt.text.toString(), binding.pwET.text.toString())
        }
        viewModel.loginResult.observe(this) {
            loginSuccess()
        }
        viewModel.errorMessage.observe(this) {
            if (it in (400..499)) {
                loginBadResponse()
            }
        }
        clickSignUpListener()
    }

    private fun login() {
        binding.btnLogin.setOnClickListener {
            loginService = ServicePool.authService
            loginService.login(
                RequestLoginDTO(
                    binding.idEt.text.toString(), binding.pwET.text.toString()
                )
            )
                .enqueue(object : Callback<ResponseLoginDTO> {
                    override fun onResponse(
                        call: Call<ResponseLoginDTO>,
                        response: Response<ResponseLoginDTO>
                    ) {
                        if (response.isSuccessful) {
                            loginSuccess()
                        } else {
                            loginBadResponse()
                        }
                    }

                    override fun onFailure(call: Call<ResponseLoginDTO>, t: Throwable) {
                        loginNoResponse()
                    }
                })
        }
    }

    fun loginSuccess() {
        Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun loginBadResponse() {
        Toast.makeText(
            this@LoginActivity,
            "로그인 실패, 40X 응답값", Toast.LENGTH_SHORT
        ).show()
    }

    fun loginNoResponse() {
        Toast.makeText(
            this@LoginActivity,
            "네트워크 연결 미약", Toast.LENGTH_SHORT
        ).show()
    }

    private fun clickSignUpListener() {
        binding.btnSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}