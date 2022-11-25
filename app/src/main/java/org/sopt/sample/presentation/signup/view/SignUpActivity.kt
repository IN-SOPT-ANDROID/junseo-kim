package org.sopt.sample.presentation.signup.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.google.android.material.snackbar.Snackbar
import org.sopt.sample.R
import org.sopt.sample.databinding.ActivitySignUpBinding
import org.sopt.sample.presentation.login.view.LoginActivity
import org.sopt.sample.presentation.signup.viewModel.SignUpViewModel


class SignUpActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignUpBinding
    private val viewModel by lazy { SignUpViewModel() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        activateBtn()
        signUp()
    }

    private fun activateBtn() {
        binding.idEt.addTextChangedListener {
            setTextWatcher()
        }
        binding.pwET.addTextChangedListener {
            setTextWatcher()
        }
        binding.etName.addTextChangedListener {
            setTextWatcher()
        }
    }

    private fun setTextWatcher() {

        if (binding.idEt.text.length *
            binding.pwET.text.length *
            binding.etName.text.length != 0
        ) {
            binding.signupBtn.setBackgroundColor(getColor(R.color.blue_700))
            binding.signupBtn.isClickable = true
        } else {
            binding.signupBtn.setBackgroundColor(getColor(R.color.grey_200))
            binding.signupBtn.isClickable = false
        }
    }

    private fun signUp() {
        binding.signupBtn.setOnClickListener {
            if (binding.idEt.text.length < 6) {
                Snackbar.make(binding.root, "아이디는 6자 이상으로 만들어주세요.", Snackbar.LENGTH_SHORT).show()
            } else if (binding.pwET.text.length !in 8..12) {
                Snackbar.make(binding.root, "비밀번호는 8자 ~ 12자로 만들어주세요.", Snackbar.LENGTH_SHORT).show()
            } else {
                viewModel.signUp(
                    binding.idEt.text.toString(),
                    binding.pwET.toString(), binding.etName.toString()
                )

                viewModel.signUpResult.observe(this) {
                    signUpSuccess()
                }

                viewModel.errorMessage.observe(this) {
                    Toast.makeText(
                        this@SignUpActivity,
                        "회원가입 실패, 상태 코드 $it", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun signUpSuccess() {
        val intent = Intent(this@SignUpActivity.parent, LoginActivity::class.java)
        startActivity(intent)
        Toast.makeText(
            this@SignUpActivity,
            "회원가입 성공, 로그인 하세요!", Toast.LENGTH_SHORT
        ).show()
        finish()
    }
}