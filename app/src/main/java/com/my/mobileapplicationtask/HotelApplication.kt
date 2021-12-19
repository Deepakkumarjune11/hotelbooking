package com.my.mobileapplicationtask

import android.app.Application
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import java.lang.reflect.Type

class HotelApplication: Application() {
    lateinit var loginService: ApiInterface
    lateinit var registerService: RegisterApiInterface
    lateinit var hotelService:HotelsApiService
    lateinit var emailupdate:ChangeEmailApiService
    lateinit var delete:DeleteApiService
    lateinit var historyService:HistoryApiService
    lateinit var otherUserService: OtherUserApiService
    lateinit var myReservationService:MyReservationsApiService
    override fun onCreate() {
        super.onCreate()
        delete = initHttpDeleteApiService()
        hotelService = initHttpApiService()
        emailupdate = initUpdateHttpApiService()
        historyService = HistoryHttpApiService()
        otherUserService = OtherUserHttpApiService()
        myReservationService = MyReservationHttpApiService()
        loginService = loginApi()
        registerService = initHttpregisterApiService()

    }
    private fun MyReservationHttpApiService():MyReservationsApiService{

        val retrofit = Retrofit.Builder()
            .baseUrl("https://android-kanini-course.cloud")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(MyReservationsApiService::class.java)
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
            .addConverterFactory(loginApplication.NullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(RegisterApiInterface::class.java)
    }
    private fun OtherUserHttpApiService():OtherUserApiService{

        val retrofit = Retrofit.Builder()
            .baseUrl("https://android-kanini-course.cloud/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create( OtherUserApiService::class.java)
    }

    private fun initHttpApiService():HotelsApiService{

        val retrofit = Retrofit.Builder()
            .baseUrl("https://android-kanini-course.cloud/")
            .addConverterFactory(JacksonConverterFactory.create())
            .build()
        return retrofit.create(HotelsApiService::class.java)
    }
    private fun HistoryHttpApiService():HistoryApiService{

        val retrofit = Retrofit.Builder()
            .baseUrl("https://android-kanini-course.cloud/")
            .addConverterFactory(JacksonConverterFactory.create())
            .build()
        return retrofit.create(HistoryApiService::class.java)
    }
    private fun initUpdateHttpApiService():ChangeEmailApiService{

        val retrofit = Retrofit.Builder()
            .baseUrl("https://android-kanini-course.cloud/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(ChangeEmailApiService::class.java)
    }
    fun initHttpDeleteApiService():DeleteApiService  {

        val retrofit = Retrofit.Builder()
            .baseUrl("https://android-kanini-course.cloud/")
            .addConverterFactory(NullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(DeleteApiService::class.java)
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