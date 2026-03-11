package com.qbs.laafresh.ui.extension

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.text.Html
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StrikethroughSpan
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.qbs.laafresh.R
import java.text.DecimalFormat
import java.util.regex.Pattern

fun setDrawerToggle(drawer: DrawerLayout?, appToolbar: Toolbar, activity: Activity) {

   /* val mDrawerToggle = ActionBarDrawerToggle(
        activity, drawer, appToolbar,
        R.string.navigation_drawer_open, R.string.navigation_drawer_close
    )
    drawer?.addDrawerListener(mDrawerToggle)
    mDrawerToggle.syncState()
    mDrawerToggle.isDrawerIndicatorEnabled = true*/
}

private fun isValidMail(email: String): Boolean {
    val EMAIL_STRING = ("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
    return Pattern.compile(EMAIL_STRING).matcher(email).matches()
}

fun initToolbar(activity: Activity, toolbar: Toolbar) {

    (activity as AppCompatActivity).setSupportActionBar(toolbar)

//    activity.supportActionBar?.setBackgroundDrawable(
//        ContextCompat.getDrawable(
//            activity,
//            R.drawable.toolbar_transpant
//        )
//    )
}
typealias onClickToolbar = () -> Unit

fun initToolbarWithBack(activity: Activity, toolbar: Toolbar, onClickToolbar: onClickToolbar) {

    (activity as AppCompatActivity).setSupportActionBar(toolbar)
    activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    toolbar.setNavigationOnClickListener { onClickToolbar.invoke() }

//    activity.supportActionBar?.setBackgroundDrawable(
//        ContextCompat.getDrawable(
//            activity,
//            R.drawable.toolbar_transpant
//        )
//    )
}


fun View.show() {
    visibility = View.VISIBLE
}


fun View.hide() {
    visibility = View.GONE
}

fun dashboardNavigationController(
    menuItem: MenuItem,
    activity: Activity,
    bottomNavigationView: BottomNavigationView
) {
    val navigationController: NavController =
        Navigation.findNavController(activity, R.id.dashboardNavHost)


//    visibilityNavElements(navigationController,bottomNavigationView)
}


fun navigationController(sourceId: Int, destinationId: Int, activity: Activity) {
    val navigationController: NavController =
        Navigation.findNavController(activity, sourceId)
    navigationController.navigate(destinationId)

}

fun navigationControllerArguments(
    sourceId: Int,
    destinationId: Int,
    activity: Activity,
    dataClass: Any
) {
    val bundle = bundleOf("reqBasicInfo" to dataClass)
    val navigationController: NavController =
        Navigation.findNavController(activity, sourceId)
    navigationController.navigate(destinationId, bundle)

}

fun Context.toast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Context.toastLong(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}



fun Context.remove_demical(value: String?): String {
    val number: Double = value!!.toDouble()
    val format = DecimalFormat("0.00")
    val formatted: String = format.format(number)
    return formatted
}

fun errorResponseMessage(successResponse: Any): String {
    /*return when (successResponse) {
        is ResMessage -> successResponse.message.toString()
        is String -> {

            successResponse
        }
        else -> "Error"
    }*/
    return  ""
}
fun specialPriceSpan(specialPrice:String): SpannableString {
    val spannableString1 = SpannableString(specialPrice)
    spannableString1.setSpan(StrikethroughSpan(), 0, spannableString1.length, 0)
    return spannableString1
}
fun reduceProduct(productCount: Int): String {
    var returnCount = ""
    if (productCount > 1) {
        returnCount = (productCount - 1).toString()
    } else if (productCount == 1) {
        returnCount = "0"
    }
    return returnCount
}

fun addProduct(productCount: Int): String {
    val Edittext_plus: Int = productCount
    return (Edittext_plus + 1).toString()

}
typealias result = (Boolean) -> Unit

fun Activity.showDialAlert(title: String, result: result) {
    var exitDialog = androidx.appcompat.app.AlertDialog.Builder(this)
    exitDialog.setMessage(title)
    exitDialog.setNegativeButton(
        "no"
    ) { dialog, which ->
        result(false)
        dialog.dismiss()
    }
    exitDialog.setPositiveButton(
        "yes"
    ) { dialog, which ->
        result(true)
        dialog.dismiss()
        dialog.cancel()
    }

    exitDialog.show()
}

fun Fragment.blockInput() {
    activity?.window?.setFlags(
        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
    )
}

fun Fragment.unblockInput() {
    activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
}
fun Context.hideKeyBoard() {
    val inputMethodManager =            // To hide KeyBoard
        this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
}

fun View.showKeyboard() {
    this.requestFocus()
    val inputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun isConnected(activity: Activity): Boolean {
    val connectivityManager =
        activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return connectivityManager.activeNetworkInfo != null
}

fun String.toSpanned(): Spanned {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        return Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    } else {
        @Suppress("DEPRECATION")
        return Html.fromHtml(this)
    }
}

