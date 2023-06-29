package com.example.mironote.utils.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.ContextThemeWrapper
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatTextView
import com.example.mironote.R

class DropDownView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
): AppCompatTextView(ContextThemeWrapper(context, R.style.SpinnerStyle), attributeSet, defStyleAttr) {

    private var selectedOption: String = "yellow"

    private val optionsDialog = AlertDialog.Builder(context)

    fun setOptions(optionsTitle: String, list: List<String>?) {
        setText(optionsTitle)
        selectedOption = optionsTitle
        val array = list?.map { item -> item as CharSequence }?.toTypedArray()
        optionsDialog.setItems(array) { dialog, which ->
            selectedOption = array?.get(which)?.toString().toString()
            setText(selectedOption)
            dialog.dismiss()
        }
        optionsDialog.create()
        setOnClickListener { optionsDialog.show() }
    }

    fun getSelectedOption() = selectedOption
}