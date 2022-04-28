package com.sixlogs.pt.base.util

import android.app.Activity
import android.app.Dialog
import android.view.View
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.sixlogs.pt.R


fun View.showSnackBar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).show()
}

fun alertDialog(activity: Activity, message: String, title: String) {

    val dialog: androidx.appcompat.app.AlertDialog? =
        MaterialAlertDialogBuilder(activity, R.style.MaterialAlertDialogText)
            .setTitle("HomeQ")
            .setMessage(message)
            .setPositiveButton("OK") { dialog, which ->
                // Respond to positive button press
                dialog.dismiss()

            }
            .show()


    dialog?.getButton(Dialog.BUTTON_POSITIVE)?.textSize = 15f
    dialog?.getButton(Dialog.BUTTON_NEGATIVE)?.textSize = 15f
    val textView = dialog?.findViewById<TextView>(android.R.id.message)
    val textViewTitle = dialog?.findViewById<TextView>(android.R.id.title)
    if (textView != null) {
        textView.textSize = 15f
        val face = ResourcesCompat.getFont(activity, R.font.akaya_telivigala)
        textView.typeface = face
        textView.maxLines = 10
        textView.isSingleLine = false

        textViewTitle?.textSize = 10f
        textViewTitle?.typeface = face


    }
}