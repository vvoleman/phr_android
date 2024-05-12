package cz.vvoleman.phr.featureMeasurement.ui.utils.detail

import com.patrykandpatrick.vico.core.common.Dimensions
import com.patrykandpatrick.vico.core.common.DrawContext
import com.patrykandpatrick.vico.core.common.component.Component

/**
 * A [Component] composed out of two [Component]s, with one drawn over the other.
 * @property outer the outer (background) [Component].
 * @property inner the inner (foreground) [Component].
 * @property innerPaddingStartDp the start padding between the inner and outer components.
 * @property innerPaddingTopDp the top padding between the inner and outer components.
 * @property innerPaddingEndDp the end padding between the inner and outer components.
 * @property innerPaddingBottomDp the bottom padding between the inner and outer components.
 */
public class OverlayingComponent(
    public val outer: Component,
    public val inner: Component,
    public val innerPaddingStartDp: Float = 0f,
    public val innerPaddingTopDp: Float = 0f,
    public val innerPaddingEndDp: Float = 0f,
    public val innerPaddingBottomDp: Float = 0f,
    override val margins: Dimensions,
) : Component {

    override fun draw(
        context: DrawContext,
        left: Float,
        top: Float,
        right: Float,
        bottom: Float,
        opacity: Float,
    ): Unit =
        with(context) {
            val leftWithMargin = left + margins.startDp.pixels
            val topWithMargin = top + margins.topDp.pixels
            val rightWithMargin = right - margins.endDp.pixels
            val bottomWithMargin = bottom - margins.bottomDp.pixels

            outer.draw(context, leftWithMargin, topWithMargin, rightWithMargin, bottomWithMargin, opacity)
            inner.draw(context, leftWithMargin, topWithMargin, rightWithMargin, bottomWithMargin, opacity)
        }
}
