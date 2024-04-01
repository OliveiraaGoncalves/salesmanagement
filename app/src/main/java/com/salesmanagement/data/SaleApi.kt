package com.salesmanagement.data

import com.salesmanagement.core_network.handleProvider.NetworkResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface SaleApi {

    @GET("allSales")
    suspend fun getAllSale(): NetworkResponse<List<SaleModel>>

    @GET("salesHash")
    suspend fun findSaleHash(@Query("hashSale") hashSale: String): NetworkResponse<List<SaleModel>>

    @POST("createSale")
    suspend fun createSale(@Body saleModel: SaleModel): NetworkResponse<Unit>
}