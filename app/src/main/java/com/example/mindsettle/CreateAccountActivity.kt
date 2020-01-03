package com.example.mindsettle

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_create.*

class CreateAccountActivity : AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        buttonSignUp.setOnClickListener {
            showError()
        }

    }

    private fun showError(){
        if(editTextEmail.text.toString().isEmpty()){
            textInputLayoutEmail.error = "Please enter a valid email."
        } else{
            textInputLayoutEmail.error = null
        }

        if (editTextPass.length() < 6){
            textInputLayoutPass.error = "TPassword must be at least 6 characters long."
        } else{
            textInputLayoutPass.error = null
        }

        if(editTextConfirmPass.text.toString() != editTextPass.text.toString()){
            textInputLayoutConfirmPass.error = "Password don't match. "
        } else{
            textInputLayoutConfirmPass.error = null
        }

        if(editTextNickname.text.toString().isEmpty()){
            textInputLayoutNickname.error = "Please enter a valid nickname."
        } else{
            textInputLayoutNickname.error = null
        }

        if(editTextBirthYear.length() != 4){
            textInputLayoutBirthYear.error = "Please enter a valid birth year."
        } else{
            textInputLayoutBirthYear.error = null
        }

        if(editTextCountry.text.toString().isEmpty()){
            textInputLayoutCountry.error = "Please enter a valid country."
        } else{
            textInputLayoutCountry.error = null
        }

    }



}