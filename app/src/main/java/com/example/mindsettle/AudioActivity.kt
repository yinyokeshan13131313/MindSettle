package com.example.mindsettle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_audio.*

class AudioActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio)
        imageFemale.setOnClickListener {
            //Explicit Intent
            val intent = Intent(this, AudioFemaleActivity::class.java)

            //start the second activity. With no return value
            startActivity(intent)
        }

        imageMale.setOnClickListener {
            //Explicit Intent
            val intent = Intent(this, AudioMaleActivity::class.java)

            //start the second activity. With no return value
            startActivity(intent)
        }
    }
}
