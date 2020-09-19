package com.thinkfreely.fortune

import android.content.Context
import java.io.BufferedReader
import java.io.File
import java.util.concurrent.ThreadLocalRandom
import kotlin.random.Random

data class FortIdx(val start: Int, val end: Int)

class FortuneStrings(private val mycontext : Context) {

    private val assetlist = arrayOf("art.txt")

    private fun selectAsset() : String {
        val rnd = Random.nextInt(assetlist.size)
        return assetlist[rnd]
    }

    private fun getFortuneCount(s: String): Int {
        return s.filter {it == '%'}.count()
    }

    private fun getFortuneIdx(s: String, idx: Int): FortIdx {
        var tmpend = 0
        var tmpstart = 0
        var idxcount = 0

        while(idxcount != idx) {
            tmpstart = tmpend
            tmpend = s.indexOf('%', tmpend+1)
            idxcount++
        }
        return FortIdx(tmpstart, tmpend)
    }

    fun getFortune() : String {
        val asset = selectAsset()
        val mngr = mycontext.getAssets()
        val fortunes = mngr.open(asset)
        val reader = BufferedReader(fortunes.reader())
        val content = StringBuilder()
        try {
            var line = reader.readLine()
            while (line != null) {
                content.append(line)
                line = reader.readLine()
            }
        }
        finally {
            reader.close()
        }

        val contentstring = content.toString()
        val fortcount = getFortuneCount(contentstring)
        val fortuneid = Random.nextInt(fortcount)
        val idxs = getFortuneIdx(contentstring, fortuneid)
        return content.substring(idxs.start+1, idxs.end)
    }
}