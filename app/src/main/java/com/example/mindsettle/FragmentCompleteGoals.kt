package com.example.mindsettle


import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import org.json.JSONObject
import java.net.URL

/**
 * A simple [Fragment] subclass.
 */
class FragmentCompleteGoals : Fragment() {

    var dataList = ArrayList<Goal>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_fragment_complete_goals, container, false)

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
            val readURL = getString(R.string.url_server) + getString(R.string.url_user_search_goal) + "?username=" + username + "&status=complete"
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
                val listView: ListView = view!!.findViewById(R.id.listViewComplete)
                val adapter = GoalAdapter(activity, dataList)
                listView.adapter = adapter
            }
        }
    }

}
