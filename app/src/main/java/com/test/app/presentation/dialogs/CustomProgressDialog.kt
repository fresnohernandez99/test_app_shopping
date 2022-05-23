package com.test.warofsuits.ui.dialogs

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import com.test.app.databinding.CustomProgressDialogBinding

class CustomProgressDialog(
    val context: Context,
    inflater: LayoutInflater,
    private val isCancelable: Boolean
) {

    private val dialogBuilder = AlertDialog.Builder(context)
    private val informationLayoutBinding = CustomProgressDialogBinding.inflate(inflater)
    private lateinit var ins: AlertDialog

    var isVisible = false

    init {
        build()
    }

    fun build() {
        dialogBuilder.setView(informationLayoutBinding.root)
        dialogBuilder.setCancelable(isCancelable)
        informationLayoutBinding.apply {

        }
        ins = dialogBuilder.create()
        ins.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    fun buildAndShow() {
        build()
        show()
    }

    fun show() {
        ins.show()
        isVisible = true
    }

    fun hide() {
        ins.hide()
        isVisible = false
    }

    fun dismiss() {
        ins.dismiss()
        isVisible = false
    }

}