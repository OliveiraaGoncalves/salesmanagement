package com.salesmanagement.presentation.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.salesmanagement.databinding.ActivityMainBinding
import com.salesmanagement.extensions.showOrHideView
import com.salesmanagement.presentation.create.CreateSaleActivity
import com.salesmanagement.presentation.home.SaleViewModel.Companion.isEnable
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: SaleViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        lifecycle.addObserver(viewModel)

        setupObserverViewModel()

        binding.floatingActionButton.setOnClickListener {
            startActivity(CreateSaleActivity.newIntent(this))
        }

        swipeList()

    }

    private fun setupObserverViewModel() {
        viewModel.state.observe(this) { state ->
            when (state) {
                is SaleState.ContentScreen -> {
                    with(binding) {
                        this.rcSale.adapter = SaleAdapter(state.items)
                        this.rcSale.layoutManager = LinearLayoutManager(this@MainActivity)
                        this.swipeRefresh.isRefreshing = state.processors.visibility
                        this.rcSale.visibility = state.processors.visibility.not().showOrHideView()
                        this.errorTV.visibility = state.processors.visibility.showOrHideView()
                        state.totalAmount.let {
                            this.txtAmount.text = it
                        }
                    }
                }

                is SaleState.ShowLoading -> {
                    binding.swipeRefresh.isRefreshing = state.visibility
                }

                is SaleState.Error -> {
                    showError(state.processors.visibility)
                }
            }
        }
    }

    private fun swipeList() = with(binding.swipeRefresh) {
        this.setOnRefreshListener {
            refresh()
        }
    }

    override fun onResume() {
        super.onResume()
        refresh()
    }

    private fun refresh(visibility: Boolean = isEnable) {
        with(binding) {
            this.swipeRefresh.isRefreshing = visibility
            lifecycle.coroutineScope.launch {
                viewModel.getAllSales()
            }
        }
    }

    private fun showError(visibility: Boolean) = with(binding) {
        this.rcSale.visibility = View.GONE
        this.errorTV.visibility = View.VISIBLE
        this.swipeRefresh.isRefreshing = visibility
    }
}