package com.salesmanagement.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.salesmanagement.databinding.SaleItemListBinding
import com.salesmanagement.domain.Sale

class SaleAdapter(
    private val sales: List<Sale>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemViewHolder).bind(sale = sales[position])
    }

    override fun getItemCount(): Int = sales.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        getItemHolder(parent = parent)

    private fun getItemHolder(parent: ViewGroup) = ItemViewHolder(
        itemBinding = SaleItemListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    private class ItemViewHolder(val itemBinding: SaleItemListBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(
            sale: Sale,
        ) = with(itemBinding) {
            txtDescription.text = sale.clientName
        }
    }
}