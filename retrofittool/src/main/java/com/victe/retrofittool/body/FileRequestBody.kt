package com.victe.exercise.http

import okhttp3.MediaType
import okhttp3.RequestBody
import okio.*
import java.io.IOException


/**
 * Created by 13526 on 2018/1/25.
 */
class FileRequestBody(requestBody:RequestBody,progressListener:ProgressResponseListener): RequestBody() {
    //实际的待包装请求体
    private var requestBody: RequestBody? = null
    //进度回调接口
    private var progressListener: ProgressResponseListener? = null
    //包装完成的BufferedSink
    private var bufferedSink: BufferedSink? = null
    init {
        this.requestBody = requestBody
        this.progressListener = progressListener;
    }

    /**
     * 重写调用实际的响应体的contentType
     */
    override fun contentType(): MediaType? {
        return requestBody!!.contentType();
    }

    /**
     * 重写调用实际的响应体的contentLength
     */
    override fun contentLength(): Long {
        return requestBody!!.contentLength();
    }

    override fun writeTo(sink: BufferedSink?) {
        if (bufferedSink == null) {
//            //包装
            bufferedSink = Okio.buffer(sink(sink!!));

        }
        //写入
        requestBody!!.writeTo(bufferedSink!!);
        //必须调用flush，否则最后一部分数据可能不会被写入
        bufferedSink!!.flush();

    }

    /**
     * 写入，回调进度接口
     * @param sink Sink
     * @return Sink
     */
    private fun sink(sink: Sink): Sink {
        return object : ForwardingSink(sink) {
            //当前写入字节数
            internal var bytesWritten = 0L
            //总字节长度，避免多次调用contentLength()方法
            internal var contentLength = 0L

            @Throws(IOException::class)
            override fun write(source: Buffer, byteCount: Long) {
                super.write(source, byteCount)
                if (contentLength == 0L) {
                    //获得contentLength的值，后续不再调用
                    contentLength = contentLength()
                }
                //增加当前写入的字节数
                bytesWritten += byteCount
                //回调
                if (progressListener != null) {
                    progressListener!!.onResponseProgress(bytesWritten, contentLength, bytesWritten == contentLength)
                }
            }
        }
    }

}