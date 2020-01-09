package com.example.mindsettle

import android.content.Context
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_check_in_summary.*
import kotlinx.android.synthetic.main.activity_gratitude_history.*
import org.json.JSONObject
import java.net.URL

class GratitudeHistoryActivity : AppCompatActivity() {

    var dataList = ArrayList<Gratitude>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gratitude_history)
        fetchJsonData().execute()
    }
    inner class fetchJsonData() : AsyncTask<String, Void, String>() {

        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun doInBackground(vararg params: String?): String? {
            var token = getSharedPreferences("username", Context.MODE_PRIVATE)
            var username = token.getString("loginusername", "")
            val readURL = getString(R.string.url_server) + getString(R.string.url_user_search_gratitude) + "?username=" + username
            return URL(readURL).readText(Charsets.UTF_8)
        }
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if(result!=""){
                val jsonObj = JSONObject(result)
                val recordsArr = jsonObj.getJSONArray("records")
                for (i in 0 until recordsArr.length()) {
                    val singleRecord = recordsArr.getJSONObject(i)
                    val gratitude = Gratitude()
                    gratitude.userGratitudeOne = singleRecord.getString("userGratitudeOne")
                    gratitude.userGratitudeTwo = singleRecord.getString("userGratitudeTwo")
                    gratitude.userGratitudeThree = singleRecord.getString("userGratitudeThree")
                    gratitude.dateTime = singleRecord.getString("dateTime")

                    dataList.add(gratitude)
                }

                val adapter = GratitudeAdapter(applicationContext, dataList)
                listViewGratitude.adapter = adapter
            }
        }
    }
}
