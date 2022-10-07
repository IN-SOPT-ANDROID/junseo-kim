package org.sopt.sample

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import org.sopt.sample.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    lateinit var resultLauncher: ActivityResultLauncher<Intent>
    lateinit var binding: ActivityLoginBinding
    var id: String? = null
    var pw : String? = null
    var mbti : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    id = result.data?.getStringExtra("id") ?: ""
                    pw = result.data?.getStringExtra("pw") ?: ""
                    mbti = result.data?.getStringExtra("mbti") ?: ""
                }
            }

        binding.loginBtn.setOnClickListener {

            if (binding.idEt.text.toString() == id || binding.pwET.text.toString() == pw) {
                loginSuccess()
            } else {
                loginFail()
            }
        }
        binding.signupBtn.setOnClickListener {
            clickSignUp()
        }
    }

    fun loginSuccess() {
        Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("mbti", mbti)
        intent.putExtra("id", id)
        startActivity(intent)
        finish()
    }

    fun loginFail() {
        Snackbar.make(
            binding.root, "로그인 실패", Snackbar.LENGTH_SHORT).show()
    }

    fun clickSignUp() {
        val intent = Intent(this, SignUpActivity::class.java)
        resultLauncher.launch(intent)
    }
}