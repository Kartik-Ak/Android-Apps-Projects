package com.example.spamdetector
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import com.example.textfiled.R
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Ui(navController: NavController) {
    val context = LocalContext.current

    var emailContent by remember { mutableStateOf("") }
    Image(
        painter = painterResource(id = R.drawable.bgu), contentDescription = "Background",
        contentScale = ContentScale.Crop
    )
    

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        OutlinedTextField(
            value = emailContent,
            onValueChange = { emailContent = it },
            textStyle = TextStyle.Default.copy(
                color = Color.Black,
                fontSize = 18.sp,
                textAlign = TextAlign.Left,
            ),
            modifier = Modifier
                .width(450.dp)
                .heightIn(min = 56.dp, max = 200.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(20.dp),
            placeholder = {
                Text(
                    text = "Enter your Message",
                    color = Color.Black
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.MailOutline,
                    contentDescription = "MailOutline",
                    tint = Color.Black
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color.White,
                focusedBorderColor = Color.Cyan,
                unfocusedBorderColor = Color.Cyan
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                // API Call
                CoroutineScope(Dispatchers.IO).launch {
                    val url = "https://api.apilayer.com/spamchecker?threshold=threshold"
                    val mediaType = "text/plain".toMediaTypeOrNull()
                    val requestBody = emailContent.toRequestBody(mediaType) // Dynamic content

                    val request = Request.Builder()
                        .url(url)
                        .addHeader("apikey", "uIsAo7rCVcfwD3C3owRycj1GY4dbUhbH")
                        .post(requestBody)
                        .build()

                    try {
                        val client = OkHttpClient()
                        val response = client.newCall(request).execute()
                        val responseBody = response.body?.string() ?: ""

                        if (response.isSuccessful) {
                            // Parse JSON response
                            val jsonResponse = JSONObject(responseBody)
                            val isSpam = jsonResponse.getBoolean("is_spam")
                            val score = jsonResponse.getDouble("score")

                            // Update UI on the main thread
                            withContext(Dispatchers.Main) {
                                if (isSpam) {
                                    navController.navigate("Screen1/$score")
                                    Toast.makeText(
                                        context,
                                        "Spam detected! Score: $score",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    navController.navigate("Screen2/$score")
                                    Toast.makeText(
                                        context,
                                        "Not spam! Score: $score",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        } else {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                    context,
                                    "API Error: ${response.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            },
            colors = ButtonColors(
                containerColor = Color.Black,
                contentColor = Color.White,
                disabledContainerColor = Color.Cyan,
                disabledContentColor = Color.LightGray
            )
        ) {
            Text(text = "Submit")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun UiPreview() {
    val navController = rememberNavController()
    Ui(navController = navController)
}
