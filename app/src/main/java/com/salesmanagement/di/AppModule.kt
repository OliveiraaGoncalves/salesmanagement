package com.salesmanagement.di

import android.content.res.Resources
import com.salesmanagement.core_network.di.NetworkModule
import com.salesmanagement.data.SaleApi
import com.salesmanagement.data.SaleRepository
import com.salesmanagement.data.SaleRepositoryImpl
import com.salesmanagement.domain.SaleMapper
import com.salesmanagement.domain.SaleMapperImpl
import com.salesmanagement.domain.SaleUseCase
import com.salesmanagement.domain.SaleUseCaseImpl
import com.salesmanagement.presentation.create.CreateSaleUiModel
import com.salesmanagement.presentation.create.CreateSaleUiModelImpl
import com.salesmanagement.presentation.create.CreateSaleViewModel
import com.salesmanagement.presentation.home.SaleViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

object AppModule {

    val module = module {
        single<Resources> { androidContext().resources }
        viewModel {
            SaleViewModel(
                saleUseCase = get(),
            )
        }

        viewModel {
            CreateSaleViewModel(
                saleUseCase = get(),
                uiModel = get()
            )
        }

        factory<SaleUseCase> {
            SaleUseCaseImpl(
                saleRepository = get(),
                saleMapper = get()
            )
        }

        factory<SaleRepository> {
            SaleRepositoryImpl(
                saleApi = get(),
            )
        }

        factory<SaleMapper> {
            SaleMapperImpl()
        }

        factory<CreateSaleUiModel> {
            CreateSaleUiModelImpl(
                resources = androidContext().resources
            )
        }

        factory { get<Retrofit>().create(SaleApi::class.java) }
    }
    val list = module + NetworkModule.module
}