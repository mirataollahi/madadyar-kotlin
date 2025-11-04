package ir.madadyar.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object ErrorHandler {
    
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
               capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    }
    
    fun getErrorMessage(exception: Throwable): String {
        return when (exception) {
            is UnknownHostException, is SocketTimeoutException -> {
                "اتصال به اینترنت برقرار نیست. لطفا اتصال خود را بررسی کنید."
            }
            is IOException -> {
                "خطا در ارتباط با سرور. لطفا دوباره تلاش کنید."
            }
            is Exception -> {
                exception.message ?: "خطایی رخ داده است. لطفا دوباره تلاش کنید."
            }
            else -> {
                "خطای غیرمنتظره‌ای رخ داده است."
            }
        }
    }
    
    fun getErrorMessageFromStatusCode(statusCode: Int): String {
        return when (statusCode) {
            422 -> "اطلاعات وارد شده صحیح نمیباشند"
            290 -> "این شماره قبلا ثبت شده است"
            318 -> "کد وارد شده صحیح نمیباشد"
            336 -> "کد وارد شده منقضی شده است"
            401, 403 -> "دسترسی غیرمجاز. لطفا دوباره وارد شوید."
            404 -> "صفحه مورد نظر یافت نشد"
            500, 502, 503 -> "خطا در سرور. لطفا بعدا تلاش کنید."
            else -> "خطایی رخ داده است (کد خطا: $statusCode)"
        }
    }
}

