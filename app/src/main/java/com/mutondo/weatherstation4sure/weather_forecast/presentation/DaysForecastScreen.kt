package com.mutondo.weatherstation4sure.weather_forecast.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import coil.compose.AsyncImage
import com.mutondo.weatherstation4sure.BuildConfig
import com.mutondo.weatherstation4sure.R
import com.mutondo.weatherstation4sure.common.presentation.PreferencesDataStoreEvent
import com.mutondo.weatherstation4sure.common.presentation.PreferencesDataStoreViewModel
import com.mutondo.weatherstation4sure.component.LoadingIndicator
import com.mutondo.weatherstation4sure.component.ScreenTopAppBar
import com.mutondo.weatherstation4sure.component.TemperatureDialog
import com.mutondo.weatherstation4sure.navigation.LATITUDE_KEY
import com.mutondo.weatherstation4sure.navigation.LONGITUDE_KEY
import com.mutondo.weatherstation4sure.navigation.Navigator
import com.mutondo.weatherstation4sure.utils.AppUtils.Companion.convertKelvinToCelsius
import com.mutondo.weatherstation4sure.utils.AppUtils.Companion.convertKelvinToFahrenheit
import com.mutondo.weatherstation4sure.utils.Constants.CELSIUS
import com.mutondo.weatherstation4sure.weather_forecast.domain.model.WeatherForecast

const val ICON_ROW_ID = "icon row id"

@Composable
fun DaysForecastScreen(
    navigator: Navigator,
    navBackStackEntry: NavBackStackEntry,
    viewModel: WeatherForecastViewModel,
    prefsViewModel: PreferencesDataStoreViewModel = hiltViewModel()
) {
    val latitude = navBackStackEntry.arguments?.getString(LATITUDE_KEY) ?: ""
    val longitude = navBackStackEntry.arguments?.getString(LONGITUDE_KEY) ?: ""

    var showDialog by remember { mutableStateOf(false) }

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
                onClick = { showDialog = true }
            )
        },
        containerColor = Color.White

    ) { contentPadding ->
        DaysForecastScreenContent(
            modifier = Modifier.padding(top = contentPadding.calculateTopPadding()),
            navigator = navigator,
            uiState = viewModel.uiState.value,
            showDialog = showDialog,
            onDismissDialog = { showDialog = false },
            onConfirmation = {
                showDialog = false

                prefsViewModel.onEvent(
                    PreferencesDataStoreEvent.UpdateShowCelsius(
                        showCelsius = it == CELSIUS
                    )
                )
            }
        )
    }
}

@Composable
fun DaysForecastScreenContent(
    modifier: Modifier,
    navigator: Navigator,
    uiState: WeatherForecastUiState,
    showDialog: Boolean,
    onDismissDialog: () -> Unit,
    onConfirmation: (String) -> Unit
) {
    if (showDialog) {
        TemperatureDialog(
            onDismissRequest = onDismissDialog,
            onConfirmation = onConfirmation
        )
    }

    if (uiState.isLoading) {
        LoadingIndicator()
    }

    ForecastList(
        modifier = modifier.fillMaxSize(),
        forecasts = uiState.forecasts,
        onDaySelected = {
            navigator.navigateToDayForecastDetails(it)
        }
    )
}

@Composable
fun ForecastList(
    modifier: Modifier = Modifier,
    forecasts: List<WeatherForecast>,
    onDaySelected: (Int) -> Unit
) {
    LazyColumn(
        modifier = modifier
    ) {
        itemsIndexed(forecasts) { index, forecast ->
            ForecastItem(
                index = index,
                day = forecast.dayOfWeek,
                icon = forecast.icon,
                temperature = forecast.temperature,
                onDaySelected = onDaySelected,
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
    index: Int,
    day: String,
    icon: String,
    temperature: Float,
    onDaySelected: (Int) -> Unit,
    prefsViewModel: PreferencesDataStoreViewModel = hiltViewModel()
) {
    val constraints = iconConstraints()
    val preferences = prefsViewModel.readPreferencesFromDataStore.collectAsState(null).value

    ConstraintLayout(
        modifier = Modifier.fillMaxWidth(),
        constraintSet = constraints
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = dimensionResource(R.dimen.margin_default),
                    vertical = dimensionResource(R.dimen.margin_tiny)
                )
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    onDaySelected(index)
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = day,
                color = Color(0xFF5D5D5D)
            )

            Text(
                text = if (preferences?.showCelsius == true) {
                    convertKelvinToCelsius(temperature)
                } else {
                    convertKelvinToFahrenheit(temperature)
                },
                color = Color(0xFF5D5D5D)
            )
        }

        AsyncImage(
            modifier = Modifier
                .size(48.dp)
                .layoutId(ICON_ROW_ID),
            model = "http://openweathermap.org/img/wn/$icon@2x.png",
            contentDescription = null
        )
    }
}

private fun iconConstraints(): ConstraintSet {
    return ConstraintSet {
        val titleRowRef = createRefFor(ICON_ROW_ID)

        constrain(titleRowRef) {
            start.linkTo(parent.start, margin = 0.dp)
            top.linkTo(parent.top, margin = 0.dp)
            end.linkTo(parent.end, margin = 0.dp)
            bottom.linkTo(parent.bottom, margin = 0.dp)
        }
    }
}
