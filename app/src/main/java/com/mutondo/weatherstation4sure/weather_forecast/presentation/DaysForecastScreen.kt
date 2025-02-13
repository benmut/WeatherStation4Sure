package com.mutondo.weatherstation4sure.weather_forecast.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import com.mutondo.weatherstation4sure.BuildConfig
import com.mutondo.weatherstation4sure.R
import com.mutondo.weatherstation4sure.component.ScreenTopAppBar
import com.mutondo.weatherstation4sure.navigation.LATITUDE_KEY
import com.mutondo.weatherstation4sure.navigation.LONGITUDE_KEY
import com.mutondo.weatherstation4sure.navigation.Navigator
import com.mutondo.weatherstation4sure.weather_forecast.domain.model.WeatherForecast

@Composable
fun DaysForecastScreen(
    navigator: Navigator,
    navBackStackEntry: NavBackStackEntry,
    viewModel: WeatherForecastViewModel = hiltViewModel()
) {
    val latitude = navBackStackEntry.arguments?.getString(LATITUDE_KEY) ?: ""
    val longitude = navBackStackEntry.arguments?.getString(LONGITUDE_KEY) ?: ""

    LaunchedEffect(Unit) {
        viewModel.onEvent(
            WeatherForecastUiEvent.GetWeatherForecast(latitude, longitude, BuildConfig.OW_API_KEY)
        )
    }

    Scaffold(
        topBar = {
            ScreenTopAppBar(
                topAppBarTitle = stringResource(id = R.string.days_forecast_screen_title),
                onNavigateUp = { navigator.navigateUp() },
            )
        },
        containerColor = Color.White

    ) { contentPadding ->
        DaysForecastScreenContent(
            modifier = Modifier.padding(top = contentPadding.calculateTopPadding()),
            viewModel.uiState.value
        )
    }
}

@Composable
fun DaysForecastScreenContent(
    modifier: Modifier,
    uiState: WeatherForecastUiState,
) {
    ForecastList(
        modifier = modifier.fillMaxSize(),
        forecasts = uiState.forecasts
    )
}

@Composable
fun ForecastList(
    modifier: Modifier = Modifier,
    forecasts: List<WeatherForecast>
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(forecasts) { forecast ->
            ForecastItem(
                day = forecast.dayOfWeek,
                icon = "", // TODO
                temperature = forecast.temperature
            )

            HorizontalDivider(
                modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.margin_none)),
                thickness = 1.dp,
                color = Color(0xFFD3D3D3)
            )
        }
    }
}

@Composable
fun ForecastItem(
    day: String,
    icon: String,
    temperature: String
) {
    Row(
        modifier = Modifier
            .padding(dimensionResource(R.dimen.margin_default))
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {

            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = day,
            color = Color(0xFF5D5D5D)
        )

//        Icon() // TODO

        Text(
            modifier = Modifier.weight(1f),
            text = temperature,
            color = Color(0xFF5D5D5D)
        )
    }
}
