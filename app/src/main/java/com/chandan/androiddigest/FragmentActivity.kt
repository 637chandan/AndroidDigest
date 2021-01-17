package com.chandan.androiddigest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_fragment.*

class FragmentActivity : AppCompatActivity() {
    lateinit var firstFrag: Fragment
    lateinit var secondFrag: SecondFragment

    lateinit var toggle:ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)
        firstFrag = FirstFragment()
        secondFrag = SecondFragment()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment,firstFrag)
            commit()
        }

        toggle = ActionBarDrawerToggle(this, drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        nav_view.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.item1 -> Toast.makeText(applicationContext,"Click Item 1", Toast.LENGTH_SHORT).show()
                R.id.item2 -> {
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.fragment,secondFrag)
                        commit()
                    }
                    Toast.makeText(applicationContext,"Click Item 2", Toast.LENGTH_SHORT).show()
                }
                R.id.item3 -> Toast.makeText(applicationContext,"Click Item 3", Toast.LENGTH_SHORT).show()
            }
            drawerLayout.closeDrawers()
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }

        return super.onOptionsItemSelected(item)
    }
    fun setFirstFrag(view: View) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment,firstFrag)
            commit()
        }
    }
    fun setSecondFrag(view: View) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment,secondFrag)
            commit()
        }
    }


}