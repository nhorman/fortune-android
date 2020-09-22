package com.thinkfreely.fortune

import android.content.Context
import java.io.BufferedReader
import java.io.File
import java.util.concurrent.ThreadLocalRandom
import kotlin.random.Random

data class FortIdx(val start: Int, val end: Int)
data class FortInfo(val fortune : String, val art: Boolean)

class FortuneStrings(private val mycontext : Context) {

    private val assetlist = arrayOf("art.txt", "ascii-art.txt", "computers.txt",
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
            if (tmpend == -1) {
                //crap, file has an error, just take the first element
                return getFortuneIdx(s, 1)
            }
            idxcount++
        }
        return FortIdx(tmpstart, tmpend)
    }

    fun getFortune() : FortInfo {
        val asset = selectAsset()
        val mngr = mycontext.getAssets()
        val fortunes = mngr.open(asset)
        val reader = BufferedReader(fortunes.reader())
        val content = StringBuilder()
        try {
            var line = reader.readLine()
            while (line != null) {
                line = line.replace(" ", "\u0020",true)
                content.append(line + "\n")
                line = reader.readLine()
            }
        }
        finally {
            reader.close()
        }
        val contentstring = content.toString()
        val fortcount = getFortuneCount(contentstring)
        val fortuneid = Random.nextInt(fortcount-1) + 1
        val idxs = getFortuneIdx(contentstring, fortuneid)
        if (asset.equals("ascii-art.txt"))
            return FortInfo(content.substring(idxs.start+1, idxs.end), true)
        else
            return FortInfo(content.substring(idxs.start+1, idxs.end), false)
    }
}