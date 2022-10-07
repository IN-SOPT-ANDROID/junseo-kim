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
        val id = intent.getStringExtra("id")
        val mbti = intent.getStringExtra("mbti")

        binding.nameTv.text = binding.nameTv.text.toString() + SignUpActivity.UserInformation.id
        binding.mbtiTv.text = binding.mbtiTv.text.toString() + SignUpActivity.UserInformation.mbti
    }
}