package com.salesmanagement.presentation.create

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.salesmanagement.databinding.CreateSaleItemListBinding
import com.salesmanagement.databinding.SaleItemListBinding
import com.salesmanagement.domain.Sale

class CreateSaleAdapter(
    private val sales: List<Sale>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemViewHolder).bind(sale = sales[position])
    }

    override fun getItemCount(): Int = sales.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        getItemHolder(parent = parent)

    private fun getItemHolder(parent: ViewGroup) = ItemViewHolder(
        itemBinding = CreateSaleItemListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    private class ItemViewHolder(val itemBinding: CreateSaleItemListBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(
            sale: Sale,
        ) = with(itemBinding) {
            txtDescription.text = sale.clientName
            txtAmount.text = sale.productAmount
        }
    }
}