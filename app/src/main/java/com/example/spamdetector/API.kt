package com.example.spamdetector
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

class API {
    private val client = OkHttpClient()

    fun APIcall(email: String): String? {
        val url = "https://get-email-information.p.rapidapi.com/getemailinfo?email=$email"

        val request = Request.Builder()
            .url(url)
            .get()
            .addHeader("x-rapidapi-key", "1d3c7268f7msh5ef1791fee3d653p1e7cf9jsn5330833e3991")
            .addHeader("x-rapidapi-host", "get-email-information.p.rapidapi.com")
            .build()

        return try {
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                response.body?.string()  // API response as string
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}