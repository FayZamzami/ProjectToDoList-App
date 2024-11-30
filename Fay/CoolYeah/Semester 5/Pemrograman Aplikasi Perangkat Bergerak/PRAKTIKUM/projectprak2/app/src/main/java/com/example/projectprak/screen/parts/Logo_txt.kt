package com.example.projectprak.screen.parts

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.projectprak.R

@Composable
fun Logo_txt() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top // Align items to the top of the Logo column
    ) {
        Image(
            painter = painterResource(id = R.drawable.to_do_list_nobg),
            contentDescription = "Image Judul",
            modifier = Modifier.size(240.dp)
        )
        Text(
            text = "Stay Organized\n" +
                    "Achieve More Every Day!",
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}



