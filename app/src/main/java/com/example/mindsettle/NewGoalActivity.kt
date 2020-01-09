package com.example.mindsettle

import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_new_goal.*
import kotlinx.android.synthetic.main.dialog_learn_more_goal.view.*
import kotlinx.android.synthetic.main.dialog_learn_more_schedule.view.*
import org.json.JSONArray
import org.json.JSONObject
import java.util.*
import com.android.volley.Response.Listener
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class NewGoalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_goal)

        //when in show this one
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_goal, null)
        val mBuilder = AlertDialog.Builder(this).setView(mDialogView)
        val mAlertDialog = mBuilder.show()
        mDialogView.dialogBtnGoal.setOnClickListener{
            mAlertDialog.dismiss()
        }


        //calendar
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        //val date = ""
        //val formatter = DateTimeFormatter.ofPattern("dd-mm-yyyy", Locale.ENGLISH)


        //val string = "July 25, 2017"
        //val formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH)
        //val date = LocalDate.parse(string, formatter)

        buttonLearnGoal.setOnClickListener {
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_learn_more_goal, null)
            val mBuilder = AlertDialog.Builder(this).setView(mDialogView)
            val mAlertDialog = mBuilder.show()
            mDialogView.dialogBtnGoal.setOnClickListener{
                mAlertDialog.dismiss()
            }
        }

        buttonLearnDate.setOnClickListener {
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_learn_more_schedule, null)
            val mBuilder = AlertDialog.Builder(this).setView(mDialogView)
            val mAlertDialog = mBuilder.show()
            mDialogView.dialogBtnSchedule.setOnClickListener{
                mAlertDialog.dismiss()
            }
        }

        textViewSelectDate.setOnClickListener {
            //val now = Calendar.getInstance()
            val dpd = DatePickerDialog(this,R.style.DialogTheme, DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                val selectedDate = Calendar.getInstance()
                //selectedDate.set(Calendar.YEAR,mYear)
                //selectedDate.set(Calendar.MONTH,mMonth+1)
                //selectedDate.set(Calendar.DAY_OF_MONTH,mDay)
                //val date = dateFormat.format(selectedDate.time)
                //datePass = dateFormat.parse(date)
                textViewSelectDate.setText("" + mYear + "-" + (mMonth+1) + "-" + mDay)
                //var dateDialog = DatePickerDialog()
                //val date = mDay + "-" + (mMonth+1) + "-" + mYear
            }, year, month, day)
            dpd.datePicker.minDate = System.currentTimeMillis() -1000
            //show dialog
            dpd.show()
        }

        textViewSelectDate.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(editTextNewGoal.text.toString().isNotEmpty() && (!textViewSelectDate.text.toString().equals("Select Date")))
                    buttonSubmit.isEnabled = true
            }
        })

        buttonSubmit.setOnClickListener {
            var checkValid = showError()
            if(checkValid == 0){
                createGoal(editTextNewGoal.text.toString(), textViewSelectDate.text.toString())
            }
        }
    }

    private fun showError(): Int {
        var errorNum = 0;
        if (editTextNewGoal.text.toString().isEmpty()) {
            editTextNewGoal.error = "Please declare a new goal."
            errorNum++;
        } else {
                editTextNewGoal.error = null
        }

        if (textViewSelectDate.text.toString().equals("Select Date")) {
            textViewSelectDate.error = "Please select a date"
            errorNum++;
        } else {
            textViewSelectDate.error = null
        }
        return errorNum;
    }



    private fun createGoal(usergoal: String, goaldate: String) {
        var token = getSharedPreferences("username", Context.MODE_PRIVATE)
        var username = token.getString("loginusername", "")
        val url = getString(R.string.url_server) + getString(R.string.url_user_create_goal) + "?username=" + username + "&usergoal=" + usergoal + "&goaldate=" + goaldate

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
