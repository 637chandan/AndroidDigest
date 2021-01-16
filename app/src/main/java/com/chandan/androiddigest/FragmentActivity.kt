package com.chandan.androiddigest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_fragment.view.*

class FragmentActivity : AppCompatActivity() {
    lateinit var firstFrag: Fragment
    lateinit var secondFrag: SecondFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)
        firstFrag = FirstFragment()
        secondFrag = SecondFragment()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment,firstFrag)
            commit()
        }
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