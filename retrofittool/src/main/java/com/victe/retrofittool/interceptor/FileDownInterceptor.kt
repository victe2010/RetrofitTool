package com.victe.exercise.interceptor

import com.victe.exercise.http.FileResponseBody
import com.victe.exercise.http.ProgressResponseListener
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by 13526 on 2018/1/25.
 * 文件下载拦截器
 */
class FileDownInterceptor(progressListener: ProgressResponseListener):Interceptor{
    private var progressListener:ProgressResponseListener?=null;
    init {
        this.progressListener = progressListener;
    }
    override fun intercept(chain: Interceptor.Chain?): Response {
        //拦截
        val originalResponse = chain!!.proceed(chain.request())
        //包装响应体并返回
        return originalResponse.newBuilder()
                .body(FileResponseBody(originalResponse.body()!!, progressListener!!))
                .build()
    }

}