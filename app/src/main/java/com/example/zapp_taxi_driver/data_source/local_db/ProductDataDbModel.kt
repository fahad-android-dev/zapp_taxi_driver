package com.example.zapp_taxi_driver.data_source.local_db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity
data class ProductDataDbModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "productID") val productID: String,
    @ColumnInfo(name = "entityID") val entityID: String?,
    @ColumnInfo(name = "categoryID") val categoryID: String?,
    @ColumnInfo(name = "productName") val name: String?,
    @ColumnInfo(name = "brandName") val brand: String?,
    @ColumnInfo(name = "sku") val sku: String?,
    @ColumnInfo(name = "image") val image: String?,
    @ColumnInfo(name = "quantity") var quantity: Int?,
    @ColumnInfo(name = "finalPrice") val finalPrice: String?,
    @ColumnInfo(name = "regularPrice") val regularPrice: String?,
    @ColumnInfo(name = "discount") val discount: String?,
    @ColumnInfo(name = "remainingQuantity") val remainingQuantity: String?,
    @TypeConverters(Converters::class)
    @ColumnInfo(name = "configOptions") val configOptions: ArrayList<DBConfigurableOptionModel?>? = null
)

data class DBConfigurableOptionModel(
    @ColumnInfo(name = "attribute_id") val attribute_id: String? = null,
    @ColumnInfo(name = "type") val type: String? = null,
    @ColumnInfo(name = "attribute_code") val attribute_code: String? = null,
    @TypeConverters(Converters::class) val attributes: ArrayList<DBAttributeOptionModel?>? = null,
    @ColumnInfo(name = "attributes") var isSelected: Boolean? = false
)

data class DBAttributeOptionModel(
    @ColumnInfo(name = "value") var value: String? = null,
    @ColumnInfo(name = "option_id") var option_id: String? = null,
    @ColumnInfo(name = "option_product_id") var option_product_id: String? = null
)
