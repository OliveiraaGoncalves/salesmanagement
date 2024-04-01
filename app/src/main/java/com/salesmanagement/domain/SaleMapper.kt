package com.salesmanagement.domain

import com.salesmanagement.data.SaleModel

interface SaleMapper {
    fun mapToItem(saleModel: SaleModel): Sale
    fun mapToModel(sale: Sale): SaleModel
}

class SaleMapperImpl : SaleMapper {

    override fun mapToItem(saleModel: SaleModel): Sale {
        saleModel.apply {
            return Sale(
                id = this.id,
                clientName = this.clientName,
                productName = this.productName,
                productAmount = this.productAmount,
                productQuantity = this.productQuantity,
                hashSale = this.hashSale
            )
        }
    }

    override fun mapToModel(sale: Sale): SaleModel {
        sale.apply {
            return SaleModel(
                clientName = this.clientName,
                productName = this.productName,
                productAmount =  this.productAmount,
                productQuantity = this.productQuantity,
                hashSale = this.hashSale
            )
        }
    }
}