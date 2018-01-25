package com.victe.exercise.interceptor

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response
import android.R.attr.host
import android.text.TextUtils
import android.R.id.edit
import android.content.SharedPreferences
import android.util.Log
import android.webkit.CookieManager
import com.victe.exercise.http.CookUtils
import com.victe.exercise.http.FileResponseBody


/**
 * Created by 13526 on 2018/1/24.
 */
class SaveCookiesInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain?): Response {
        val request = chain!!.request()
        val response = chain!!.proceed(request)
        //set-cookie可能为多个
        if (!response.headers("set-cookie").isEmpty()) {
            val cookies = response.headers("set-cookie")
            val cookie = encodeCookie(cookies)
            CookUtils.getInsatnce()!!.setCookie(cookie);
        }
        return response
    }
    //整合cookie为唯一字符串
    private fun encodeCookie(cookies: List<String>): String {
        val sb = StringBuilder()
        val set = HashSet<String>()
        for (cookie in cookies) {
            val arr = cookie.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            for (s in arr) {
                if (set.contains(s)) continue
                set.add(s)

            }
        }

        val ite = set.iterator()
        while (ite.hasNext()) {
            val cookie = ite.next()
            sb.append(cookie).append(";")
        }

        val last = sb.lastIndexOf(";")
        if (sb.length - 1 == last) {
            sb.deleteCharAt(last)
        }

        return sb.toString()
    }

}