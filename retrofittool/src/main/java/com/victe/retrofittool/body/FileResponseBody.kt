package com.victe.exercise.http

import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.*
import java.io.IOException


/**
 * Created by 13526 on 2018/1/25.
 */
class FileResponseBody(responseBody: ResponseBody, progressListener: ProgressResponseListener) : ResponseBody() {
    //实际的待包装响应体
    private var responseBody: ResponseBody? = null
    //进度回调接口
    private var progressListener: ProgressResponseListener? = null
    //包装完成的BufferedSource
    private var bufferedSource: BufferedSource? = null

    init {
        this.responseBody = responseBody;
        this.progressListener = progressListener;
    }

    override fun contentLength(): Long {
        return responseBody!!.contentLength();
    }

    override fun contentType(): MediaType? {
        return responseBody!!.contentType();
    }

    override fun source(): BufferedSource {
        if (bufferedSource == null) {
            //包装
            bufferedSource = Okio.buffer(source(responseBody!!.source()));
        }
        return bufferedSource!!;
    }

    /**
     * 读取，回调进度接口
     *
     * @param source Source
     * @return Source
     */
    private fun source(source: Source): Source {

        return object : ForwardingSource(source) {
            //当前读取字节数
            internal var totalBytesRead = 0L

            @Throws(IOException::class)
            override fun read(sink: Buffer, byteCount: Long): Long {
                val bytesRead = super.read(sink, byteCount)
                //增加当前读取的字节数，如果读取完成了bytesRead会返回-1
                totalBytesRead += if (bytesRead.toInt() != -1) bytesRead else 0
                //回调，如果contentLength()不知道长度，会返回-1
                if (progressListener != null) {
                    progressListener!!.onResponseProgress(totalBytesRead, responseBody!!.contentLength(), bytesRead.toInt() == -1)
                }
                return bytesRead
            }
        }
    }

}