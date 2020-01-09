package com.example.mindsettle

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_gratitude.*
import java.time.LocalDateTime

class GratitudeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gratitude)

        editTextThree.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(editTextOne.text.toString().isNotEmpty() && editTextTwo.text.toString().isNotEmpty() && editTextThree.text.toString().isNotEmpty())
                    buttonSaveGratitude.isEnabled = true
            }
        })

        buttonSaveGratitude.setOnClickListener {
            createGratitude(editTextOne.text.toString(), editTextTwo.text.toString(),editTextThree.text.toString() )
        }

        buttonGratitudeHistory.setOnClickListener {
            val intent = Intent(this, GratitudeHistoryActivity::class.java)
            startActivity(intent)
        }
    }

    private fun createGratitude(gratitudeOne: String, gratitudeTwo: String, gratitudeThree: String) {
        var token = getSharedPreferences("username", Context.MODE_PRIVATE)
        var username = token.getString("loginusername", "")
        val currentTime = LocalDateTime.now()
        val url = getString(R.string.url_server) + getString(R.string.url_user_create_gratitude) + "?username=" + username + "&userGratitudeOne=" + gratitudeOne + "&userGratitudeTwo=" + gratitudeTwo + "&userGratitudeThree=" + gratitudeThree + "&dateTime=" + currentTime

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
                    Log.d("Main", "ResponseGoal: %s".format(e.message.toString()))
                }
            },
            Response.ErrorListener { response ->
                Log.d("Main", "ResponseGoal: %s".format(response.message.toString()))
            }
        )

        // Access the RequestQueue through your singleton class.
        jsonObjectRequest.tag = NewGoalActivity.TAG
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)

    }
}
