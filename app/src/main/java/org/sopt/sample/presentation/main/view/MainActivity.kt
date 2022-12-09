package org.sopt.sample.presentation.main.view

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import org.sopt.sample.R
import org.sopt.sample.databinding.ActivityMainBinding
import org.sopt.sample.presentation.main.fragment.GalleryFragment
import org.sopt.sample.presentation.main.fragment.HomeFragment
import org.sopt.sample.presentation.main.fragment.MusicFragment
import org.sopt.sample.presentation.main.fragment.SearchFragment

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val userId: Int = intent.getIntExtra(MainActivity.userId, 0)
        firstFragment()
        binding.bnvMain.setOnItemSelectedListener { item ->
            changeFragment(item, userId)
        }
    }

    private fun firstFragment() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)
        if (currentFragment == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainerView, HomeFragment())
                .commit()
        }
    }

    private fun changeFragment(item: MenuItem, userId: Int): Boolean {
        when (item.itemId) {
            R.id.item_home -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView, HomeFragment())
                    .commit()
                return true
            }
            R.id.item_search -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView, SearchFragment())
                    .commit()
                return true
            }
            R.id.item_gallery -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView, GalleryFragment(userId))
                    .commit()
                return true
            }
            R.id.item_music -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView, MusicFragment())
                    .commit()
                return true
            }
            else -> return false
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