package com.yurastico.pingpongx

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import com.yurastico.pingpongx.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAnimation()

        nextScreen()
    }

    private fun nextScreen() {
        Handler(Looper.getMainLooper()).postDelayed({
            val playerIntent = Intent(this,PlayerActivity::class.java)
            startActivity(playerIntent)
            finish()
        },2000)
    }

    private fun initAnimation() {
        val anim = AnimationUtils.loadAnimation(this, R.anim.splash)
        binding.ivLogo.startAnimation(anim)
        binding.ivLogoName.startAnimation(anim)
    }
}