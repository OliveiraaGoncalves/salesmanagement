package com.salesmanagement.domain

import com.salesmanagement.data.SaleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface SaleUseCase {
    suspend fun getAllSales(): Flow<List<Sale>>
    suspend fun createSale(sale: Sale): Flow<Unit>
    suspend fun findSaleHash(hashSale: String): Flow<List<Sale>>
    fun calculateAmount(sales: List<Sale>): Double
}

class SaleUseCaseImpl(
    private val saleRepository: SaleRepository,
    private val saleMapper: SaleMapper
) : SaleUseCase {

    override suspend fun getAllSales(): Flow<List<Sale>> {
        return saleRepository.getAllSales().map {
            it.map { saleModel ->
                saleMapper.mapToItem(saleModel)
            }
        }
    }

    override suspend fun createSale(sale: Sale): Flow<Unit> {
        return saleRepository.createSale(saleMapper.mapToModel(sale))
    }

    override suspend fun findSaleHash(hashSale: String): Flow<List<Sale>> =
        saleRepository.findSaleHash(hashSale).map { it.map { saleMapper.mapToItem(it) } }

    override fun calculateAmount(sales: List<Sale>): Double {
        return sales.sumOf { it.productAmount.toDouble() * it.productQuantity.toDouble() }
    }
}