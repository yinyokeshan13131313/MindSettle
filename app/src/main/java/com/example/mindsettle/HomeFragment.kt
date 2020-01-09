package com.example.mindsettle


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.fragment_home.*
import java.time.LocalDateTime

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val activity = activity as Main2BottomActivity
        var token = activity.getSharedPreferences("username", Context.MODE_PRIVATE)
        var username = token.getString("loginusername", "")
        var textViewName: TextView = view.findViewById(R.id.textViewName)
        textViewName.text = username


        val radioGroupMood: RadioGroup = view.findViewById(R.id.radioGroup)
        val textViewMood: TextView = view.findViewById(R.id.textViewMood)


        radioGroupMood.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener{ group, checkedId ->
            val radio: RadioButton = view.findViewById(checkedId)
            when(radio){
                radioButton4 ->{
                    val imageViewPhoto: ImageView = view.findViewById(R.id.imageViewPhoto)
                    imageViewPhoto.setImageResource(R.drawable.great)
                    textViewMood.text = getString(R.string.feelgreat)

                }
                radioButton5 ->{
                     val imageViewPhoto: ImageView = view.findViewById(R.id.imageViewPhoto)
                     imageViewPhoto.setImageResource(R.drawable.good)
                     textViewMood.text = getString(R.string.feelgood)
                }
                radioButton6 ->{
                    val imageViewPhoto: ImageView = view.findViewById(R.id.imageViewPhoto)
                    imageViewPhoto.setImageResource(R.drawable.ok)
                    textViewMood.text = getString(R.string.feelok)
                }
                radioButton9 ->{
                    val imageViewPhoto: ImageView = view.findViewById(R.id.imageViewPhoto)
                    imageViewPhoto.setImageResource(R.drawable.bad)
                    textViewMood.text = getString(R.string.feelbad)
                }
                radioButton10 ->{
                    val imageViewPhoto: ImageView = view.findViewById(R.id.imageViewPhoto)
                    imageViewPhoto.setImageResource(R.drawable.awful)
                    textViewMood.text = getString(R.string.feelawful)
                }
            }
        })


        val buttonSubmit: Button = view.findViewById(R.id.buttonSubmitMood)
        val editTextComment:EditText = view.findViewById(R.id.editTextComment)
        buttonSubmit.setOnClickListener {
            var checkValid = showError(editTextComment)
            if(checkValid){
                val checkedRadio: RadioButton = view.findViewById(radioGroupMood.checkedRadioButtonId)
                var userMood = checkedRadio.text.toString()
                createMood(userMood, editTextComment.text.toString())
            }
        }

        val buttonViewSummary : Button = view.findViewById(R.id.buttonViewSummary)
        buttonViewSummary.setOnClickListener {
            val activity = activity as Main2BottomActivity
            val intent = Intent(activity, CheckInSummaryActivity::class.java)
            activity.startActivity(intent)
        }

        return view
    }

    private fun showError(editTextComment:EditText): Boolean{
        if (editTextComment.text.toString().isEmpty()) {
            editTextComment.error = "Comment cannot be empty."
            return false
        } else {
            return true
        }
    }
    private fun createMood(userMood: String, comment: String) {

        val activity = activity as Main2BottomActivity
        var token = activity.getSharedPreferences("username", Context.MODE_PRIVATE)
        var username = token.getString("loginusername", "")
        val currentTime = LocalDateTime.now()
        val url = getString(R.string.url_server) + getString(R.string.url_user_create_mood) + "?username=" + username + "&userMood=" + userMood + "&dateTime=" + currentTime + "&comment=" + comment
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                try {
                    if (response != null) {
                        val success: String = response.get("success").toString()

                        if (success.equals("1")) {
                            Toast.makeText(context, "Record saved", Toast.LENGTH_LONG).show()
                            val intent = Intent(activity, Main2BottomActivity::class.java)
                            activity.startActivity(intent)
                        } else {
                            Toast.makeText(context,"Record not saved", Toast.LENGTH_LONG
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
        //jsonObjectRequest.tag = TAG
        MySingleton.getInstance(activity).addToRequestQueue(jsonObjectRequest)
    }





}
