package org.sopt.sample.presentation.signup.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import org.sopt.sample.R
import org.sopt.sample.databinding.ActivitySignUpBinding
import org.sopt.sample.presentation.login.view.LoginActivity
import org.sopt.sample.presentation.signup.viewModel.SignUpViewModel


class SignUpActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignUpBinding
    private val viewModel by lazy { SignUpViewModel() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        binding.vm = viewModel
        binding.lifecycleOwner = this

        observeId()
        observePw()
        observeName()

        binding.btnSignUp.setOnClickListener {
            signUp()
        }
    }

    private fun observeId() {
        viewModel.isUserIdSuit.observe(this) {
            if (it == true)
                binding.layoutEtId.boxStrokeColor = getColor(R.color.blue_500)
            else
                binding.layoutEtId.boxStrokeColor = getColor(R.color.red_500)
            setBtnColor()
        }
    }

    private fun observePw() {
        viewModel.isUserPwSuit.observe(this) {
            if (it == true)
                binding.layoutEtPw.boxStrokeColor = getColor(R.color.blue_500)
            else
                binding.layoutEtPw.boxStrokeColor = getColor(R.color.red_500)
            setBtnColor()
        }
    }

    private fun observeName() {
        viewModel.isUserNameSuit.observe(this) {
            if (it == true)
                binding.layoutEtName.boxStrokeColor = getColor(R.color.blue_500)
            else
                binding.layoutEtName.boxStrokeColor = getColor(R.color.red_500)
            setBtnColor()
        }
    }

    private fun setBtnColor() {
        if (viewModel.checkSignUpFormat()) {
            binding.btnSignUp.setBackgroundColor(getColor(R.color.blue_700))
            binding.btnSignUp.isClickable = true
        } else {
            binding.btnSignUp.setBackgroundColor(getColor(R.color.grey_200))
            binding.btnSignUp.isClickable = false
        }
    }

    private fun signUp() {
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