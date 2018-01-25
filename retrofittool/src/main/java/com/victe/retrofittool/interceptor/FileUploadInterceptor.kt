package com.victe.exercise.interceptor

import com.victe.exercise.http.FileRequestBody
import com.victe.exercise.http.ProgressResponseListener
import okhttp3.Interceptor
import okhttp3.Response




/**
 * 文件上传
 * Created by 13526 on 2018/1/25.
 */
class FileUploadInterceptor(progressListener: ProgressResponseListener):Interceptor{
    private var progressListener:ProgressResponseListener?=null;
    init {
        this.progressListener = progressListener;
    }
    override fun intercept(chain: Interceptor.Chain?): Response {
        val original = chain!!.request()
        val request = original.newBuilder()
                .method(original.method(), FileRequestBody(original!!.body()!!, progressListener!!))
                .build()
        return chain.proceed(request)
    }

}