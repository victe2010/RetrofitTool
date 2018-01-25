# retrofit封装工具通信类
### 1、HTPP通信GET POST请求协议
### 2、HTPP获取头文件--cookie
### 3、文件上传
### 4、文件下载
# 使用指南
## 第 1 步 在存储库的 build.gradle 中添加︰
    allprojects {
       		repositories {
       			...
       			maven { url 'https://jitpack.io' }
       		}
    }
       	
## 第 2 步 添加依赖项
	dependencies {
	     compile 'com.github.victe2010:RetrofitTool:1.0'
	}
	
## 第 3 步 建议在Applicaiotn里面初始化，设置host
    RetrofitTools.init("http://www.***.com");

## 使用方法

#### 通过注解设置请求方法可以仿照ApiTest接口
  eg:
    @GET("api)
     fun reuqestTest(@QueryMap map:HashMap<String, String>): Call<String>
    返回的是String类型
        
      @POST("sso/login")
      @FormUrlEncoded
      fun login(@FieldMap args: HashMap<String, String>): Call<T>
      post协议 回调泛型
      
 ####      请求接口
    var service = RetrofitWrapper.getInstance()!!.create(Api::class.java);
         var call:Call<MainBean> = service.getConfigBean();
         call.enqueue(object :retrofit2.Callback<MainBean>{
             override fun onResponse(call: retrofit2.Call<MainBean>?, response: Response<MainBean>?) {
                 Log.e("TAG","-----onResponse--------->"+response!!.body()!!.name)
             }
 
             override fun onFailure(call: retrofit2.Call<MainBean>?, t: Throwable?) {
                 Log.e("TAG","--------onFailure------>"+t.toString())
             }
 
         });
    
    

