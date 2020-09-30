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
import android.widget.*
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.thinkfreely.fortune.FortuneWidget
import kotlinx.android.synthetic.main.activity_config.view.*

class ConfigActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config)
        val updatetime = findViewById(R.id.editTextNumber) as EditText
        updatetime.setText("0")

        val MyAssetList = arrayOf("art.txt", "ascii-art.txt", "computers.txt",
                                    "cookie.txt", "debian.txt", "definitions.txt",
                                    "disclaimer.txt", "drugs.txt", "education.txt",
                                    "ethnic.txt", "food.txt", "fortunes.txt", "goedel.txt",
                                    "humorists.txt", "kids.txt", "knghtbrd.txt", "law.txt",
                                    "linux.txt", "linuxcookie.txt", "literature.txt", "love.txt",
                                    "magic.txt", "medicine.txt", "men-women.txt", "miscellaneous.txt",
                                    "news.txt", "paradoxum.txt", "people.txt", "perl.txt", "pets.txt",
                                    "platitudes.txt", "politics.txt", "pratchett.txt", "riddles.txt",
                                    "science.txt", "songs-poems.txt", "riddles.txt", "sports.txt",
                                    "startrek.txt", "tao.txt", "translate-me.txt", "wisdom.txt",
                                    "work.txt", "zippy.txt")
        FortuneStrings.setAssetlist(MyAssetList)

        // Init adds space
        MobileAds.initialize(this)

        val mAdView = findViewById(R.id.adView) as AdView
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        // Make a checkable list of all fortune categories
        val categories = TextView(applicationContext)
        categories.setText("Fortune Categories to display")
        val categorylayout = findViewById(R.id.fortunelistlayout) as LinearLayout
        categorylayout.addView(categories)
        val fortunelist = FortuneStrings.getAssetlist()
        for (i in 0 until fortunelist.size) {
            val assettext = fortunelist[i]
            val assetcheck = CheckBox(applicationContext)
            assetcheck.setText(assettext)
            assetcheck.setChecked(true)
            val checklistener = View.OnClickListener { view ->
                val assetlist = FortuneStrings.getAssetlist()
                val checkbox = view as CheckBox
                val text = checkbox.getText().toString()
                val assetMList = assetlist.toMutableList()

                if (!checkbox.isChecked) {
                    if (assetlist.size == 1) {
                        val toast = Toast.makeText(applicationContext, "Must leave at least one box checked", Toast.LENGTH_SHORT)
                        toast.show()
                        checkbox.setChecked(true)
                    } else {
                        val idx = assetlist.indexOf(text)
                        assetMList.removeAt(idx)
                        val newassets = assetMList.toTypedArray() as Array<String>
                        FortuneStrings.setAssetlist(newassets)
                    }
                } else {
                    assetMList.add(text)
                    val newassets = assetMList.toTypedArray() as Array<String>
                    FortuneStrings.setAssetlist(newassets)
                }
            }
            assetcheck.setOnClickListener(checklistener)
            categorylayout.addView(assetcheck)
        }

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