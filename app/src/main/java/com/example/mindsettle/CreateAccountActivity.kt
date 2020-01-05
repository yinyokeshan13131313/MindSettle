package com.example.mindsettle

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.Response.Listener
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_create.*
import org.json.JSONArray
import org.json.JSONObject
import android.text.TextUtils
import org.json.JSONException


class CreateAccountActivity : AppCompatActivity() {

    //lateinit var userList: ArrayList<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        buttonSignUp.setOnClickListener {
            var checkValid = showError()
            if(checkValid == 0){
                createUser(User(editTextUsername.text.toString(),editTextEmail.text.toString(),editTextPass.text.toString(),editTextBirthYear.text.toString().toInt(),editTextCountry.text.toString()))
            }
           // createUser(User(editTextUsername.text.toString(),editTextEmail.text.toString(),editTextPass.text.toString(),editTextBirthYear.text.toString().toInt(),editTextCountry.text.toString()))

        }

    }


    private fun showError(): Int {
        var errorNum = 0;
        if (editTextEmail.text.toString().isEmpty()) {
            textInputLayoutEmail.error = "Email cannot be empty."
            errorNum++;
        } else if(!isValidEmail(editTextEmail.text.toString())){
            textInputLayoutEmail.error = "Please enter a valid email."
        } else {
            textInputLayoutEmail.error = null
        }

        if (editTextPass.length() < 5) {
            textInputLayoutPass.error = "Password must be at least 6 characters long."
            errorNum++;
        } else {
            textInputLayoutPass.error = null
        }

        if (editTextConfirmPass.text.toString() != editTextPass.text.toString()) {
            textInputLayoutConfirmPass.error = "Password don't match. "
            errorNum++;
        } else {
            textInputLayoutConfirmPass.error = null
        }

        if (editTextUsername.text.toString().isEmpty()) {
            textInputLayoutUsername.error = "Username cannot be empty."
            errorNum++;
        } else if(checkDuplicateUsername(editTextUsername.text.toString())){
            errorNum++;
        } else {
            textInputLayoutUsername.error = null
        }

        if (editTextBirthYear.length() != 4 || editTextBirthYear.text.toString().toInt() > 2020) {
            textInputLayoutBirthYear.error = "Please enter a valid birth year."
            errorNum++;
        } else {
            textInputLayoutBirthYear.error = null
        }

        if (editTextCountry.text.toString().isEmpty()) {
            textInputLayoutCountry.error = "Country cannot be empty."
            errorNum++;
        } else {
            textInputLayoutCountry.error = null
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

    private fun checkDuplicateUsername(username:String): Boolean{
        val loginURL = getString(R.string.url_server) + getString(R.string.url_user_read_one) + "?username=" + username
        var duplicateName = false
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, loginURL, null,
            Response.Listener { response ->
                try {
                    if (response != null) {
                        val strResponse = response.toString()
                        val jsonResponse  = JSONObject(strResponse)

                        if(jsonResponse != null){
                            textInputLayoutUsername.error = "The username already exist. Please try another one."
                            duplicateName = true
                        }
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener {
                if(!duplicateName){
                    textInputLayoutUsername.error = null
                }
            }
        )
        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
        return duplicateName
    }

    private fun createUser(user: User) {
        val url = getString(R.string.url_server) + getString(R.string.url_user_create) + "?username=" + user.username +
                "&email=" + user.email + "&password=" + user.password + "&birthyear=" + user.birthyear + "&country=" + user.country

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener {response ->
                try {
                    if (response != null) {
                        val success: String = response.get("success").toString()

                        if (success.equals("1")) {
                            Toast.makeText(applicationContext, "Record saved", Toast.LENGTH_LONG).show()
                            //Add record to user list
                            //userList.add(user)
                            //Explicit Intent
                            val intent = Intent(this, SignInActivity::class.java)
                            //start the second activity. With no return value
                            startActivity(intent)
                        } else {
                            Toast.makeText(applicationContext,"Record not saved",Toast.LENGTH_LONG
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
        jsonObjectRequest.tag = TAG
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)

    }

    private fun syncContact() {
        val url = getString(R.string.url_server) + getString(R.string.url_user_read)

        //Display progress bar

        //Delete all user records
        //userList.clear()

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Listener { response ->
                // Process the JSON
                try {
                    if (response != null) {
                        val strResponse = response.toString()
                        val jsonResponse = JSONObject(strResponse)
                        val jsonArray: JSONArray = jsonResponse.getJSONArray("records")

                        val size: Int = jsonArray.length()

                        for (i in 0..size - 1) {
                            var jsonUser: JSONObject = jsonArray.getJSONObject(i)
                            var user: User = User(
                                jsonUser.getString("username"),
                                jsonUser.getString("email"),
                                jsonUser.getString("password"),
                                jsonUser.getString("birthyear").toInt(),
                                jsonUser.getString("country")
                            )

                            //userList.add(user)
                        }
                        Toast.makeText(
                            applicationContext,
                            "Record found :" + size,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } catch (e: Exception) {
                    Log.d("Main", "Response: %s".format(e.message.toString()))

                }
            },
            Response.ErrorListener { error ->
                Log.d("Main", "Response: %s".format(error.message.toString()))
            }
        )

        // Access the RequestQueue through your singleton class.
        jsonObjectRequest.tag = TAG
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    companion object {
        const val TAG = "com.example.mindsettle"
    }
}