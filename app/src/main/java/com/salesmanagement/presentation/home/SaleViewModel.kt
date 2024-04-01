package com.salesmanagement.presentation.home

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salesmanagement.domain.Sale
import com.salesmanagement.domain.SaleUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

sealed class SaleState {
    data class ShowLoading(val visibility: Boolean) : SaleState()
    data class ContentScreen(
        val items: List<Sale>,
        val processors: ShowLoading,
        val totalAmount: String
    ) : SaleState()

    data class Error(val processors: ShowLoading) : SaleState()
}

class SaleViewModel(
    private val saleUseCase: SaleUseCase
) : ViewModel(), DefaultLifecycleObserver {

    private val _state = MutableLiveData<SaleState>()
    val state: LiveData<SaleState>
        get() = _state

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        getAllSales()
    }

    fun getAllSales() {
        _state.value = SaleState.ShowLoading(isEnable)
        viewModelScope.launch {
            saleUseCase.getAllSales().catch {
                _state.postValue(
                    SaleState.Error(
                        processors = SaleState.ShowLoading(isEnable.not())
                    )
                )
            }.collect {
                _state.value =
                    SaleState.ContentScreen(
                        items = it,
                        processors = SaleState.ShowLoading(isEnable.not()),
                        totalAmount = defaultCurrency.plus(saleUseCase.calculateAmount(it))
                    )
            }
        }
    }

    companion object {
        const val isEnable = true
        const val defaultCurrency = "R$ "
    }
}