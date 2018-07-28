package com.victe.exercise.http

import android.os.Environment
import com.google.common.io.Flushables.flush
import java.io.File.separator
import okhttp3.ResponseBody
import java.io.*


/**
 * 文件下载
 * Created by 13526 on 2018/1/24.
 */
class FileDownLoad {
    /**
     * 请求body 适合小文件下载
     * path存储地址
     */
     fun writeResponseBodyToDisk(body: ResponseBody, path: String): Boolean {
        try {
            val futureStudioIconFile = File(path)
            if (!futureStudioIconFile.parentFile.exists()){
                futureStudioIconFile.parentFile.mkdirs();
            }
            if (futureStudioIconFile.exists()){
                futureStudioIconFile.delete()
            }
            if (!futureStudioIconFile.exists()){
                futureStudioIconFile.createNewFile();
            }
            var inputStream: InputStream? = null
            var outputStream: OutputStream? = null
            try {
                val fileReader = ByteArray(4096)
//                val fileSize = body.contentLength()
                var fileSizeDownloaded: Long = 0
                inputStream = body.byteStream()
                outputStream = FileOutputStream(futureStudioIconFile)
                while (true) {
                    val read = inputStream!!.read(fileReader)
                    if (read == -1) {
                        break
                    }
                    outputStream.write(fileReader, 0, read)
                    fileSizeDownloaded += read.toLong()
                }
                outputStream.flush()
                return true
            } catch (e: IOException) {
                return false
            } finally {
                if (inputStream != null) {
                    inputStream.close()
                }
                if (outputStream != null) {
                    outputStream.close()
                }
            }
        } catch (e: IOException) {
            return false
        }

    }



}