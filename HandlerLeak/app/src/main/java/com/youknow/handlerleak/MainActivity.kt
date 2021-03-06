package com.youknow.handlerleak

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnLeak.setOnClickListener {
            startActivity(Intent(this, LeakActivity::class.java))
        }

        btnNonLeak.setOnClickListener {
            startActivity(Intent(this, NonLeakActivity::class.java))
        }
    }
}
