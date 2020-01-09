package com.example.mindsettle

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_sign_in.*
import org.json.JSONException
import org.json.JSONObject

class SignInActivity: AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        buttonSignHome.setOnClickListener {
            JSONProccess1(editTextUsername.text.toString(), editTextPass.text.toString())
        }

    }

    private fun JSONProccess1(username:String,password:String) {
        var token = getSharedPreferences("username", Context.MODE_PRIVATE)
        val loginURL = getString(R.string.url_server) + getString(R.string.url_user_read_one_sign) + "?username=" + username + "&password=" + password

        // output = (TextView) findViewById(R.id.jsonData);
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, loginURL, null,
            Response.Listener { response ->
                try {
                    if (response != null) {
                        val strResponse = response.toString()
                        val jsonResponse  = JSONObject(strResponse)

                        if(jsonResponse != null){
                            Toast.makeText(this, "Log in successful", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, Main2BottomActivity::class.java)
                            intent.putExtra("username", username)
                            finish()

                            var editor = token.edit()
                            editor.putString("loginusername", username)
                            editor.commit()

                            startActivity(intent)
                        }
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { e ->
                Log.e("Volley", "Error" + e.message.toString())
                Toast.makeText(this, "Username or password wrong", Toast.LENGTH_SHORT).show()
            }
        )
        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }
}