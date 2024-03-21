package com.example.zapp_taxi_driver.data_source.local_db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken



class Converters {
    companion object {
        @TypeConverter
        @JvmStatic
        fun fromString(value: String): ArrayList<DBConfigurableOptionModel> {
            val listType = object : TypeToken<ArrayList<DBConfigurableOptionModel>>() {

            }.type
            return Gson().fromJson(value, listType)
        }

        @TypeConverter
        @JvmStatic
        fun fromArrayList(list: ArrayList<DBConfigurableOptionModel>): String {
            val gson = Gson()
            return gson.toJson(list)
        }

        @TypeConverter
        @JvmStatic
        fun fromStringAttribute(value: String): ArrayList<DBAttributeOptionModel> {
            val listType = object : TypeToken<ArrayList<DBAttributeOptionModel>>() {

            }.type
            return Gson().fromJson(value, listType)
        }

        @TypeConverter
        @JvmStatic
        fun fromArrayListAttribute(list: ArrayList<DBAttributeOptionModel>): String {
            val gson = Gson()
            return gson.toJson(list)
        }
    }
}