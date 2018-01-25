package com.victe.exercise.interceptor

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response
import android.text.TextUtils
import android.util.Log
import android.webkit.CookieManager
import com.victe.exercise.http.CookUtils


/**
 * Created by 13526 on 2018/1/24.
 */
class AddCookiesInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain?): Response {
        val request = chain!!.request()
        val builder = request.newBuilder()
        val cookie =  CookUtils.getInsatnce()!!.getCookie()
        if (!TextUtils.isEmpty(cookie)) {
            builder.addHeader("Cookie", cookie)
        }
        return chain!!.proceed(builder.build())
    }

}