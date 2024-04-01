package com.salesmanagement

import android.app.Application
import com.salesmanagement.di.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SaleManagementApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@SaleManagementApplication)
            modules(AppModule.list)
        }
    }
}