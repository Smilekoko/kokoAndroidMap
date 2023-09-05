package com.shiki.koko.amap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.shiki.koko.amap.databinding.ActivityIndoorMapBinding

//室内地图
class IndoorMapActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIndoorMapBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIndoorMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}