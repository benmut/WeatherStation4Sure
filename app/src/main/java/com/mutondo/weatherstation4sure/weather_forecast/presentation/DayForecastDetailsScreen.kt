package com.mutondo.weatherstation4sure.weather_forecast.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import com.mutondo.weatherstation4sure.R
import com.mutondo.weatherstation4sure.component.ForecastDetailsRow
import com.mutondo.weatherstation4sure.component.ScreenTopAppBar
import com.mutondo.weatherstation4sure.navigation.DAY_INDEX_KEY
import com.mutondo.weatherstation4sure.navigation.Navigator

@Composable
fun DayForecastDetailsScreen(
    navigator: Navigator,
    navBackStackEntry: NavBackStackEntry,
    viewModel: WeatherForecastViewModel
) {
    val index = navBackStackEntry.arguments?.getInt(DAY_INDEX_KEY) ?: 0

    Scaffold(
        topBar = {
            ScreenTopAppBar(
                topAppBarTitle = String(),
                onNavigateUp = { navigator.navigateUp() },
            )
        },
        containerColor = Color.White

    ) { contentPadding ->
        DayForecastDetailsContent(
            modifier = Modifier.padding(top = contentPadding.calculateTopPadding()),
            uiState = viewModel.uiState.value,
            index = index
        )
    }
}

@Composable
fun DayForecastDetailsContent(
    modifier: Modifier,
    uiState: WeatherForecastUiState,
    index: Int
) {
    val selectedForecast = uiState.forecasts[index]

    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(top = dimensionResource(R.dimen.margin_huge)),
            text = selectedForecast.city,
            style = TextStyle(
                color = Color(0xFF5D5D5D),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
        )

        Text(
            text = selectedForecast.temperature,
            style = TextStyle(
                color = Color(0xFF5D5D5D),
                fontSize = 75.sp,
                fontWeight = FontWeight.Bold
            )
        )

        Text(
            text = selectedForecast.description,
            color = Color(0xFF5D5D5D),
            fontSize = 25.sp
        )

        HorizontalDivider(
            modifier = Modifier.padding(top = dimensionResource(R.dimen.margin_huge)),
            thickness = 1.dp,
            color = Color(0xFFD3D3D3)
        )

        ForecastDetailsRow(
            upperText1 = stringResource(R.string.min),
            lowerText1 = selectedForecast.tempMin,
            upperText2 = stringResource(R.string.max),
            lowerText2 = selectedForecast.tempMax
        )

        ForecastDetailsRow(
            upperText1 = stringResource(R.string.sunrise),
            lowerText1 = selectedForecast.sunrise,
            upperText2 = stringResource(R.string.sunset),
            lowerText2 = selectedForecast.sunset
        )

        ForecastDetailsRow(
            upperText1 = stringResource(R.string.humidity),
            lowerText1 = "${selectedForecast.humidity}%",
            upperText2 = stringResource(R.string.pressure),
            lowerText2 = "${selectedForecast.pressure} hPa"
        )

        ForecastDetailsRow(
            upperText1 = stringResource(R.string.visibility),
            lowerText1 = "${selectedForecast.visibility} Km"
        )
    }
}
