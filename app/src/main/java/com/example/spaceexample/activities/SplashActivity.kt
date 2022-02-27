package com.example.spaceexample.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import com.example.spaceexample.databinding.ActivitySplashBinding
import com.example.spaceexample.enums.UserCreateShapeEnum
import com.example.spaceexample.utils.HideActionBarActivity
import com.example.spaceexample.utils.SharedPref

@SuppressLint("CustomSplashScreen")
class SplashActivity : HideActionBarActivity() {
    private lateinit var binding: ActivitySplashBinding
    private val splashDuration: Long = 2000
    private lateinit var sharedPref: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPref = SharedPref(this)

        binding.apply {
            splashImage.alpha = 0f
            splashImage.animate().setDuration(splashDuration).alpha(1f).withEndAction {
                userHasCreateShip(sharedPref.getUserCreateSpaceShip() ?: "NOT_CREATED")
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                finish()
            }
        }
    }

    private fun goMainPage() {
        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        startActivity(intent)
    }

    private fun goCreateShipPage() {
        val intent = Intent(this@SplashActivity, CreateShipActivity::class.java)
        startActivity(intent)
    }

    private fun userHasCreateShip(userCreateShip: String) {
        when (userCreateShip) {
            "${UserCreateShapeEnum.CREATED}" -> {
                goMainPage()
                print("CREATED")
            }
            "${UserCreateShapeEnum.NOT_CREATED}" -> {
                goCreateShipPage()
                print("NOT_CREATED")
            }


        }
    }
}