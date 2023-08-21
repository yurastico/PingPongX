package com.yurastico.pingpongx

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.yurastico.pingpongx.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var playerOneScore : Int = 0
    private var playerTwoScore : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpExtras(savedInstanceState)
        setUpListeners()
    }



    private fun setUpExtras(savedInstanceState: Bundle?) {
        binding.tvPlayerOneName.text = intent.getStringExtra(KEY_EXTRA_PLAYER1_NAME)
        binding.tvPlayerTwoName.text = intent.getStringExtra(KEY_EXTRA_PLAYER2_NAME)

        if(savedInstanceState != null) {
            playerOneScore = savedInstanceState.getInt(PLAYER_ONE_SCORE)
            playerTwoScore = savedInstanceState.getInt(PLAYER_TWO_SCORE)
        setUpScorePlayerOne()
            setUpScorePlayerTwo()
        }
    }

    private fun setUpListeners() {



        binding.btPlayerOneScore.setOnClickListener {
            playerOneScore++
            setUpScorePlayerOne()
        }
        binding.btPlayerTwoScore.setOnClickListener {
            playerTwoScore++
            setUpScorePlayerTwo()
        }

        binding.btWhatsApp.setOnClickListener {
            shareWhatsApp()
        }

        binding.btFinishMatch.setOnClickListener {
            val ret = Intent()
            ret.putExtra(KEY_RESULT_EXTRA_PLAYER_ONE_NAME, binding.tvPlayerOneName.text.toString())
            ret.putExtra(KEY_RESULT_EXTRA_PLAYER_TWO_NAME, binding.tvPlayerTwoName.text.toString())
            ret.putExtra(KEY_RESULT_EXTRA_PLAYER_ONE_SCORE,
                binding.tvPlayerOneScore.text.toString().toInt())
            ret.putExtra(KEY_RESULT_EXTRA_PLAYER_TWO_SCORE,
                binding.tvPlayerTwoScore.text.toString().toInt())
            setResult(RESULT_OK, ret)
            super.finish()
        }

        binding.btRevenge.setOnClickListener {
            revenge()
        }
    }

    private fun setUpScorePlayerOne() {
        binding.tvPlayerOneScore.text = playerOneScore.toString()
    }

    private fun setUpScorePlayerTwo() {
        binding.tvPlayerTwoScore.text = playerTwoScore.toString()
    }

    private fun shareWhatsApp() {
        try{
            val whatsAppIntent = Intent(Intent.ACTION_SEND)
            whatsAppIntent.type = "text/plain"
            whatsAppIntent.setPackage("com.whatsapp")
            val message = getString(R.string.message_to_share,
                binding.tvPlayerOneName.text,
                binding.tvPlayerTwoName.text,
                playerOneScore,
                playerTwoScore)
            whatsAppIntent.putExtra(Intent.EXTRA_TEXT, message)
            startActivity(whatsAppIntent)
            } catch (e: ActivityNotFoundException) {
            //Toast.makeText(this, "WhatsApp não instalado",
             //   Toast.LENGTH_LONG).show()
                val appPackageName = "com.whatsapp"
            try {
                startActivity(Intent( Intent.ACTION_VIEW,
                    Uri.parse("market://details?id= $appPackageName ")))
            } catch (anfe: android.content. ActivityNotFoundException ) {
                startActivity(Intent( Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")))
            }
        }
    }


    private fun revenge() {
        playerOneScore = 0
        playerTwoScore = 0
        setUpScorePlayerOne()
        setUpScorePlayerTwo()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(PLAYER_ONE_SCORE, playerOneScore)
        outState.putInt(PLAYER_TWO_SCORE, playerTwoScore)
    }

    companion object {
        const val KEY_EXTRA_PLAYER1_NAME = "KEY_EXTRA_PLAYER1_NAME"
        const val KEY_EXTRA_PLAYER2_NAME = "KEY_EXTRA_PLAYER2_NAME"
        const val PLAYER_ONE_SCORE = "PLAYER_ONE_SCORE"
        const val PLAYER_TWO_SCORE = "PLAYER_TWO_SCORE"
    }
}
