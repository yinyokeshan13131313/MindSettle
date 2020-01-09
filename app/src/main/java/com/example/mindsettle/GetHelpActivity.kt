package com.example.mindsettle

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_get_help.*
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T



class GetHelpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_help)
        textViewCanadaSMSNumber.setOnClickListener{
            val uri = Uri.parse("smsto:686868")
            val intent = Intent(Intent.ACTION_SENDTO, uri)
            intent.putExtra("sms_body", "HELLO")
            startActivity(intent)
        }
        textViewCanadaPhoneNumber.setOnClickListener{
            val intent = Intent(Intent.ACTION_DIAL);
            intent.data = Uri.parse("tel:18334564566")
            startActivity(intent)
        }
        textViewUSSMSNumber.setOnClickListener{
            val uri = Uri.parse("smsto:741741")
            val intent = Intent(Intent.ACTION_SENDTO, uri)
            intent.putExtra("sms_body", "HELLO")
            startActivity(intent)
        }
        textViewUSPhoneNumber.setOnClickListener{
            val intent = Intent(Intent.ACTION_DIAL);
            intent.data = Uri.parse("tel:18002738255")
            startActivity(intent)
        }
    }
}
