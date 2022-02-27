package com.example.spaceexample.utils

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

open class HideActionBarActivity : AppCompatActivity() {
    override fun onCreate( savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       supportActionBar!!.hide()
    }
}