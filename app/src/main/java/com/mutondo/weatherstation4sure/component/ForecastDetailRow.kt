package com.mutondo.weatherstation4sure.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.sp
import com.mutondo.weatherstation4sure.R
import java.util.Locale

@Composable
fun ForecastDetailsRow(
    modifier: Modifier = Modifier,
    upperText1: String,
    lowerText1: String,
    upperText2: String = String(),
    lowerText2: String = String()
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Detail(
                modifier = modifier,
                upperText = upperText1,
                lowerText = lowerText1
            )

            Detail(
                modifier = Modifier.padding(start = 120.dp),
                upperText = upperText2,
                lowerText= lowerText2
            )
        }

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.margin_default)),
            thickness = 1.dp,
            color = Color(0xFFD3D3D3)
        )
    }
}

@Composable
fun Detail(
    modifier: Modifier = Modifier,
    upperText: String,
    lowerText: String
) {
    Column(
        modifier = modifier.padding(dimensionResource(R.dimen.margin_default))
    ) {
        Text(
            text = upperText.uppercase(Locale.getDefault()),
            color = Color(0xFF5D5D5D),
            fontSize = 16.sp
        )

        Text(
            text = lowerText,
            color = Color(0xFF5D5D5D),
            fontSize = 30.sp,
        )
    }
}
