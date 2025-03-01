package com.mutondo.weatherstation4sure.component

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun LoadingIndicator(
    modifier: Modifier = Modifier
) {
    Dialog(
        onDismissRequest = {}
    ) {
        CircularProgressIndicator(
            modifier = modifier.size(40.dp)
        )
    }
}
