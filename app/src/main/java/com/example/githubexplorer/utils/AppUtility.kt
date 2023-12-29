package com.example.githubexplorer.utils

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.util.TypedValue
import com.example.githubexplorer.CustomToast
import com.example.githubexplorer.R
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

class AppUtility {
    companion object {
        fun showToast(message: String, duration: Int = CustomToast.LENGTH_SHORT) {
            CustomToast.show(message, duration)
        }

        fun openUrl(context: Context, htmlUrl: String) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(htmlUrl))
            context.startActivity(intent)
        }

        fun getLanguageColor(language: String): Int {
            val languageColorMap = mapOf(
                "javascript" to R.color.colorJavaScript,
                "java" to R.color.colorJava,
                "python" to R.color.colorPython,
                "ruby" to R.color.colorRuby,
                "typescript" to R.color.colorTypeScript,
                "c#" to R.color.colorCSharp,
                "cpp" to R.color.colorCpp,
                "css" to R.color.colorCSS,
                "c" to R.color.colorC,
                "go" to R.color.colorGo,
                "swift" to R.color.colorSwift,
                "objectivec" to R.color.colorObjectiveC,
                "php" to R.color.colorPHP,
                "shell" to R.color.colorShell,
                "powershell" to R.color.colorPowerShell,
                "rust" to R.color.colorRust,
                "kotlin" to R.color.colorKotlin,
                "scala" to R.color.colorScala,
                "html" to R.color.colorHTML,
                "vue" to R.color.colorVue,
                "perl" to R.color.colorPerl,
                "r" to R.color.colorR,
            )
            return if (languageColorMap.containsKey(language.lowercase())) {
                languageColorMap[language.lowercase()]!!
            } else {
                R.color.white
            }

        }

        fun getFormattedDate(updatedAt: String): String {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            inputFormat.timeZone = TimeZone.getTimeZone("UTC")
            val date = inputFormat.parse(updatedAt)

            val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            return outputFormat.format(date)
        }

        fun Int.toDp(): Float {
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                this.toFloat(),
                Resources.getSystem().displayMetrics
            )
        }

    }
}