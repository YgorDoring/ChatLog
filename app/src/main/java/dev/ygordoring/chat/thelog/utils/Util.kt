package dev.ygordoring.chat.thelog.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import dev.ygordoring.chat.thelog.R
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

object Util {
    fun dateSqlToBPtBr(date: Date): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        return sdf.format(date)
    }

    fun hideKeyboard(context: Context, viewCurrentFocus: View?) {
        if (viewCurrentFocus != null) {
            val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(viewCurrentFocus.windowToken, 0)
            viewCurrentFocus.clearFocus()
        }
    }

    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun showSnackbar(context: Context, view: View, msg: String) {
        val snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_LONG)
        snackbar.view.setBackgroundColor(
            ContextCompat.getColor(
                context,
                android.R.color.holo_red_dark
            )
        )
        snackbar.setActionTextColor(ContextCompat.getColor(context, R.color.white))

        val mTextView =
            snackbar.view.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        mTextView.maxLines = 3
        mTextView.textAlignment = View.TEXT_ALIGNMENT_CENTER

        snackbar.show()
    }
}