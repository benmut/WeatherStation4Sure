package com.mutondo.weatherstation4sure.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberUpdatedMarkerState
import com.mutondo.weatherstation4sure.navigation.Navigator

@Composable
fun MapScreen(
    navigator: Navigator
) {
    val currentLocation = LatLng(-26.204103, 28.047304) // Johannesburg
    var marker by remember { mutableStateOf(currentLocation) }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(currentLocation, 10f)
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        onMapLongClick = { marker = it }
    ) {
        Marker(
            state = rememberUpdatedMarkerState(position = marker),
            title = "You are here",
            snippet = "${marker.latitude}; ${marker.longitude}",
            onInfoWindowLongClick = {
                navigator.navigateToDaysForecast("${marker.latitude}", "${marker.longitude}")
            }
        )
    }
}
