package com.example.mathsquare

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.game_popup.view.*
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val login = findViewById<Button>(R.id.login)
        val play = findViewById<Button>(R.id.play_game)
        val user = findViewById<TextView>(R.id.logged_user)
        val exit = findViewById<Button>(R.id.exit_game)
        val settings = findViewById<Button>(R.id.view_settings)
        val ranking = findViewById<Button>(R.id.view_ranking)

        val lang = findViewById<Button>(R.id.toggle_language)

        lang.setOnClickListener{
            changeLang()
        }

        auth = FirebaseAuth.getInstance()

        if(auth.currentUser==null){
            user.setText(getString(R.string.log_in))
            login.setOnClickListener {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }else{
            user.setText(auth.currentUser?.displayName)
            login.setOnClickListener {
                val intent = Intent(this, UserActivity::class.java)
                startActivity(intent)
            }
        }
        exit.setOnClickListener {
            finish()
        }
        play.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            //startActivity(intent)
            val gamePopup = LayoutInflater.from(this).inflate(R.layout.game_popup,null)

            val mBuilder = AlertDialog.Builder(this)
                .setView(gamePopup)

            val mAlertDialog = mBuilder.show()

            val gamemode = gamePopup.gamemode
            val difficulty = gamePopup.difficulty


            val start =  gamePopup.findViewById<Button>(R.id.start)
            start.setOnClickListener{
                var gamemodeId: Int = gamemode.checkedRadioButtonId
                var difficultyId: Int = difficulty.checkedRadioButtonId
                if(difficultyId!=-1&&gamemodeId!=-1){

                    val diffselect = gamePopup.findViewById<RadioButton>(difficultyId)
                    val gamemodeselect = gamePopup.findViewById<RadioButton>(gamemodeId)
                    intent.putExtra("difficulty",diffToNumber(diffselect.text))
                    intent.putExtra("gamemode",gamemodeToNumber(gamemodeselect.text))
                    mAlertDialog.dismiss()
                    startActivity(intent)
                }else{
                    Toast.makeText(this,getString(R.string.prompt_pref),Toast.LENGTH_SHORT).show()
                }
            }

            settings.setOnClickListener{
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
            }

            ranking.setOnClickListener{
                val intent = Intent(this, RankingActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val login = findViewById<Button>(R.id.login)
        val user = findViewById<TextView>(R.id.logged_user)

        auth = FirebaseAuth.getInstance()

        if(auth.currentUser==null){
            user.setText(getString(R.string.log_in))
            login.setOnClickListener {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }else{
            user.setText(auth.currentUser?.displayName)
            login.setOnClickListener {
                val intent = Intent(this, UserActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun diffToNumber(s:CharSequence):Int{
        when (s){
            "Hard"->return 2
            "Medium"->return 1
            "Easy"->return 0
        }
        return 0
    }
    private fun gamemodeToNumber(s:CharSequence):Int{
        when(s){
            "Decimal"->return 0
            "Hexadecimal"->return 1
        }
        return 0
    }

    private fun changeLang(){
        if(Locale.getDefault().toString() == "th_TH" || Locale.getDefault().toString() == "th") {
            setLocale("en") //if language is Thai change the language to eng
        } else if (Locale.getDefault().toString() == "en" || Locale.getDefault().toString() == "en_US") {
            setLocale("th")// if language eng change to thai
        }
    }
    private fun setLocale(Lang: String?) {
        val languageCode = Lang
        val config = resources.configuration
        val locale = Locale(languageCode) //get locale of this language

        Locale.setDefault(locale) //set new locale or new language
        //config.locale = locale
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics) //update config to new resource
        //createConfigurationContext(config)
        recreate() //recreate the app
    }
}
