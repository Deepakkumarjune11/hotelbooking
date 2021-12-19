package com.my.mobileapplicationtask

import android.app.Application
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type

class loginApplication:Application() {
    lateinit var loginService: ApiInterface
    lateinit var registerService: RegisterApiInterface
    override fun onCreate() {
        super.onCreate()
        loginService = loginApi()
        registerService = initHttpregisterApiService()
    }

fun loginApi(): ApiInterface
{
    val retrofit= Retrofit.Builder()
        .baseUrl("https://android-kanini-course.cloud/hotelBooking/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    return retrofit.create(ApiInterface::class.java)
}
fun initHttpregisterApiService(): RegisterApiInterface {

    val retrofit = Retrofit.Builder()
        .baseUrl("https://android-kanini-course.cloud/hotelBooking/")
        .addConverterFactory(NullOnEmptyConverterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    return retrofit.create(RegisterApiInterface::class.java)
}

    class NullOnEmptyConverterFactory : Converter.Factory() {
        override fun responseBodyConverter(
            type: Type?,
            annotations: Array<Annotation>?,
            retrofit: Retrofit?
        ): Converter<ResponseBody, *>? {
            val delegate = retrofit!!.nextResponseBodyConverter<Any>(this, type!!, annotations!!)
            return Converter<ResponseBody, Any> {
                if (it.contentLength() == 0L) return@Converter
                delegate.convert(it)
            }
        }
    }
}