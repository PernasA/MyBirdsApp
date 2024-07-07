package com.example.mybirdsapp.views

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.mybirdsapp.R

@Composable
fun AboutUsPage() {
    OutlinedButton(
        modifier = Modifier.padding(top = 10.dp),
        onClick = {}
    ) {
        Text(stringResource(R.string.about_us))
    }
}