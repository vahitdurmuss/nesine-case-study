package com.nesine.casestudy.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nesine.casestudy.R
import com.nesine.casestudy.ui.detail.OnBackPressedListener
import com.nesine.casestudy.ui.list.ListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CaseStudyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_case_study)
        supportFragmentManager.beginTransaction().run {
           replace(R.id.container_fragment,ListFragment())
           commit()
        }
    }

    override fun onBackPressed() {

        val currentFragment = supportFragmentManager.findFragmentById(R.id.container_fragment)

        if (currentFragment is OnBackPressedListener) {
            currentFragment.onBackPressed()
        }

        super.onBackPressed()
    }
}