package com.salesmanagement.extensions

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*

fun EditText.moneyMask(locale: Locale = Locale.getDefault()) {
    val numberFormat = NumberFormat.getCurrencyInstance(locale)
    val currencySymbol = Currency.getInstance(locale).symbol

    val maskWatcher = object : TextWatcher {
        private var current = ""

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            if (s.toString() != current) {
                removeTextChangedListener(this)

                val cleanString = s.toString()
                    .replace(currencySymbol, "")
                    .replace("[,.]".toRegex(), "")
                    .trim()

                val parsed = try {
                    val parsedValue = BigDecimal(cleanString)
                    val formattedValue = numberFormat.format(parsedValue.divide(BigDecimal(100)))
                    formattedValue.substring(1) // Remove o primeiro caractere (o símbolo da moeda)
                } catch (e: Exception) {
                    ""
                }

                current = parsed
                setText(parsed)
                setSelection(parsed.length)

                addTextChangedListener(this)
            }
        }
    }

    addTextChangedListener(maskWatcher)
}
