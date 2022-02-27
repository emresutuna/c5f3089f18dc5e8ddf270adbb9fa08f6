package com.example.spaceexample.activities

import android.os.Bundle
import com.example.spaceexample.R
import com.example.spaceexample.utils.HideActionBarActivity

class MainActivity : HideActionBarActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}