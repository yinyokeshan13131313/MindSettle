package com.example.mindsettle

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_create.*
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.activity_edit_profile.editTextUsername

class EditProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        var token = getSharedPreferences("username", Context.MODE_PRIVATE)
        var username = token.getString("loginusername", "")

        editTextUsername.text = "Username: " + username.toString()

        buttonEditProfileeee.setOnClickListener {
            var checkValid = showError()
            if(checkValid == 0){
                updateUser(editTextEmailEdit.text.toString(),editTextPassEdit.text.toString(),editTextBirthYearEdit.text.toString().toInt(),editTextCountryEdit.text.toString())
            }
        }
    }

    private fun showError(): Int {
        var errorNum = 0;
        if (editTextEmailEdit.text.toString().isEmpty()) {
            textInputLayoutEmailEdit.error = "Email cannot be empty."
            errorNum++;
        } else if(!isValidEmail(editTextEmailEdit.text.toString())){
            textInputLayoutEmailEdit.error = "Please enter a valid email."
        } else {
            textInputLayoutEmailEdit.error = null
        }

        if (editTextPassEdit.length() < 5) {
            textInputLayoutPassEdit.error = "Password must be at least 6 characters long."
            errorNum++;
        } else {
            textInputLayoutPassEdit.error = null
        }

        if (editTextConfirmPassEdit.text.toString() != editTextPassEdit.text.toString()) {
            textInputLayoutConfirmPassEdit.error = "Password don't match. "
            errorNum++;
        } else {
            textInputLayoutConfirmPassEdit.error = null
        }

        if (editTextBirthYearEdit.length() != 4 || editTextBirthYearEdit.text.toString().toInt() > 2020) {
            textInputLayoutBirthYearEdit.error = "Please enter a valid birth year."
            errorNum++;
        } else {
            textInputLayoutBirthYearEdit.error = null
        }

        if (editTextCountryEdit.text.toString().isEmpty()) {
            textInputLayoutCountryEdit.error = "Country cannot be empty."
            errorNum++;
        } else {
            textInputLayoutCountryEdit.error = null
        }

        return errorNum;
    }

    private fun isValidEmail(target: CharSequence): Boolean {
        return if (TextUtils.isEmpty(target)) {
            false
        } else {
            android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()
        }
    }

    private fun updateUser(email:String, password:String, birthyear:Int, country: String) {
        var token = getSharedPreferences("username", Context.MODE_PRIVATE)
        var username = token.getString("loginusername", "")
        val url = getString(R.string.url_server) + getString(R.string.url_user_update) + "?email=" +email + "&password=" + password + "&birthyear=" + birthyear + "&country=" + country + "&username=" + username

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                try {
                    if (response != null) {
                        val success: String = response.get("success").toString()

                        if (success.equals("1")) {
                            Toast.makeText(applicationContext, "Record saved", Toast.LENGTH_LONG).show()
                            //Add record to user list
                            //userList.add(user)
                            //Explicit Intent
                            val intent = Intent(this, Main2BottomActivity::class.java)
                            //start the second activity. With no return value
                            startActivity(intent)
                        } else {
                            Toast.makeText(applicationContext,"Record not saved", Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                } catch (e: Exception) {
                    Log.d("Main", "Response: %s".format(e.message.toString()))
                }
            },
            Response.ErrorListener { response ->
                Log.d("Main", "Response: %s".format(response.message.toString()))
            }
        )

        // Access the RequestQueue through your singleton class.
        jsonObjectRequest.tag = CreateAccountActivity.TAG
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)

    }
}
