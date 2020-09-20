package com.thinkfreely.fortune

import android.appwidget.AppWidgetManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class ConfigActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config)
        setResult(RESULT_CANCELED)
        val donebutton = findViewById(R.id.doneButton) as Button
        val clicklistener = View.OnClickListener { view ->
            var appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
            val intent = getIntent()
            val extras = intent.getExtras()
            if (extras != null) {
                appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID)
            }
            if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID)
                finish()
            val resultValue = Intent()
            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            setResult(RESULT_OK)
            finish()
        }

        donebutton.setOnClickListener(clicklistener)
    }
}