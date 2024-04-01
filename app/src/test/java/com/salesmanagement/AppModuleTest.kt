package com.salesmanagement

import android.content.res.Resources
import com.salesmanagement.data.SaleApi
import com.salesmanagement.data.SaleRepository
import com.salesmanagement.di.AppModule
import com.salesmanagement.domain.SaleMapper
import com.salesmanagement.domain.SaleUseCase
import com.salesmanagement.presentation.create.CreateSaleUiModel
import com.salesmanagement.presentation.create.CreateSaleViewModel
import com.salesmanagement.presentation.home.SaleViewModel
import io.mockk.MockKAnnotations
import io.mockk.mockk
import org.junit.Before
import org.junit.Test
import org.koin.dsl.koinApplication
import org.koin.dsl.module
import org.koin.test.check.checkModules
import org.koin.test.junit5.AutoCloseKoinTest

class AppModuleTest : AutoCloseKoinTest() {

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `check all modules`() {
        koinApplication {
            modules(AppModule.module.plus(mockModule))
        }.checkModules()
    }

    private val mockModule = module {
        single<Resources> { mockk(relaxed = true) }
        factory<SaleViewModel> { mockk(relaxed = true) }
        factory<CreateSaleViewModel> { mockk(relaxed = true) }
        factory<SaleUseCase> { mockk(relaxed = true) }
        factory<SaleRepository> { mockk(relaxed = true) }
        factory<SaleMapper> { mockk(relaxed = true) }
        factory<CreateSaleUiModel> { mockk(relaxed = true) }
        factory<SaleApi> { mockk(relaxed = true) }
    }
}