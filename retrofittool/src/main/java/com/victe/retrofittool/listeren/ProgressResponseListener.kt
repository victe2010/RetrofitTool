package com.victe.exercise.http

/**
 * Created by 13526 on 2018/1/25.
 */
interface ProgressResponseListener{
    fun onResponseProgress(bytesRead: Long, contentLength: Long, done: Boolean)
}