package com.nesine.casestudy.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nesine.casestudy.R
import com.nesine.casestudy.ui.list.ListFragment

class CaseStudyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_case_study)
        supportFragmentManager.beginTransaction().run {
           replace(R.id.container_fragment,ListFragment())
           commit()
        }
    }
}