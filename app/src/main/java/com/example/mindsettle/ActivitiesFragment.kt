package com.example.mindsettle


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_activities.*

/**
 * A simple [Fragment] subclass.
 */
class ActivitiesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_activities, container, false)
        val activity = activity as Main2BottomActivity

        //toolbar
        val toolbar: androidx.appcompat.widget.Toolbar = view.findViewById(R.id.toolbarActivity)
        activity.setSupportActionBar(toolbar)

        val imageViewStep: ImageView = view.findViewById(R.id.imageViewStep)
        imageViewStep.setOnClickListener{
            val intent = Intent(activity, TakeASmallStep::class.java)
            startActivity(intent)
        }

        val TextViewStep: TextView = view.findViewById(R.id.TextViewStep)
        TextViewStep.setOnClickListener{
            val intent = Intent(activity, TakeASmallStep::class.java)
            startActivity(intent)
        }

        val imageViewGratitude: ImageView = view.findViewById(R.id.imageViewGratitude)
        imageViewGratitude.setOnClickListener{
            val intent = Intent(activity, GratitudeActivity::class.java)
            startActivity(intent)
        }

        val TextViewGratitude: TextView = view.findViewById(R.id.TextViewGratitude)
        TextViewGratitude.setOnClickListener{
            val intent = Intent(activity, GratitudeActivity::class.java)
            startActivity(intent)
        }

        return view
    }


}
