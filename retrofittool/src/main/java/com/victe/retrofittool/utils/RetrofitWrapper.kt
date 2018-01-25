package com.victe.exercise.http

import com.victe.exercise.interceptor.AddCookiesInterceptor
import com.victe.exercise.interceptor.FileDownInterceptor
import com.victe.exercise.interceptor.FileUploadInterceptor
import com.victe.exercise.interceptor.SaveCookiesInterceptor
import com.victe.retrofittool.utils.RetrofitTools
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


/**
 * Created by 13526 on 2018/1/24.
 */
class RetrofitWrapper {
    //    private val host = "http://119.28.187.117/";
    private var retrofitBuilder: Retrofit.Builder? = null
    private var okHttpBuild:OkHttpClient.Builder?=null;
    init {
        retrofitBuilder = Retrofit.Builder();
        retrofitBuilder!!.baseUrl(RetrofitTools.getHost());
        retrofitBuilder!!.addConverterFactory(ScalarsConverterFactory.create())
        retrofitBuilder!!.addConverterFactory(GsonConverterFactory.create())
        okHttpBuild = OkHttpClient().newBuilder();
    }

    companion object {
        private var INSTANCE: RetrofitWrapper? = null
        fun getInstance(): RetrofitWrapper? {
            if (INSTANCE == null) {
                synchronized(RetrofitWrapper::class.java) {
                    INSTANCE = RetrofitWrapper();
                }
            }
            return INSTANCE;
        }
    }

    /**
     * 泛型-需要cookie
     */
    fun <T> createCookie(service: Class<T>): T {
        retrofitBuilder!!.client(okHttpBuild!!.addInterceptor(SaveCookiesInterceptor()).addInterceptor(AddCookiesInterceptor()).build())
        return retrofitBuilder!!.build().create(service)
    }

    /**
     * 泛型-不需要头文件
     */
    fun <T> create(service: Class<T>): T {
        return retrofitBuilder!!.build()!!.create(service)
    }

    /**
     * 创建带响应进度(下载进度)回调的service
     * ProgressResponseListener下载回调接口
     */
    fun <T> createResponseService(tClass: Class<T>, listener: ProgressResponseListener): T {
        return retrofitBuilder!!
                .client(OkHttpClient().newBuilder().addInterceptor(FileDownInterceptor(listener)).build())
                .build()
                .create(tClass)
    }

    /**
     * 上传文件带响应进度条
     * ProgressResponseListener上传回调接口
     */
    fun <T> createRequestService(tClass: Class<T>, listener: ProgressResponseListener): T {
        return retrofitBuilder!!
                .client(OkHttpClient().newBuilder().addInterceptor(FileUploadInterceptor(listener)).build())
                .build()
                .create(tClass)
    }

}