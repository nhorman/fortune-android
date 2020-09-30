package com.thinkfreely.fortune

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.Service
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetManager.ACTION_APPWIDGET_UPDATE
import android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_IDS
import android.appwidget.AppWidgetProvider
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Handler
import android.os.SystemClock
import android.util.Log
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.RemoteViews
import android.widget.TextView
import androidx.core.view.updateLayoutParams
import java.util.*


/**
 * Implementation of App Widget functionality.
 */
class FortuneWidget : AppWidgetProvider() {
    companion object {
        var updateMills : Int = 0
        fun updateTimer(periodms: Int) {
            updateMills = periodms
        }
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {

        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, appWidgetIds)
        }
        if (updateMills != 0) {
            val timerintent = Intent(context, FortuneWidget::class.java)
            timerintent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE)
            timerintent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds)

            val pendingtimerintent =
                PendingIntent.getBroadcast(context, 0, timerintent, PendingIntent.FLAG_CANCEL_CURRENT)

            val alarm: AlarmManager =
                context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarm.cancel(pendingtimerintent)
            alarm.setRepeating(
                AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime(),
                updateMills.toLong(),
                pendingtimerintent
            )
        }
    }


    override fun onEnabled(context: Context) {

    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
    }


    protected fun updateAppWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int,
        ids: IntArray
    ) {
        // Construct the RemoteViews object
        val views = RemoteViews(context.packageName, R.layout.fortune_widget)
        val fortuneMaker = FortuneStrings(context)
        val fortune = fortuneMaker.getFortune()
        val intent = Intent(context, FortuneWidget::class.java)
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE)
        intent.putExtra(EXTRA_APPWIDGET_IDS, ids)
        val pendingintent =
            PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        views.setOnClickPendingIntent(R.id.button, pendingintent)
        views.setTextViewText(R.id.fortune_text, fortune.fortune)
        views.setTextViewTextSize(R.id.fortune_text, TypedValue.COMPLEX_UNIT_SP, 10f)
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }
}