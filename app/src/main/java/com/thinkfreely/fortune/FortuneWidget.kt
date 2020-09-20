package com.thinkfreely.fortune

import android.app.AlarmManager
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetManager.ACTION_APPWIDGET_UPDATE
import android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_IDS
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.SystemClock
import android.util.Log
import android.util.TypedValue
import android.widget.RemoteViews
import java.util.*

/**
 * Implementation of App Widget functionality.
 */
class FortuneWidget : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
        val intent = Intent(context, FortuneWidget::class.java)
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE)
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds)
        val pendingintent =
            PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)

        val alarm : AlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarm.cancel(pendingintent)
        alarm.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), 60000, pendingintent)
    }


    override fun onEnabled(context: Context) {

    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    Log.e(TAG, "Got Update")
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.fortune_widget)
    val fortuneMaker = FortuneStrings(context)
    val fortune = fortuneMaker.getFortune()
    views.setTextViewText(R.id.fortune_text, fortune)
    views.setTextViewTextSize(R.id.fortune_text, TypedValue.COMPLEX_UNIT_SP, 10f)
    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}