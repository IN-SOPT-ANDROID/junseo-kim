package org.sopt.sample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import org.sopt.sample.databinding.ActivityLoginBinding
import org.sopt.sample.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signupBtn.setOnClickListener {
            if(binding.idEt.text.length < 6 || binding.idEt.text.length > 10) {
                Snackbar.make(binding.root, "아이디는 6자 ~ 10자로 만들어주세요.", Snackbar.LENGTH_SHORT).show()
            }
            else if(binding.pwET.text.length < 8 ||binding.pwET.text.length > 12 ){
                Snackbar.make(binding.root, "비밀번호는 8자 ~ 12자로 만들어주세요.", Snackbar.LENGTH_SHORT).show()
            }
            else{
                val intent = Intent(this, LoginActivity::class.java)
                intent.putExtra("id", binding.idEt.text)
                intent.putExtra("pw", binding.pwET.text)
                setResult(RESULT_OK, intent)
                finish()
            }
        }
    }
}