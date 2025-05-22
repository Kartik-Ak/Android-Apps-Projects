package com.example.spamdetector

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.textfiled.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Output1(score1: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.s2),
            contentDescription = "Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 150.dp),
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
                value = score1,
                onValueChange = {},
                label = { Text("Spam Score", color = Color.White) },
                readOnly = true,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    containerColor = Color.Transparent,
                    unfocusedBorderColor = Color.Red,
                    unfocusedLabelColor = Color.White,
                    focusedLabelColor = Color.White
                )
            )


        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun OutputPreview() {
    Output1(score1 = "6.7")

}
