package com.example.spamdetector

import android.widget.ImageView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.textfiled.R
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Output2(score2: String) {

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.sc3),
            contentDescription = "Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

          AndroidView(
            factory = { context ->
                ImageView(context).apply {
                    setImageDrawable(
                        pl.droidsonroids.gif.GifDrawable(
                            context.resources,
                            R.drawable.gi
                        )
                    )
                }
            },
            modifier = Modifier
                .size(250.dp)
                .align(Alignment.TopCenter)
        )

        // Score Text Display
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 250.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Spam Detector Result",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(16.dp))

           
            OutlinedTextField(
                value = score2,
                onValueChange = {},
                label = { Text("Spam Score", color = Color.White) },
                readOnly = true,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    containerColor = Color.Transparent,
                    unfocusedBorderColor = Color.Cyan,
                    unfocusedLabelColor = Color.White,
                    focusedLabelColor = Color.White
                )
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Output2Preview() {
    Output2(score2 = "3.5")
}
