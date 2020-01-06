package com.example.mindsettle

import android.content.Context
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
import kotlinx.android.synthetic.main.activity_test.*
import org.json.JSONException

class TestActivity: AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        var token = getSharedPreferences("username", Context.MODE_PRIVATE)
        tvSession.text = "Sharepreferences " + token.getString("loginusername", "")
        var username = token.getString("loginusername", "")


        buttonDelete.setOnClickListener {
            deleteUser(username.toString())
        }


    }




    private fun deleteUser(username:String) {
        val url = getString(R.string.url_server) + getString(R.string.url_user_delete) + "?username=" + username

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                try {
                    if (response != null) {
                        val success: String = response.get("success").toString()

                        if (success.equals("1")) {
                            Toast.makeText(applicationContext, "Account deleted", Toast.LENGTH_LONG).show()
                            var editor = getSharedPreferences("username", Context.MODE_PRIVATE).edit()
                            editor.putString("loginusername", "")
                            editor.commit()

                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(applicationContext,"Account no delete", Toast.LENGTH_LONG
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

    private fun syncContact() {
        val url = getString(R.string.url_server) + getString(R.string.url_user_read)

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
              //  jsonObjectRequest.tag = CreateAccountActivity.TAG
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }
}