package com.thinkfreely.fortune

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RemoteViews
import android.widget.TextView
import com.thinkfreely.fortune.FortuneWidget
import kotlinx.android.synthetic.main.activity_config.view.*

class ConfigActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config)
        setResult(RESULT_CANCELED)
        val donebutton = findViewById(R.id.doneButton) as Button
        val clicklistener = View.OnClickListener { view ->
            val updatetimetext = findViewById(R.id.editTextNumber) as EditText
            var updatetime : Long = updatetimetext.getText().toString().toLong()
            if (updatetime <= 60)
                updatetime = 60
            com.thinkfreely.fortune.updateMills = updatetime * 1000
            setResult(RESULT_OK)
            finish()
        }

        donebutton.setOnClickListener(clicklistener)
    }
}