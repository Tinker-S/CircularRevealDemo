package com.mobvoi.demo.circular.reveal

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MotionEvent
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.setTitle(R.string.app_name)
        setSupportActionBar(toolbar)

        findViewById<View>(R.id.root).setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val intent = Intent(this@MainActivity, AnimationActivity::class.java)
                intent.putExtra("x", event.x.toInt())
                intent.putExtra("y", event.y.toInt())
                startActivity(intent)
            }
            true
        }
    }
}