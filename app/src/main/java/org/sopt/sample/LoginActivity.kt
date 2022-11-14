package org.sopt.sample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import org.sopt.sample.data.remote.AuthService
import org.sopt.sample.data.remote.RequestLoginDTO
import org.sopt.sample.data.remote.ResponseLoginDTO
import org.sopt.sample.data.remote.ServicePool
import org.sopt.sample.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    lateinit var loginService : AuthService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        login()
        clickSignUp()
    }

    private fun login() {
        binding.btnLogin.setOnClickListener {
            loginService = ServicePool.authService
            loginService.login(RequestLoginDTO(
                binding.idEt.text.toString(), binding.pwET.text.toString()))
                .enqueue(object : Callback<ResponseLoginDTO> {
                    override fun onResponse(
                        call: Call<ResponseLoginDTO>,
                        response: Response<ResponseLoginDTO>
                    ) {
                        if(response.isSuccessful){
                            loginSuccess()
                        }else{
                            loginBadResponse()
                        }
                    }

                    override fun onFailure(call: Call<ResponseLoginDTO>, t: Throwable) {
                        loginNoResponse()
                    }
                })
        }
    }

    private fun loginSuccess() {
        Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
    private fun loginBadResponse(){
        Toast.makeText(this@LoginActivity,
            "로그인 실패, 40X 응답값", Toast.LENGTH_SHORT).show()
    }
    private fun loginNoResponse(){
        Toast.makeText(this@LoginActivity,
            "네트워크 연결 미약", Toast.LENGTH_SHORT).show()
    }

    private fun clickSignUp() {
        binding.btnSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}