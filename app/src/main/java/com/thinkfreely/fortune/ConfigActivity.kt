package com.thinkfreely.fortune

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.ComponentName
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
        val updatetime = findViewById(R.id.editTextNumber) as EditText
        updatetime.setText("0")
        setResult(RESULT_CANCELED)
        val donebutton = findViewById(R.id.doneButton) as Button
        val clicklistener = View.OnClickListener { view ->
            val updatetimetext = findViewById(R.id.editTextNumber) as EditText
            var updatems = updatetimetext.getText().toString().toInt()
            if (updatems == 0) {
                FortuneWidget.updateTimer(0)
            }
            if (updatems <= 60 && updatems != 0) {
                updatems = 60
                FortuneWidget.updateTimer((updatems * 1000))
            }
            setResult(RESULT_OK)
            finish()
        }

        donebutton.setOnClickListener(clicklistener)
    }
}