package com.salesmanagement.extensions

import android.view.View

fun Boolean.showOrHideView(): Int = if (this) View.VISIBLE else View.GONE
