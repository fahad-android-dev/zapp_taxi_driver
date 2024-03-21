package com.example.zapp_taxi_driver.data_source.local_db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CartDao {
    @Insert
    fun addProductToCart(vararg products: ProductDataDbModel)

    @Query("UPDATE ProductDataDbModel SET quantity =:strQty WHERE entityID = :productEntityID")
    fun updateProductsInCart(strQty: Int?, productEntityID: String?)

    @Query("UPDATE ProductDataDbModel SET quantity =:strQty,productID =:productID,entityID =:productEntityID," +
            "configOptions =:configurableOptionModel WHERE entityID = :productEntityID")
    fun updateProductsInCartOffline(
        strQty: String?,
        productID: String?,
        productEntityID: String?,
        configurableOptionModel: ArrayList<DBConfigurableOptionModel?>
    )

    @Query("DELETE from ProductDataDbModel where entityID=:productEntityID")
    fun deleteProductFromCart(productEntityID: String?)

    @Query("SELECT DISTINCT quantity FROM ProductDataDbModel WHERE entityID IN (:productEntityID)")
    fun getQtyInCart(productEntityID: String?): Int

    @Query("SELECT * FROM ProductDataDbModel")
    fun getTotalCartProductCount(): List<ProductDataDbModel>

    @Query("SELECT entityID FROM ProductDataDbModel WHERE entityID=:productEntityID")
    fun isProductPresentInCart(productEntityID: String?): Boolean

    @Query("SELECT * FROM ProductDataDbModel")
    fun getAllCartProducts(): List<ProductDataDbModel?>

    @Query("DELETE FROM ProductDataDbModel")
    fun deleteCartTable()
}