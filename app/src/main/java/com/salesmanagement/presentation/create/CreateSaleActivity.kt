package com.salesmanagement.presentation.create

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.salesmanagement.databinding.ActivityCreateSaleBinding
import com.salesmanagement.extensions.moneyMask
import com.salesmanagement.extensions.showOrHideView
import com.salesmanagement.presentation.home.SaleViewModel.Companion.isEnable
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Locale

class CreateSaleActivity : AppCompatActivity() {
    private val viewModel: CreateSaleViewModel by viewModel()
    private lateinit var binding: ActivityCreateSaleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(viewModel)

        supportActionBar?.setDisplayHomeAsUpEnabled(isEnable)
        supportActionBar?.setDisplayShowHomeEnabled(isEnable)


        binding = ActivityCreateSaleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarDetail.setNavigationOnClickListener {
            finish()
        }
        binding.textInputLayoutAmount.editText?.moneyMask(Locale.US)

        binding.button.setOnClickListener {
            viewModel.validateInputData(
                customerName = binding.textInputLayoutCustomerName,
                productName = binding.textInputLayoutProductName,
                productQuantity = binding.textInputLayoutQuantity,
                productAmount = binding.textInputLayoutAmount
            ) {
                hideKeyboard()
            }
        }

        setupObserverViewModel()
    }

    private fun setupObserverViewModel() {
        viewModel.state.observe(this) { state ->
            when (state) {
                is CreateSaleState.ContentScreen -> {
                    with(binding) {
                        this.rcCreateSale.adapter = CreateSaleAdapter(state.items)
                        this.rcCreateSale.layoutManager =
                            LinearLayoutManager(this@CreateSaleActivity)
                        this.progress.visibility = state.processors.visibility.showOrHideView()
                        this.msgProductNotCreated.visibility = isEnable.not().showOrHideView()
                        this.txtLabelTotalAmount.visibility = isEnable.showOrHideView()
                        this.txtTotalAmount.visibility = isEnable.showOrHideView()
                        this.txtTotalAmount.text = state.totalAmount.toString()
                        showAlert(msg = state.msg)
                    }
                }

                is CreateSaleState.ShowLoading -> {
                    binding.progress.visibility = state.visibility.showOrHideView()
                }

                is CreateSaleState.Error -> {
                    with(binding) {
                        this.progress.visibility = state.processors.visibility.showOrHideView()
                    }
                    showAlert(msg = state.msg)
                }
            }
        }
    }

    private fun showAlert(msg: String) {
        Toast.makeText(
            this@CreateSaleActivity,
            msg,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, CreateSaleActivity::class.java)
        }
    }
}