package com.example.mindsettle

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ProfileFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_profile, container, false)
        val activity = activity as Main2BottomActivity
        val btn: Button = view.findViewById(R.id.buttonEdit)
        btn.setOnClickListener {
            val intent = Intent(activity, EditProfileActivity::class.java)
            activity.startActivity(intent)
        }

        val btnSignOut: Button =  view.findViewById(R.id.buttonSignOut)
        btnSignOut.setOnClickListener {
            val mBuilder = AlertDialog.Builder(activity)
            mBuilder.setTitle("Sign Out")
            mBuilder.setMessage("Are you sure you want to sign out?")
            mBuilder.setPositiveButton("SIGN OUT"){dialog, which ->
                var token = activity.getSharedPreferences("username", Context.MODE_PRIVATE)
                var editor = token.edit()
                editor.putString("loginusername", "")
                editor.commit()

                val intent = Intent(activity, MainActivity::class.java)
                activity.startActivity(intent)
                activity.finish()
            }
            mBuilder.setNegativeButton("CANCEL"){dialog, which ->
                dialog.dismiss()
            }
            val mAlertDialog = mBuilder.show()

        }

        val btnDeleteAccount: Button = view.findViewById(R.id.buttonDeleteProfile)
        btnDeleteAccount.setOnClickListener {
            val mBuilder = AlertDialog.Builder(activity)
            mBuilder.setTitle("Delete Account")
            mBuilder.setMessage("Your account and all your saved data will be deleted. This action cannot be undone. Are you sure?")
            mBuilder.setPositiveButton("DELETE"){dialog, which ->
                deleteProfile()
            }
            mBuilder.setNegativeButton("CANCEL"){dialog, which ->
                dialog.dismiss()
            }
            val mAlertDialog = mBuilder.show()
        }
        return view
    }

    private fun deleteProfile(){
        val activity = activity as Main2BottomActivity
        var token = activity.getSharedPreferences("username", Context.MODE_PRIVATE)
        var username = token.getString("loginusername", "")
        val url = getString(R.string.url_server) + getString(R.string.url_user_delete) + "?username=" + username

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                try {
                    if (response != null) {
                        val success: String = response.get("success").toString()

                        if (success.equals("1")) {
                            deleteGoal()
                        } else {
                            Toast.makeText(activity,"Account no delete", Toast.LENGTH_LONG
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
        MySingleton.getInstance(activity).addToRequestQueue(jsonObjectRequest)
    }

    private fun deleteGoal(){
        val activity = activity as Main2BottomActivity
        var token = activity.getSharedPreferences("username", Context.MODE_PRIVATE)
        var username = token.getString("loginusername", "")
        val url = getString(R.string.url_server) + getString(R.string.url_user_delete_goal) + "?username=" + username

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                try {
                    if (response != null) {
                        val success: String = response.get("success").toString()

                        if (success.equals("1")) {
                            deleteGratitude()
                        } else {
                            Toast.makeText(activity,"Goal no delete", Toast.LENGTH_LONG
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
        MySingleton.getInstance(activity).addToRequestQueue(jsonObjectRequest)

    }

    private fun deleteGratitude(){
        val activity = activity as Main2BottomActivity
        var token = activity.getSharedPreferences("username", Context.MODE_PRIVATE)
        var username = token.getString("loginusername", "")
        val url = getString(R.string.url_server) + getString(R.string.url_user_delete_gratitude ) + "?username=" + username

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                try {
                    if (response != null) {
                        val success: String = response.get("success").toString()

                        if (success.equals("1")) {
                            deleteMood()
                        } else {
                            Toast.makeText(activity,"Gratitude no delete", Toast.LENGTH_LONG
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
        MySingleton.getInstance(activity).addToRequestQueue(jsonObjectRequest)
    }

    private fun deleteMood(){
        val activity = activity as Main2BottomActivity
        var token = activity.getSharedPreferences("username", Context.MODE_PRIVATE)
        var username = token.getString("loginusername", "")
        val url = getString(R.string.url_server) + getString(R.string.url_user_delete_gratitude ) + "?username=" + username

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                try {
                    if (response != null) {
                        val success: String = response.get("success").toString()

                        if (success.equals("1")) {
                            Toast.makeText(activity, "Account deleted", Toast.LENGTH_LONG).show()
                            var editor = activity.getSharedPreferences("username", Context.MODE_PRIVATE).edit()
                            editor.putString("loginusername", "")
                            editor.commit()

                            val intent = Intent(activity, MainActivity::class.java)
                            startActivity(intent)
                            activity.finish()
                        } else {
                            Toast.makeText(activity,"Account no delete", Toast.LENGTH_LONG
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
        MySingleton.getInstance(activity).addToRequestQueue(jsonObjectRequest)
    }

}
