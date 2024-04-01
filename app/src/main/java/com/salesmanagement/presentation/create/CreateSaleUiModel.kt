package com.salesmanagement.presentation.create

import android.content.res.Resources
import com.salesmanagement.R

interface CreateSaleUiModel {
    fun attributeRequired(): String
    fun orderCreated(): String
    fun failureOderNotCreated(): String
}

class CreateSaleUiModelImpl(
    private val resources: Resources
) : CreateSaleUiModel {

    override fun attributeRequired(): String = resources.getString(R.string.attibute_required)

    override fun orderCreated(): String = resources.getString(R.string.show_alert_order_created)

    override fun failureOderNotCreated(): String =
        resources.getString(R.string.show_alert_order_not_created)

}