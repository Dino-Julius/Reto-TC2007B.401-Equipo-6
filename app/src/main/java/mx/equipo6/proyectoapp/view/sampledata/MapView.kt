package mx.equipo6.proyectoapp.view.sampledata

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker


class MapManager(private val context: Context) {
    val mapView: MapView = MapView(context).apply {
        setTileSource(TileSourceFactory.MAPNIK)
        setMultiTouchControls(true)
        setBuiltInZoomControls(true)
    }

    private val mapController = mapView.controller

    init {
        mapController.setZoom(13.3)
    }

    fun addMarker(latitude: Double, longitude: Double, title: String, snippet: String) {
        val point = GeoPoint(latitude, longitude)
        mapController.setCenter(point)

        val marker = Marker(mapView).apply {
            position = point
            setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            this.title = title
            this.snippet = snippet
        }
        mapView.overlays.add(marker)
    }

    fun onResume() = mapView.onResume()
    fun onPause() = mapView.onPause()
    fun onDetach() = mapView.onDetach()
}

@Composable
fun OSMDroidMapView() {
    val context = LocalContext.current
    val mapManager = remember { MapManager(context) }
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val lifecycleObserver = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> mapManager.onResume()
                Lifecycle.Event.ON_STOP -> mapManager.onPause()
                Lifecycle.Event.ON_DESTROY -> mapManager.onDetach()
                else -> {}
            }
        }

        lifecycleOwner.lifecycle.addObserver(lifecycleObserver)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(lifecycleObserver)
        }
    }

    Box(modifier = Modifier.height(300.dp).fillMaxWidth()) {
        AndroidView(
            factory = { mapManager.mapView },
            update = {
                mapManager.addMarker(19.52942213451882, -99.23029932013507, "Marker ZAZIL", "ZAZIL")
            }
        )
    }
}