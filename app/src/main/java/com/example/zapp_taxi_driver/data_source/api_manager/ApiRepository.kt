package com.example.zapp_taxi_driver.data_source.api_manager


import android.util.Base64
import android.util.Log
import com.example.zapp_taxi_driver.helper.Constants

import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

object ApiRepository {
    inline fun <reified RESPONSE> apiGet(methodExt :String): Flow<RESPONSE> {
        return flow<RESPONSE> {
            val response = RestClient.apiService.get {
                url(WebServices.getDomainUrl() +methodExt)
            }
            emit(response.body())
        }.catch { e ->
            Log.e("ApiRepository", "Error fetching data: ${e.message}")
        }.flowOn(Dispatchers.IO)
    }

   inline fun <reified REQUEST , reified RESPONSE> apiPost (methodExt :String, model: REQUEST): Flow<RESPONSE> {
        return flow<RESPONSE> {
            val response = RestClient.apiService.post {
                val creds : String = Constants.USERNAME + ":" + Constants.PASSWORD
                val auth = ("Basic "
                        + Base64.encodeToString(creds.toByteArray(), Base64.NO_WRAP))
                headers{
                    append("Authorization", auth)
                    append("token", Constants.TOKEN)
                }
                url(WebServices.getDomainUrl() +methodExt)
                setBody(model)
            }
            emit(response.body())
        }.catch { e ->
            Log.e("ApiRepository", "Error fetching data: ${e.message}")
        }.flowOn(Dispatchers.IO)
    }


    /** use this function only when calling api from different host */
    inline fun <reified RESPONSE> apiGetByUrl(url :String): Flow<RESPONSE> {
        return flow<RESPONSE> {
            val response = RestClient.apiService.get {
                url(url)
            }
            emit(response.body())
        }.catch { e ->
            Log.e("ApiRepository", "Error fetching data: ${e.message}")
        }.flowOn(Dispatchers.IO)
    }
    /** use this function only when calling api from different host */
    inline fun <reified RESPONSE , reified REQUEST> apiPostByUrl(url :String , model: REQUEST): Flow<RESPONSE> {
        return flow<RESPONSE> {
            val response = RestClient.apiService.post {
                url(url)
                setBody(model)
            }
            emit(response.body())
        }.catch { e ->
            Log.e("ApiRepository", "Error fetching data: ${e.message}")
        }.flowOn(Dispatchers.IO)
    }

}
