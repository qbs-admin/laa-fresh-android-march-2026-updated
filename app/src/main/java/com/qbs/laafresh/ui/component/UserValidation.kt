package com.sg.kjm.ui.component

import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.widget.EditText
import androidx.core.content.res.ResourcesCompat
import com.qbs.laafresh.R
import com.qbs.laafresh.ui.component.TypefaceSpan
import java.util.regex.Pattern


object UserValidation {
    private val EMAIL_FORMAT =
        "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"

    fun isEmailAddress(
        editText: EditText,
        required: Boolean,
        mContext: Context,
        stringErrorMessage: String
    ): Boolean {
        return isValid(editText, EMAIL_FORMAT, stringErrorMessage, required, mContext)
    }


    fun isValid(
        editText: EditText,
        regex: String,
        errMsg: String,
        required: Boolean,
        mContext: Context
    ): Boolean {
        val tf: Typeface?
        tf = ResourcesCompat.getFont(mContext, R.font.sfproregular)
        val text = editText.text.toString().trim { it <= ' ' }
        //        editText.setError(null);


        if (required && !hasText(editText, errMsg, mContext)) {
            return false
        }


        if (required && !Pattern.matches(regex, text)) {
            val spannableString = SpannableString(errMsg)
            spannableString.setSpan(
                TypefaceSpan(tf),
                0,
                spannableString.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            editText.error = errMsg
            return false
        }

        return true
    }


    fun hasText(editText: EditText, stringMessage: String, mContext: Context): Boolean {

        val text = editText.text.toString().trim { it <= ' ' }
        //        editText.setError(null);
        val tf: Typeface? = ResourcesCompat.getFont(mContext, R.font.sfproregular)

        if (text.length == 0) {
            val spannableString = SpannableString(stringMessage)
            spannableString.setSpan(
                TypefaceSpan(tf),
                0,
                spannableString.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            editText.error = spannableString
            return false
        }


        return true
    }



    fun hasTextPassword(
        editText: EditText,
        stringMessage: String?,
        mContext: Context?
    ): Boolean {
        val tf: Typeface?
        tf = ResourcesCompat.getFont(mContext!!, R.font.sfproregular)
        val text = editText.text.toString().trim { it <= ' ' }
        editText.error = null
        if (text.length < 6) {
            val spannableString = SpannableString(stringMessage)
            spannableString.setSpan(
                TypefaceSpan(tf),
                0,
                spannableString.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            editText.error = spannableString
            editText.error = stringMessage
            return false
        }
        return true
    }

    fun hasPhoneLength(
        editText: EditText,
        stringMessage: String?,
        mContext: Context?
    ): Boolean {
        val text = editText.text.toString().trim { it <= ' ' }
        editText.error = null
        val tf: Typeface?
        tf = ResourcesCompat.getFont(mContext!!, R.font.sfproregular)
        if (text.length < 8) {
            val spannableString = SpannableString(stringMessage)
            spannableString.setSpan(
                TypefaceSpan(tf),
                0,
                spannableString.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            editText.error = spannableString
            return false
        }
        return true
    }

    fun hasMatchPassword(
        editText: EditText,
        confirmPass: EditText,
        stringMessage: String?,
        mContext: Context?
    ): Boolean {
        val text = editText.text.toString().trim { it <= ' ' }
        val text1 = confirmPass.text.toString().trim { it <= ' ' }
        editText.error = null
        confirmPass.error = null
        val tf: Typeface?
        tf = ResourcesCompat.getFont(mContext!!, R.font.sfproregular)
        if (!text1.matches(text.toRegex())) {
            val spannableString = SpannableString(stringMessage)
            spannableString.setSpan(
                TypefaceSpan(tf),
                0,
                spannableString.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            confirmPass.error = spannableString
            return false
        }
        return true
    }

    fun hasTextCvv(editText: EditText, stringMessage: String, mContext: Context): Boolean {

        val text = editText.text.toString().trim { it <= ' ' }
        //        editText.setError(null);
        val tf: Typeface? = ResourcesCompat.getFont(mContext, R.font.sfproregular)

        if (text.length <3) {
            val spannableString = SpannableString(stringMessage)
            spannableString.setSpan(
                TypefaceSpan(tf),
                0,
                spannableString.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            editText.error = spannableString
            return false
        }


        return true
    }


}
