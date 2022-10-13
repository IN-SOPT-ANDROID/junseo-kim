package org.sopt.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.RecyclerView
import org.sopt.sample.fragments.GalleryFragment
import org.sopt.sample.fragments.SearchFragment
import org.sopt.sample.databinding.ActivityMainBinding
import org.sopt.sample.fragments.HomeFragment

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firstFragment()
        binding.bnvMain.setOnItemSelectedListener { item ->
            changeFragment(item)
        }
        binding.bnvMain.setOnItemReselectedListener { item ->
            scrollRecyclerView(item)
        }
    }

    private fun firstFragment(){
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)
        if(currentFragment == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainerView, HomeFragment())
                .commit()
        }
    }
    private fun changeFragment(item : MenuItem) : Boolean{
        when(item.itemId) {
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
                    .replace(R.id.fragmentContainerView, GalleryFragment())
                    .commit()
                return true
            }
            else -> return false
        }
    }
    private fun scrollRecyclerView(item : MenuItem){
        when(item.itemId) {
            R.id.item_home -> {
                val recyclerView = findViewById<RecyclerView>(R.id.rv_github_users)
                recyclerView.smoothScrollToPosition(0)
            }
        }
    }
}