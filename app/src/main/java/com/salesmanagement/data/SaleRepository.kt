package com.salesmanagement.data

import com.salesmanagement.core_network.handleProvider.toFlow
import com.salesmanagement.domain.Sale
import kotlinx.coroutines.flow.Flow

interface SaleRepository {
    suspend fun getAllSales(): Flow<List<SaleModel>>
    suspend fun createSale(saleModel: SaleModel): Flow<Unit>
    suspend fun findSaleHash(hashSale: String): Flow<List<SaleModel>>
}

class SaleRepositoryImpl (
    private val saleApi: SaleApi
) : SaleRepository {
    override suspend fun getAllSales(): Flow<List<SaleModel>> = saleApi.getAllSale().toFlow()
    override suspend fun createSale(saleModel: SaleModel): Flow<Unit> = saleApi.createSale(saleModel).toFlow()
    override suspend fun findSaleHash(hashSale: String): Flow<List<SaleModel>> = saleApi.findSaleHash(hashSale).toFlow()
}