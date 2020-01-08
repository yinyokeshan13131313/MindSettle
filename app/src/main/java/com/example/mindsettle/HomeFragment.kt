package com.example.mindsettle


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView

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


       
        return view
    }




}
