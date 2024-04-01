package com.salesmanagement.presentation.create

import android.view.View
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.textfield.TextInputLayout
import com.salesmanagement.domain.Sale
import com.salesmanagement.domain.SaleUseCase
import com.salesmanagement.presentation.home.SaleViewModel.Companion.isEnable
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.util.UUID

sealed class CreateSaleState {
    data class ShowLoading(val visibility: Boolean) : CreateSaleState()
    data class ContentScreen(
        val items: List<Sale>,
        val processors: ShowLoading,
        val totalAmount: Double,
        val msg: String
    ) : CreateSaleState()

    data class Error(val processors: ShowLoading, val msg: String) : CreateSaleState()
}

class CreateSaleViewModel(
    private val saleUseCase: SaleUseCase,
    private val uiModel: CreateSaleUiModel
) : ViewModel(), DefaultLifecycleObserver {

    private val _state = MutableLiveData<CreateSaleState>()
    val state: LiveData<CreateSaleState>
        get() = _state

    val hash = generateHash()

    private fun createSale(sale: Sale) {
        _state.value = CreateSaleState.ShowLoading(isEnable)
        viewModelScope.launch {
            saleUseCase.createSale(sale).catch {
                _state.postValue(
                    CreateSaleState.Error(
                        processors = CreateSaleState.ShowLoading(isEnable.not()),
                        msg = uiModel.failureOderNotCreated()
                    )
                )
            }.collect {
                sale.hashSale?.let { hashSale ->
                    delay(1000)
                    saleUseCase.findSaleHash(hashSale).catch {
                        _state.postValue(
                            CreateSaleState.Error(
                                processors = CreateSaleState.ShowLoading(isEnable.not()),
                                msg = uiModel.failureOderNotCreated()
                            )
                        )
                    }.collect {
                        _state.value =
                            CreateSaleState.ContentScreen(
                                items = it,
                                processors = CreateSaleState.ShowLoading(isEnable.not()),
                                totalAmount = saleUseCase.calculateAmount(it),
                                msg = uiModel.orderCreated()
                            )
                    }
                }
            }
        }
    }

    fun validateInputData(
        customerName: TextInputLayout,
        productName: TextInputLayout,
        productQuantity: TextInputLayout,
        productAmount: TextInputLayout,
        blockHideKeyBoard: () -> Unit
    ) {

        val inputs = listOf(customerName, productName, productQuantity, productAmount)
        val resultEmpty = inputs.filter { it.editText?.text?.isEmpty() == isEnable }
        inputs.filter { it.editText?.text?.isNotEmpty() == true }.map {
            it.error = null
        }

        if (resultEmpty.isEmpty()) {
            blockHideKeyBoard.invoke()
            createSale(
                Sale(
                    clientName = customerName.editText?.text.toString(),
                    productName = productName.editText?.text.toString(),
                    productQuantity = productQuantity.editText?.text.toString(),
                    productAmount = productAmount.editText?.text.toString(),
                    hashSale = hash
                )
            )
        } else {
            resultEmpty.map {
                it.error = uiModel.attributeRequired()
            }
        }
    }

    private fun generateHash(): String {
        return UUID.randomUUID().toString()
    }
}