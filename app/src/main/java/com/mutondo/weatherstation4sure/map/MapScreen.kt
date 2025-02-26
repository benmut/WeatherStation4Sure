package com.mutondo.weatherstation4sure.map

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberUpdatedMarkerState
import com.mutondo.weatherstation4sure.component.Dialog
import com.mutondo.weatherstation4sure.navigation.Navigator
import com.mutondo.weatherstation4sure.utils.AppUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun MapScreen(
    navigator: Navigator
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val locationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    val locationPermissions = arrayOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION)
    var currentLocation by remember { mutableStateOf<Location?>(null) }

    var showLoading by remember { mutableStateOf(false) }
    var showDialogForLocationPermission by remember { mutableStateOf(false) }

    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    val requestLocationPermission = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { result ->
        val isGranted = result.values.reduce { fine, coarse -> fine && coarse }
        if (isGranted) {
            scope.launch {
                showLoading = true
                currentLocation = getCurrentLocation(scope, locationClient)
                showLoading = false
            }
        } else {
            scope.launch {
                snackbarHostState.showSnackbar("Location currently disabled due to denied permission.")
            }
        }
    }

    if (showDialogForLocationPermission) {
        Dialog(
            dialogTitle = "Location Permission",
            dialogText = "The app would like access your location to display on the map",
            onConfirm = {
                requestLocationPermission.launch(locationPermissions)
                showDialogForLocationPermission = false
            },
            onDismiss = { showDialogForLocationPermission = false }
        )
    }

    if (!AppUtils.isGpsEnabled (locationManager)) {
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        context.startActivity(intent)
    }

    LaunchedEffect(Unit) {
        when {
            locationPermissions.all { permission ->
                ContextCompat.checkSelfPermission(
                    context,
                    permission
                ) == PackageManager.PERMISSION_GRANTED
            } -> {
                showLoading = true
                currentLocation = getCurrentLocation(scope, locationClient)
                showLoading = false
            }

            locationPermissions.all { permission ->
                ActivityCompat.shouldShowRequestPermissionRationale(
                    context as Activity,
                    permission
                )
            } -> {
                showDialogForLocationPermission = true
            }

            else -> {
                requestLocationPermission.launch(locationPermissions)
            }
        }
    }

    Scaffold { contentPadding ->
        MapScreenContent(
            modifier = Modifier.padding(vertical = contentPadding.calculateTopPadding()),
            navigator = navigator,
            showLoading = showLoading,
            currentLocation = currentLocation
        )
    }
}

@Composable
fun MapScreenContent(
    modifier: Modifier,
    navigator: Navigator,
    showLoading: Boolean,
    currentLocation: Location?
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (currentLocation != null) {
            val scope = rememberCoroutineScope()
            val currentLatLng = LatLng(currentLocation.latitude, currentLocation.longitude)

            val cameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(currentLatLng, 10f)
            }

            var marker by remember { mutableStateOf<LatLng?>(null) }

            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                properties = MapProperties(
                    isMyLocationEnabled = true
                ),
                onMapLongClick = { marker = it }
            ) {
                marker?.let {
                    Marker(
                        state = rememberUpdatedMarkerState(position = it),
                        title = "You are here",
                        snippet = "${it.latitude}; ${it.longitude}",
                        onInfoWindowLongClick = { _ ->
                            scope.launch {
                                navigator.navigateToDaysForecast("${it.latitude}", "${it.longitude}")
                            }
                        }
                    )
                }
            }
        }

        if (showLoading) {
            CircularProgressIndicator(
                modifier = Modifier.width(48.dp)
            )
        }
    }
}

@RequiresPermission(
    anyOf = [ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION]
)
suspend fun getCurrentLocation(scope: CoroutineScope, locationClient: FusedLocationProviderClient): Location? {
    return scope.async {
        locationClient.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            CancellationTokenSource().token
        ).await()
    }.await()
}
