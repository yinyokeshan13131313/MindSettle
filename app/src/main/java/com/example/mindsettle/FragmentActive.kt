package com.example.mindsettle


import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.dialog_complete_goal.view.*
import org.json.JSONObject
import java.net.URL

/**
 * A simple [Fragment] subclass.
 */
class FragmentActive : Fragment() {

    var dataList = ArrayList<Goal>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_fragment_active, container, false)

        fetchJsonData().execute()

        return view
    }

    inner class fetchJsonData() : AsyncTask<String, Void, String>(){


        override fun onPreExecute(){
            super.onPreExecute()

        }
        override fun doInBackground(vararg params: String?): String? {
            val activity = activity as Main2BottomActivity
            var token = activity.getSharedPreferences("username", Context.MODE_PRIVATE)
            var username = token.getString("loginusername", "")
            val readURL = getString(R.string.url_server) + getString(R.string.url_user_search_goal) + "?username=" + username + "&status=active"
            return URL(readURL).readText(Charsets.UTF_8)
        }
        override fun onPostExecute(result: String?){
            super.onPostExecute(result)
            if(result!="") {

                val jsonObj = JSONObject(result)
                val recordsArr = jsonObj.getJSONArray("records")
                for (i in 0 until recordsArr.length()) {
                    val singleRecord = recordsArr.getJSONObject(i)
                    val goal = Goal()
                    goal.usergoal = singleRecord.getString("usergoal")
                    goal.goaldate = singleRecord.getString("goaldate")

                    dataList.add(goal)
                }
                val activity = activity as Main2BottomActivity
                val listView: ListView = view!!.findViewById(R.id.listView)
                val adapter = GoalAdapter(activity, dataList)
                listView.adapter = adapter

                listView.setOnItemClickListener { parent, view, position, id ->
                    //val element = adapter.getItem(position)
                    val usergoal = adapter.getItemGoal(position)
                    val goaldate = adapter.getItemDate(position)


                    //Toast.makeText(activity,position,Toast.LENGTH_SHORT).show()
                    updateGoal(usergoal.toString(), goaldate.toString())
                    //updateGoal((Goal)element)

                }
            }
        }
    }

    private fun updateGoal(usergoal: String, goaldate: String) {
        val activity = activity as Main2BottomActivity
        var token = activity.getSharedPreferences("username", Context.MODE_PRIVATE)
        var username = token.getString("loginusername", "")
        val url = getString(R.string.url_server) + getString(R.string.url_user_update_goal) + "?status=complete&username=" + username +
                "&usergoal=" + usergoal + "&goaldate=" + goaldate

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                try {
                    if (response != null) {
                        val success: String = response.get("success").toString()

                        if (success.equals("1")) {

                            val mBuilder1 = AlertDialog.Builder(activity)
                            mBuilder1.setTitle("Complete Goal")
                            mBuilder1.setMessage("Are you sure you want to complete this goal?")
                            mBuilder1.setPositiveButton("COMPLETE"){dialog, which ->
                                val mDialogView = LayoutInflater.from(activity).inflate(R.layout.dialog_complete_goal, null)
                                val mBuilder = AlertDialog.Builder(activity).setView(mDialogView)
                                val mAlertDialog = mBuilder.show()
                                mDialogView.dialogCompleteGoal.setOnClickListener{
                                    val intent = Intent(activity, Main2BottomActivity::class.java)
                                    activity.startActivity(intent)
                                }
                            }
                            mBuilder1.setNegativeButton("CANCEL"){dialog, which ->
                                dialog.dismiss()
                            }
                            val mAlertDialog1 = mBuilder1.show()





                        } else {
                            Toast.makeText(activity,"Record not saved",Toast.LENGTH_LONG
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
