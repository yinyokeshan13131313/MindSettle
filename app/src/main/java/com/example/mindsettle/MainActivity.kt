package com.example.mindsettle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import com.example.mindsettle.Adapter.ListPersonAdapter
import com.example.mindsettle.DBHelper.DBHelper
import com.example.mindsettle.Model.Person
import kotlinx.android.synthetic.main.activity_create.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        buttonCreateAcc.setOnClickListener {
            createAccount()
        }

        buttonSignIn.setOnClickListener {
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
