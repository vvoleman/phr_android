package cz.vvoleman.phr.featureMeasurement.ui.utils.detail

import android.graphics.Color
import android.graphics.Typeface
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import com.patrykandpatrick.vico.core.axis.horizontal.HorizontalAxis
import com.patrykandpatrick.vico.core.axis.horizontal.createHorizontalAxis
import com.patrykandpatrick.vico.core.axis.vertical.VerticalAxis
import com.patrykandpatrick.vico.core.axis.vertical.createVerticalAxis
import com.patrykandpatrick.vico.core.chart.values.ChartValues
import com.patrykandpatrick.vico.core.component.Component
import com.patrykandpatrick.vico.core.component.marker.MarkerComponent
import com.patrykandpatrick.vico.core.component.shape.DashedShape
import com.patrykandpatrick.vico.core.component.shape.LineComponent
import com.patrykandpatrick.vico.core.component.shape.ShapeComponent
import com.patrykandpatrick.vico.core.component.shape.cornered.Corner
import com.patrykandpatrick.vico.core.component.shape.cornered.MarkerCorneredShape
import com.patrykandpatrick.vico.core.component.text.TextComponent
import com.patrykandpatrick.vico.core.dimensions.Dimensions
import com.patrykandpatrick.vico.core.dimensions.MutableDimensions
import com.patrykandpatrick.vico.core.marker.DefaultMarkerLabelFormatter
import com.patrykandpatrick.vico.core.marker.Marker
import com.patrykandpatrick.vico.core.marker.MarkerLabelFormatter
import com.patrykandpatrick.vico.views.dimensions.dimensionsOf
import cz.vvoleman.phr.featureMeasurement.ui.utils.detail.createHorizontalAxis as createHorizontalAxis1

fun createGuideline(
    stroke: Float = 0.1f,
    margins: Dimensions = dimensionsOf(0f, 0f, 0f, 0f),
): LineComponent {
    return LineComponent(
        color = Color.BLACK,
        thicknessDp = 0.1f,
        shape = DashedShape(),
        margins = margins,
        strokeWidthDp = stroke,
        strokeColor = 0xff000000.toInt(),
    )
}

fun createTextComponent(
    textSizeSp: Float = 14f,
    background: Component? = null,
    margins: MutableDimensions = dimensionsOf(0f, 0f, 0f, 0f),
    typeface: Typeface = Typeface.MONOSPACE,
    padding: Float = 0f,
): TextComponent {
    return TextComponent.Builder().apply {
        this.textSizeSp = textSizeSp
        this.margins = margins
        this.typeface = typeface
        this.padding = dimensionsOf(padding, padding, padding, padding)
        this.background = background
    }.build()
}

fun createMarker(
    indicatorSizeDp: Float = 0f,
    labelFormatter: MarkerLabelFormatter = DefaultMarkerLabelFormatter(),
): Marker {
    val background = ShapeComponent(MarkerCorneredShape(Corner.FullyRounded), Color.WHITE)
        .setShadow(
            radius = 4f,
            dy = 2f,
            applyElevationOverlay = true,
        )
    return MarkerComponent(
        label = createTextComponent(padding = 10f, background = background),
        indicator = null,
        guideline = createGuideline(1f),
    ).apply {
        this.indicatorSizeDp = indicatorSizeDp
        this.labelFormatter = labelFormatter
    }
}

inline fun <reified T : AxisPosition.Vertical> createVerticalAxis(
    title: String = "",
    typeface: Typeface = Typeface.MONOSPACE,
    titleMargins: MutableDimensions = dimensionsOf(0f, 0f, 0f, 0f),
    labelMargins: MutableDimensions = dimensionsOf(0f, 0f, 0f, 0f),
    noinline valueFormatter: ((Float, ChartValues) -> String)? = null,
): VerticalAxis<T> {

    return createVerticalAxis {
        this.title = title
        this.titleComponent = createTextComponent(typeface = typeface, margins = titleMargins)
        this.label = createTextComponent(margins = labelMargins)
        if (valueFormatter != null) {
            this.valueFormatter = AxisValueFormatter(valueFormatter)
        }
        this.guideline = createGuideline()
    }
}

inline fun <reified T : AxisPosition.Horizontal> createHorizontalAxis(
    title: String = "",
    typeface: Typeface = Typeface.MONOSPACE,
    noinline valueFormatter: ((Float, ChartValues) -> String)? = null,
): HorizontalAxis<T> {

    return createHorizontalAxis {
        this.title = title
        this.titleComponent = createTextComponent(textSizeSp = 14f, typeface = typeface)
        this.label = createTextComponent()

        if (valueFormatter != null) {
            this.valueFormatter = AxisValueFormatter(valueFormatter)
        }

        this.guideline = createGuideline()
    }
}
