package org.sopt.sample

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import org.sopt.sample.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    lateinit var resultLauncher: ActivityResultLauncher<Intent>
    lateinit var binding: ActivityLoginBinding
    lateinit var id: String
    lateinit var pw : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginBtn.setOnClickListener {
            if (binding.idEt.text.length >= 6) {
                loginSuccess()
            } else {
                loginFail()
            }
        }
        binding.signupBtn.setOnClickListener {

        }
    }

    fun loginSuccess() {
        if(binding.idEt.text.toString() == "123" || binding.pwET.text.toString() == pw) {
            Snackbar.make(binding.root, "로그인 성공", Snackbar.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun loginFail() {
        Snackbar.make(
            binding.root, "로그인 실패", Snackbar.LENGTH_SHORT
        ).apply {
            anchorView = binding.pwTV // 특정뷰 위로 스낵바 이동
        }.show()
    }

    fun clickSignUp() {
        binding.signupBtn.setOnClickListener {

            resultLauncher =
                registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                    if (result.resultCode == Activity.RESULT_OK) {
                        id = result.data?.getStringExtra("id") ?: ""
                        pw = result.data?.getStringExtra("pw") ?: ""
                    }
                }
            val intent = Intent(this, SignUpActivity::class.java)
            resultLauncher.launch(intent)
        }
    }
}