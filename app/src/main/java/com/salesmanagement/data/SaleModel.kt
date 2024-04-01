package com.salesmanagement.data

data class SaleModel (
    val id: Long? = null,
    val clientName: String,
    val productName: String,
    val productAmount: String,
    val productQuantity: String,
    val hashSale: String? = null
)
