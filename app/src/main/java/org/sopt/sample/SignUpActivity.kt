package org.sopt.sample

import android.content.Intent
import android.content.Intent.getIntent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.google.android.material.snackbar.Snackbar
import org.sopt.sample.data.remote.AuthService
import org.sopt.sample.data.remote.RequestSignUpDTO
import org.sopt.sample.data.remote.ResponseSignUpDTO
import org.sopt.sample.data.remote.ServicePool
import org.sopt.sample.databinding.ActivitySignUpBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SignUpActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignUpBinding
    lateinit var loginService : AuthService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        btnActivate()
        signUp()
    }
    private fun btnClickable(){
        if (binding.idEt.text.length*
            binding.pwET.text.length*
            binding.etName.text.length != 0){
            binding.signupBtn.setBackgroundColor(getColor(R.color.blue_700))
            binding.signupBtn.isClickable = true }
        else{
            binding.signupBtn.setBackgroundColor(getColor(R.color.grey_200))
            binding.signupBtn.isClickable = false
        }
    }
    private fun btnActivate() {
        binding.idEt.addTextChangedListener {
            btnClickable()
        }
        binding.pwET.addTextChangedListener {
            btnClickable()
        }
        binding.etName.addTextChangedListener {
            btnClickable()
        }
    }

    private fun signUp() {
        binding.signupBtn.setOnClickListener {
            if(binding.idEt.text.length < 6) {
                Snackbar.make(binding.root, "아이디는 6자 이상으로 만들어주세요.", Snackbar.LENGTH_SHORT).show()
            }
            else if(binding.pwET.text.length !in 8..12 ){
                Snackbar.make(binding.root, "비밀번호는 8자 ~ 12자로 만들어주세요.", Snackbar.LENGTH_SHORT).show()
            }
            else{
                loginService = ServicePool.authService
                loginService.signUp(
                    RequestSignUpDTO(
                    binding.idEt.text.toString(),
                    binding.pwET.text.toString(),
                    binding.etName.text.toString())
                )
                    .enqueue(object  : Callback<ResponseSignUpDTO> {
                        override fun onResponse(
                            call: Call<ResponseSignUpDTO>,
                            response: Response<ResponseSignUpDTO>
                        ) {
                            if (response.isSuccessful){
                                val intent = Intent(this@SignUpActivity.parent, LoginActivity::class.java)
                                startActivity(intent)
                                Toast.makeText(this@SignUpActivity,
                                    "회원가입 성공, 로그인 하세요!", Toast.LENGTH_SHORT).show()
                                if(isFinishing)
                                    finish()
                            }else{
                                Toast.makeText(this@SignUpActivity,
                                    "회원가입 실패", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<ResponseSignUpDTO>, t: Throwable) {
                            Toast.makeText(this@SignUpActivity,
                                "현재 서버와 통신 불가", Toast.LENGTH_SHORT).show()
                        }

                    })
            }
        }
    }


    companion object UserInformation {
        const val id = "id"
        const val pw = "pw"
        const val name = "name"
    }
}