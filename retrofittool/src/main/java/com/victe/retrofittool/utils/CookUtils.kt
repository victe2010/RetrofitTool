package com.victe.exercise.http

/**
 * Created by 13526 on 2018/1/24.
 */
class CookUtils{
    private var cookieArray = HashSet<String>();
    companion object {
        var intance:CookUtils?= null;
        fun getInsatnce():CookUtils?{
            if (intance == null){
                synchronized(CookUtils::class.java){
                    intance = CookUtils();
                }
            }
            return intance;
        }
    }

    fun getCookie():String{
        return cookieArray.joinToString(";")
    }

    fun setCookie(cookie:String){
        for (item in cookie.split(";")){
            if (item.contains("SIG") || item.contains("JSESSIONID")){
                cookieArray.add(item);
            }
        }
    }
}