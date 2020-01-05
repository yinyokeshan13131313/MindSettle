package com.example.mindsettle

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        var token = getSharedPreferences("username", Context.MODE_PRIVATE)
        textViewUsernameTest.text = token.getString("loginusername", "")

        val testingSession = intent.getStringExtra("username")
        textViewTestSession.text =testingSession

        buttonLogOut.setOnClickListener{
            var editor = token.edit()
            editor.putString("loginusername", "")
            editor.commit()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        buttonTest.setOnClickListener {
            val intent = Intent(this, TestActivity::class.java)
            startActivity(intent)
        }
    }
}