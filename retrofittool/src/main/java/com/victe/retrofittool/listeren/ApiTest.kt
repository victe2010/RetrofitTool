package com.victe.exercise.listeren
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by 13526 on 2018/1/23.
 */
interface ApiTest {

    @GET
    fun <T> reuqestTest(@Url url:String,@QueryMap map:HashMap<String, String>): Call<T>

    //map提交
    @POST
    @FormUrlEncoded
    fun <String> request(@Url url:String,@FieldMap args: HashMap<String, String>): Call<String>

    //json格式传输
    @Headers("Content-Type:application/json","Accept: application/json")
    @POST
    fun <T> requestJson(@Url url:String,@Body args: HashMap<String, String>): Call<T>

    //下载文件
    @GET
    fun downFile(@Url url:String):Call<ResponseBody>

     //上传文件
    @Multipart
    @POST
    fun uploadFile(@Url url:String,@Part file:MultipartBody.Part):Call<String>


}