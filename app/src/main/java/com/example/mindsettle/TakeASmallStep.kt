package com.example.mindsettle

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Slide
import android.transition.TransitionManager
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.activity_take_asmall_step.*
import kotlinx.android.synthetic.main.another_view.*

class TakeASmallStep : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_take_asmall_step)

        textViewReach.setOnClickListener{
            createView(1);
        }

        textViewGo.setOnClickListener{
            createView(2);
        }

        textViewHave.setOnClickListener{
            createView(3);
        }



    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    public fun createView(option:Int){
        // Initialize a new layout inflater instance
        val inflater: LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        var view = inflater.inflate(R.layout.another_view,null)
        // Inflate a custom view using layout inflater
        if(option==1){
            view = inflater.inflate(R.layout.another_view,null)
        }
        else if(option==2){
            view = inflater.inflate(R.layout.another_view2,null)
        }
        else
        {
            view = inflater.inflate(R.layout.another_view3,null)
        }


        // Initialize a new instance of popup window
        val popupWindow = PopupWindow(
            view, // Custom view to show in popup window
            ConstraintLayout.LayoutParams.WRAP_CONTENT, // Width of popup window
            ConstraintLayout.LayoutParams.WRAP_CONTENT // Window height
        )

        // Set an elevation for the popup window
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popupWindow.elevation = 10.0F
        }


        // If API level 23 or higher then execute the code
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            // Create a new slide animation for popup window enter transition
            val slideIn = Slide()
            slideIn.slideEdge = Gravity.TOP
            popupWindow.enterTransition = slideIn

            // Slide animation for popup window exit transition
            val slideOut = Slide()
            slideOut.slideEdge = Gravity.RIGHT
            popupWindow.exitTransition = slideOut

        }

        // Get the widgets reference from custom view
        val tv = view.findViewById<TextView>(R.id.textViewInfo)
        val buttonPopup = view.findViewById<Button>(R.id.button_popup)



        // Set a click listener for popup's button widget
        buttonPopup.setOnClickListener{
            // Dismiss the popup window
            popupWindow.dismiss()
        }




        // Finally, show the popup window on app
        TransitionManager.beginDelayedTransition(root_layout)
        popupWindow.showAtLocation(
            root_layout, // Location to display popup window
            Gravity.CENTER, // Exact position of layout to display popup
            0, // X offset
            0 // Y offset
        )

    }
}
