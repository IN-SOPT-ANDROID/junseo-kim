package org.sopt.sample.presentation.main.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.sopt.sample.R
import org.sopt.sample.databinding.ActivityMainBinding
import org.sopt.sample.presentation.main.fragment.GalleryFragment
import org.sopt.sample.presentation.main.fragment.HomeFragment
import org.sopt.sample.presentation.main.fragment.MusicFragment
import org.sopt.sample.presentation.main.fragment.SearchFragment

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var userId: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setFirstFragment()
        setBottomNavigationSelectedEvent()
        userId = intent.getIntExtra(MainActivity.userId, 0)
    }

    private fun setFirstFragment() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container_view)
        if (currentFragment == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container_view, HomeFragment()).commit()
        }
    }

    private fun setBottomNavigationSelectedEvent() {
        binding.bnvMain.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_view, HomeFragment()).commit()
                    return@setOnItemSelectedListener true
                }

                R.id.item_search -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_view, SearchFragment()).commit()
                    return@setOnItemSelectedListener true
                }
                R.id.item_gallery -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_view, GalleryFragment()).commit()
                    return@setOnItemSelectedListener true
                }
                R.id.item_music -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_view, MusicFragment()).commit()
                    return@setOnItemSelectedListener true
                }
                else -> return@setOnItemSelectedListener false
            }
        }
    }

    fun bottomNavigationReselectedListener(func: () -> Unit, itemId: Int) {
        binding.bnvMain.setOnItemReselectedListener {
            when (it.itemId) {
                itemId -> func()
            }
        }

    }

    companion object {
        val tag = "tag"
        val id = "id"
        val pw = "pw"
        val userId = "userId"
        val idPattern = """^(?=.*[0-9])(?=.*[a-zA-Z]).{6,10}$"""
        val pwPattern = """^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[$@$!%*#?&]).{6,12}$"""
    }
}