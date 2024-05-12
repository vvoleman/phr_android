package cz.vvoleman.phr.featureMeasurement.ui.utils.detail

import android.graphics.Color
import android.graphics.Typeface
import com.patrykandpatrick.vico.core.cartesian.axis.AxisPosition
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.marker.CartesianMarker
import com.patrykandpatrick.vico.core.cartesian.marker.DefaultCartesianMarker
import com.patrykandpatrick.vico.core.common.Dimensions
import com.patrykandpatrick.vico.core.common.component.Component
import com.patrykandpatrick.vico.core.common.component.LineComponent
import com.patrykandpatrick.vico.core.common.component.ShapeComponent
import com.patrykandpatrick.vico.core.common.component.TextComponent
import com.patrykandpatrick.vico.core.common.shape.Corner
import com.patrykandpatrick.vico.core.common.shape.DashedShape
import com.patrykandpatrick.vico.core.common.shape.MarkerCorneredShape

fun createGuideline(
    stroke: Float = 0.1f,
    margins: Dimensions = Dimensions(0f, 0f, 0f, 0f),
    color: Int = Color.BLACK,
): LineComponent {
    return LineComponent(
        color = color,
        thicknessDp = 0.1f,
        shape = DashedShape(),
        margins = margins,
        strokeWidthDp = stroke,
        strokeColor = color,
    )
}

fun createTextComponent(
    textSizeSp: Float = 14f,
    background: Component? = null,
    margins: Dimensions = Dimensions(0f, 0f, 0f, 0f),
    typeface: Typeface = Typeface.MONOSPACE,
    padding: Float = 0f,
): TextComponent {
    return TextComponent.Builder().apply {
        this.textSizeSp = textSizeSp
        this.margins = margins
        this.typeface = typeface
        this.padding = Dimensions(padding, padding, padding, padding)
        this.background = background
    }.build()
}

fun createMarker(
    indicatorSizeDp: Float = 0f,
    color: Int = Color.BLACK,
): CartesianMarker {
    val background = ShapeComponent(MarkerCorneredShape(Corner.FullyRounded), Color.WHITE)
        .setShadow(
            radius = 4f,
            dy = 2f,
            applyElevationOverlay = true,
        )
    return DefaultCartesianMarker(
        label = createTextComponent(padding = 5f, background = background),
        indicator = null,
        guideline = createGuideline(0.5f, color = color),
        labelPosition = DefaultCartesianMarker.LabelPosition.Top
    ).apply {
        this.indicatorSizeDp = indicatorSizeDp
    }
}

inline fun <reified T : AxisPosition.Vertical> createVerticalAxis(
    title: String = "",
    typeface: Typeface = Typeface.MONOSPACE,
    titleMargins: Dimensions = Dimensions(0f, 0f, 0f, 0f),
    labelMargins: Dimensions = Dimensions(0f, 0f, 0f, 0f),
    valueFormatter: CartesianValueFormatter? = null,
): VerticalAxis<T> {
    return VerticalAxis.build {
        this.title = title
        this.titleComponent = createTextComponent(typeface = typeface, margins = titleMargins)
        this.label = createTextComponent(margins = labelMargins)
        if (valueFormatter != null) {
            this.valueFormatter = valueFormatter
        }
        this.guideline = createGuideline()
        horizontalLabelPosition = VerticalAxis.HorizontalLabelPosition.Outside
    }
}

inline fun <reified T : AxisPosition.Horizontal> createHorizontalAxis(
    title: String = "",
    typeface: Typeface = Typeface.MONOSPACE,
    valueFormatter: CartesianValueFormatter? = null,
): HorizontalAxis<T> {
    return HorizontalAxis.build {
        this.title = title
        this.titleComponent = createTextComponent(textSizeSp = 14f, typeface = typeface)
        this.label = createTextComponent()

        if (valueFormatter != null) {
            this.valueFormatter = valueFormatter
        }

        this.guideline = createGuideline()
    }
}
