package com.example.mindsettle

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity: AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        var token = getSharedPreferences("username", Context.MODE_PRIVATE)
        tvSession.text = "Sharepreferences " + token.getString("loginusername", "")

    }
}