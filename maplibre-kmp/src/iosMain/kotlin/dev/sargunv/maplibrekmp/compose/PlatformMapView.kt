package dev.sargunv.maplibrekmp.compose

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.viewinterop.UIKitInteropInteractionMode
import androidx.compose.ui.viewinterop.UIKitInteropProperties
import androidx.compose.ui.viewinterop.UIKitView
import cocoapods.MapLibre.MLNMapView
import cocoapods.MapLibre.MLNMapViewDelegateProtocol
import cocoapods.MapLibre.MLNStyle
import dev.sargunv.maplibrekmp.core.PlatformMap
import dev.sargunv.maplibrekmp.core.Style
import io.github.dellisd.spatialk.geojson.Position
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.CValue
import kotlinx.cinterop.ObjCAction
import kotlinx.cinterop.useContents
import platform.CoreGraphics.CGPoint
import platform.CoreGraphics.CGPointMake
import platform.CoreGraphics.CGSizeMake
import platform.Foundation.NSURL
import platform.UIKit.UIGestureRecognizerStateBegan
import platform.UIKit.UIGestureRecognizerStateEnded
import platform.UIKit.UILongPressGestureRecognizer
import platform.UIKit.UITapGestureRecognizer
import platform.darwin.NSObject
import platform.darwin.sel_registerName

@Composable
internal actual fun PlatformMapView(
  modifier: Modifier,
  styleUrl: String,
  uiPadding: PaddingValues,
  updateMap: (map: PlatformMap) -> Unit,
  onMapLoaded: (map: PlatformMap) -> Unit,
  onStyleLoaded: (style: Style) -> Unit,
  onRelease: () -> Unit,
  onCameraMove: () -> Unit,
  onClick: (latLng: Position, xy: Pair<Float, Float>) -> Unit,
  onLongClick: (latLng: Position, xy: Pair<Float, Float>) -> Unit,
) {
  val layoutDir = LocalLayoutDirection.current
  val density = LocalDensity.current
  val insetPadding = WindowInsets.safeDrawing.asPaddingValues()

  val margins =
    remember(insetPadding, uiPadding, layoutDir) {
      val leftSafeInset = insetPadding.calculateLeftPadding(layoutDir).value
      val rightSafeInset = insetPadding.calculateRightPadding(layoutDir).value
      val bottomSafeInset = insetPadding.calculateBottomPadding().value
      val leftUiPadding = uiPadding.calculateLeftPadding(layoutDir).value - leftSafeInset
      val rightUiPadding = uiPadding.calculateRightPadding(layoutDir).value - rightSafeInset
      val bottomUiPadding = uiPadding.calculateBottomPadding().value - bottomSafeInset
      listOf(
        CGPointMake(leftUiPadding.toDouble(), bottomUiPadding.toDouble()),
        CGPointMake(rightUiPadding.toDouble(), bottomUiPadding.toDouble()),
      )
    }

  var lastStyleUrl by remember { mutableStateOf<String?>(null) }

  val currentOnStyleLoaded by rememberUpdatedState(onStyleLoaded)
  val currentOnRelease by rememberUpdatedState(onRelease)
  val currentOnCameraMove by rememberUpdatedState(onCameraMove)
  val currentOnClick by rememberUpdatedState(onClick)
  val currentOnLongClick by rememberUpdatedState(onLongClick)

  // We shouldn't need to hold on to this, but for some reason it stops working if we don't.
  // Probably some weirdness with integration between Kotlin GC and ObjC ARC.
  var mapDelegate by remember { mutableStateOf<MapDelegate?>(null) }
  var mapGestureManager by remember { mutableStateOf<MapGestureManager?>(null) }

  var platformMap by remember { mutableStateOf<PlatformMap?>(null) }
  SideEffect { platformMap?.layoutDirection = layoutDir }

  var calledOnMapLoaded by remember { mutableStateOf(false) }

  UIKitView(
    modifier =
      modifier.fillMaxSize().onSizeChanged {
        platformMap?.mapViewSize =
          with(density) { it.toSize().toDpSize() }
            .let { dpSize ->
              CGSizeMake(dpSize.width.value.toDouble(), dpSize.height.value.toDouble())
            }
        if (!calledOnMapLoaded) {
          onMapLoaded(platformMap!!)
          calledOnMapLoaded = true
        }
      },
    properties =
      UIKitInteropProperties(interactionMode = UIKitInteropInteractionMode.NonCooperative),
    factory = {
      MLNMapView().also { mapView ->
        mapView.automaticallyAdjustsContentInset = false

        platformMap = PlatformMap(mapView)
        calledOnMapLoaded = false

        mapDelegate =
          MapDelegate(
            mapView = mapView,
            onStyleLoaded = { currentOnStyleLoaded(Style(it)) },
            onCameraMove = { currentOnCameraMove() },
          )

        mapGestureManager =
          MapGestureManager(
            mapView = mapView,
            onClick = { point ->
              currentOnClick(
                mapView.convertPoint(point, toCoordinateFromView = null).useContents {
                  Position(latitude, longitude)
                },
                point.useContents { Pair(x.toFloat(), y.toFloat()) },
              )
            },
            onLongClick = { point ->
              currentOnLongClick(
                mapView.convertPoint(point, toCoordinateFromView = null).useContents {
                  Position(latitude, longitude)
                },
                point.useContents { Pair(x.toFloat(), y.toFloat()) },
              )
            },
          )
      }
    },
    update = { mapView ->
      platformMap!!.layoutDirection = layoutDir
      updateMap(platformMap!!)

      mapView.setLogoViewMargins(margins[0])
      mapView.setAttributionButtonMargins(margins[1])

      if (styleUrl != lastStyleUrl) {
        mapView.setStyleURL(NSURL(string = styleUrl))
        lastStyleUrl = styleUrl
      }
    },
    onRelease = {
      mapGestureManager = null
      currentOnRelease()
    },
  )
}

internal class MapDelegate(
  mapView: MLNMapView,
  private val onStyleLoaded: (style: MLNStyle) -> Unit,
  private val onCameraMove: () -> Unit,
) : NSObject(), MLNMapViewDelegateProtocol {
  init {
    mapView.delegate = this
  }

  override fun mapView(mapView: MLNMapView, didFinishLoadingStyle: MLNStyle) =
    onStyleLoaded(didFinishLoadingStyle)

  override fun mapViewRegionIsChanging(mapView: MLNMapView) = onCameraMove()
}

@OptIn(BetaInteropApi::class)
internal class MapGestureManager(
  private val mapView: MLNMapView,
  private val onClick: (point: CValue<CGPoint>) -> Unit,
  private val onLongClick: (pos: CValue<CGPoint>) -> Unit,
) : NSObject() {
  init {
    val singleTap = UITapGestureRecognizer(this, sel_registerName(::handleTap.name + ":"))
    val longPress =
      UILongPressGestureRecognizer(this, sel_registerName(::handleLongPress.name + ":"))

    mapView.gestureRecognizers!!
      .filterIsInstance<UITapGestureRecognizer>()
      .forEach(singleTap::requireGestureRecognizerToFail)
    mapView.gestureRecognizers!!
      .filterIsInstance<UILongPressGestureRecognizer>()
      .forEach(longPress::requireGestureRecognizerToFail)

    listOf(singleTap, longPress).forEach(mapView::addGestureRecognizer)
  }

  @ObjCAction
  fun handleTap(sender: UITapGestureRecognizer) {
    if (sender.state != UIGestureRecognizerStateEnded) return
    onClick(sender.locationInView(mapView))
  }

  @ObjCAction
  fun handleLongPress(sender: UILongPressGestureRecognizer) {
    if (sender.state != UIGestureRecognizerStateBegan) return
    onLongClick(sender.locationInView(mapView))
  }
}
