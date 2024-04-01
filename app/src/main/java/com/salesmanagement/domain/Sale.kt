package com.salesmanagement.domain

data class Sale(
    val id: Long? = null,
    val clientName: String,
    val productName: String,
    val productAmount: String,
    val productQuantity: String,
    val hashSale: String? = null
)