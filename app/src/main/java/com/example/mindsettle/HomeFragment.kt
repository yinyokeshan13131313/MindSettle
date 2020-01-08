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


        val radioGroup: RadioGroup = view.findViewById(R.id.radioGroup)
        val textViewMood: TextView = view.findViewById(R.id.textViewMood)
        radioGroup.setOnCheckedChangeListener{group, checkedId ->
            when (view.getId()) {
                R.id.radioButton4->
                    if (R.id.radioButton4 == checkedId) {
                        val imageViewPhoto: ImageView = view.findViewById(R.id.imageViewPhoto)
                        imageViewPhoto.setImageResource(R.drawable.great);
                        textViewMood.text = getString(R.string.feelgreat);
                    }
                R.id.radioButton5->
                    if (R.id.radioButton5 == checkedId) {
                        val imageViewPhoto: ImageView = view.findViewById(R.id.imageViewPhoto)
                        imageViewPhoto.setImageResource(R.drawable.good);
                        textViewMood.text = getString(R.string.feelgood);
                    }

                R.id.radioButton6->
                    if (R.id.radioButton6 == checkedId) {
                        val imageViewPhoto: ImageView = view.findViewById(R.id.imageViewPhoto)
                        imageViewPhoto.setImageResource(R.drawable.ok);
                        textViewMood.text = getString(R.string.feelok);
                    }

                R.id.radioButton9->
                    if(R.id.radioButton9 == checkedId){
                        val imageViewPhoto: ImageView = view.findViewById(R.id.imageViewPhoto)
                        imageViewPhoto.setImageResource(R.drawable.bad);
                        textViewMood.text = getString(R.string.feelbad);
                    }

                R.id.radioButton10->
                    if(R.id.radioButton10 == checkedId){
                        val imageViewPhoto: ImageView = view.findViewById(R.id.imageViewPhoto)
                        imageViewPhoto.setImageResource(R.drawable.awful);
                        textViewMood.text = getString(R.string.feelawful)
                    }
            }
        }
        return view
    }




}
