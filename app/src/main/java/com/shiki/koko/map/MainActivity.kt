package com.shiki.koko.map

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.shiki.koko.amap.AMapActivity
import com.shiki.koko.amap.AMapShowActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent(this, AMapActivity::class.java)
        startActivity(intent)
    }
}