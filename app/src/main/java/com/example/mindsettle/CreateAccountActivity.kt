package com.example.mindsettle

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

class CreateAccountActivity : AppCompatActivity() {

    //internal lateinit var db: DBHelper
    //internal var lstPersons: List<Person> = ArrayList<Person>()
    lateinit var userList: ArrayList<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        //userList = ArrayList()
        ////db = DBHelper(this)

        //refreshData()

        buttonSignUp.setOnClickListener {
            //showError()
            //val person = Person(
            //    editTextNickname.text.toString(),
            //    editTextEmail.text.toString(),
            //    editTextPass.text.toString(),
            //    Integer.parseInt(editTextBirthYear.text.toString()),
            //    editTextCountry.text.toString()
            //)
            //db.addPerson(person)
            //refreshData()
        }

    }

    //private fun refreshData() {
     //   lstPersons = db.allPerson
     //   val adapter = ListPersonAdapter(
     //       this@CreateAccountActivity,
     //       lstPersons,
     //       editTextNickname,
      //      editTextEmail,
     //       editTextPass,
     //       editTextBirthYear,
   //         editTextCountry
    //    )
      //  list_persons.adapter = adapter
    //}

    private fun showError() {
        if (editTextEmail.text.toString().isEmpty()) {
            textInputLayoutEmail.error = "Please enter a valid email."
        } else {
            textInputLayoutEmail.error = null
        }

        if (editTextPass.length() < 4) {
            textInputLayoutPass.error = "TPassword must be at least 6 characters long."
        } else {
            textInputLayoutPass.error = null
        }

        if (editTextConfirmPass.text.toString() != editTextPass.text.toString()) {
            textInputLayoutConfirmPass.error = "Password don't match. "
        } else {
            textInputLayoutConfirmPass.error = null
        }

        if (editTextUsername.text.toString().isEmpty()) {
            textInputLayoutUsername.error = "Please enter a valid username."
        } else {
            textInputLayoutUsername.error = null
        }

        if (editTextBirthYear.length() != 4) {
            textInputLayoutBirthYear.error = "Please enter a valid birth year."
        } else {
            textInputLayoutBirthYear.error = null
        }

        if (editTextCountry.text.toString().isEmpty()) {
            textInputLayoutCountry.error = "Please enter a valid country."
        } else {
            textInputLayoutCountry.error = null
        }

    }

    private fun createUser(user: User) {
        val url = getString(R.string.url_server) + getString(R.string.url_user_create) + "?name=" + user.username +
                "&email=" + user.email + "&password=" + user.password + "&birthyear=" + user.birthyear + "&country=" + user.country

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener {response ->
                try {
                    if (response != null) {
                        val success: String = response.get("success").toString()

                        if (success.equals("1")) {
                            Toast.makeText(applicationContext, "Record saved", Toast.LENGTH_LONG)
                                .show()
                            //Add record to user list
                            userList.add(user)
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "Record not saved",
                                Toast.LENGTH_LONG
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
        userList.clear()

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

                            userList.add(user)
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