package org.sopt.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.sopt.sample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        putInfo()

        setContentView(binding.root)
    }

    fun putInfo(){
        binding.nameTv.text = binding.nameTv.text.toString() + intent.getStringExtra(SignUpActivity.id)
        binding.mbtiTv.text = binding.mbtiTv.text.toString() + intent.getStringExtra(SignUpActivity.mbti)
    }
}