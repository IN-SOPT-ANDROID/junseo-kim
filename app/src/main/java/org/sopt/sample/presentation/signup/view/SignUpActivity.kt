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
        with(binding) {
            vm = viewModel
            lifecycleOwner = this@SignUpActivity
            btnSignUp.setOnClickListener { signUp() }
        }

        observeId()
        observePw()
        observeName()
    }

    private fun observeId() {
        viewModel.isUserIdSuit.observe(this) {
            binding.layoutEtId.error = if (it || (viewModel.userIdText.value!! == "")) null
            else "ID 형식이 올바르지 않습니다."
            setBtnColor()
        }
    }

    private fun observePw() {
        viewModel.isUserPwSuit.observe(this) {
            binding.layoutEtPw.error = if (it || (viewModel.userPwText.value!! == "")) null
            else "비밀번호 형식이 올바르지 않습니다."
            setBtnColor()
        }
    }

    private fun observeName() {
        viewModel.isUserNameSuit.observe(this) {
            binding.layoutEtName.error = if (it) null else "이름 형식이 올바르지 않습니다."
            setBtnColor()
        }
    }

    private fun setBtnColor() {
        if (viewModel.checkSignUpFormat()) {
            binding.btnSignUp.setBackgroundColor(getColor(R.color.blue_700))
            viewModel.btnEnabled.value = true
        } else {
            binding.btnSignUp.setBackgroundColor(getColor(R.color.grey_200))
            viewModel.btnEnabled.value = false
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