package com.example.spamdetector
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONException
import org.json.JSONObject

object Test {
    private val client = OkHttpClient()

    // Make API Call function with dynamic email content
    fun makeApiCall(emailContent: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val url = "https://api.apilayer.com/spamchecker?threshold=threshold"
            val mediaType = "text/plain".toMediaTypeOrNull()
            val requestBody = emailContent.toRequestBody(mediaType)

            val request = Request.Builder()
                .url(url)
                .addHeader("apikey", "uIsAo7rCVcfwD3C3owRycj1GY4dbUhbH") // Make sure to replace with actual API key
                .post(requestBody)
                .build()

            try {
                val response = client.newCall(request).execute()
                val responseBody = response.body?.string()

                // Check if response is empty or null
                if (responseBody.isNullOrEmpty()) {
                    Log.e("API_RESPONSE", "Empty or null response from the server.")
                    return@launch
                }

                // Check for HTTP error code
                if (!response.isSuccessful) {
                    Log.e("API_ERROR", "HTTP Error Code: ${response.code}, Message: ${response.message}")
                    return@launch
                }

                // Parse JSON response safely
                try {
                    val jsonResponse = JSONObject(responseBody)
                    val isSpam = jsonResponse.optBoolean("is_spam", false)
                    val score = jsonResponse.optDouble("score", -1.0)
                    val result = jsonResponse.optString("result", "No result")

                    // Log API response details
                    Log.d("API_RESPONSE", "isSpam: $isSpam, Score: $score, Result: $result")

                    // Handling the API response further, for example, navigating based on spam status
                    withContext(Dispatchers.Main) {
                        if (isSpam) {
                            // Handle spam detection logic (e.g., show Toast, navigate)
                            Log.d("API_RESPONSE", "Spam detected with score: $score")
                        } else {
                            // Handle non-spam logic
                            Log.d("API_RESPONSE", "Not spam, Score: $score")
                        }
                    }
                } catch (e: JSONException) {
                    Log.e("API_ERROR", "Error parsing JSON response: ${e.message}")
                }

            } catch (e: Exception) {
                Log.e("API_ERROR", "Error: ${e.message}")
            }
        }
    }
}
