package com.example.mindsettle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        buttonCreateAcc.setOnClickListener {
            createAccount()
        }

        buttonSignHome.setOnClickListener {
            signIn()
        }
    }



    private fun createAccount(){
        //Explicit Intent
        val intent = Intent(this, CreateAccountActivity::class.java)

        //start the second activity. With no return value
        startActivity(intent)

    }

    private fun signIn(){
        //Explicit Intent
        val intent = Intent (this, SignInActivity::class.java)

        //start the second activity with no return value
        startActivity(intent)
    }
}
