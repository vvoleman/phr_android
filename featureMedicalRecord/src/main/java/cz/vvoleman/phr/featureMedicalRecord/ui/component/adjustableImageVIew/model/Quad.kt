package cz.vvoleman.phr.featureMedicalRecord.ui.component.adjustableImageVIew.model

import android.graphics.PointF
import android.graphics.RectF
import cz.vvoleman.phr.featureMedicalRecord.ui.component.adjustableImageVIew.extension.distance
import cz.vvoleman.phr.featureMedicalRecord.ui.component.adjustableImageVIew.extension.move
import cz.vvoleman.phr.featureMedicalRecord.ui.component.adjustableImageVIew.extension.multiply
import cz.vvoleman.phr.featureMedicalRecord.ui.component.adjustableImageVIew.extension.toPointF
import org.opencv.core.Point

data class Quad(
    val topLeft: PointF,
    val topRight: PointF,
    val bottomLeft: PointF,
    val bottomRight: PointF
) {
    constructor(
        topLeftCorner: Point,
        topRightCorner: Point,
        bottomRightCorner: Point,
        bottomLeftCorner: Point
    ) : this(
        topLeftCorner.toPointF(),
        topRightCorner.toPointF(),
        bottomRightCorner.toPointF(),
        bottomLeftCorner.toPointF()
    )

    val corners: MutableMap<QuadCorner, PointF> = mutableMapOf(
        QuadCorner.TOP_LEFT to topLeft,
        QuadCorner.TOP_RIGHT to topRight,
        QuadCorner.BOTTOM_RIGHT to bottomRight,
        QuadCorner.BOTTOM_LEFT to bottomLeft
    )

    val edges: Array<Line> get() = arrayOf(
        Line(topLeft, topRight),
        Line(topRight, bottomRight),
        Line(bottomRight, bottomLeft),
        Line(bottomLeft, topLeft)
    )

    fun getCornerClosestToPoint(point: PointF): QuadCorner {
        return corners.minByOrNull { corner -> corner.value.distance(point) }?.key!!
    }

    /**
     * This moves a corner by (dx, dy)
     *
     * @param corner the corner that needs to be moved
     * @param dx the corner moves dx horizontally
     * @param dy the corner moves dy vertically
     */
    fun moveCorner(corner: QuadCorner, dx: Float, dy: Float) {
        corners[corner]?.offset(dx, dy)
    }

    /**
     * This maps original image coordinates to preview image coordinates. The original image
     * is probably larger than the preview image.
     *
     * @param imagePreviewBounds offset the point by the top left of imagePreviewBounds
     * @param ratio multiply the point by ratio
     * @return the 4 corners after mapping coordinates
     */
    fun mapOriginalToPreviewImageCoordinates(imagePreviewBounds: RectF, ratio: Float): Quad {
        return Quad(
            topLeft.multiply(ratio).move(
                imagePreviewBounds.left,
                imagePreviewBounds.top
            ),
            topRight.multiply(ratio).move(
                imagePreviewBounds.left,
                imagePreviewBounds.top
            ),
            bottomRight.multiply(ratio).move(
                imagePreviewBounds.left,
                imagePreviewBounds.top
            ),
            bottomLeft.multiply(ratio).move(
                imagePreviewBounds.left,
                imagePreviewBounds.top
            )
        )
    }

    /**
     * This maps preview image coordinates to original image coordinates. The original image
     * is probably larger than the preview image.
     *
     * @param imagePreviewBounds reverse offset the point by the top left of imagePreviewBounds
     * @param ratio divide the point by ratio
     * @return the 4 corners after mapping coordinates
     */
    fun mapPreviewToOriginalImageCoordinates(imagePreviewBounds: RectF, ratio: Float): Quad {
        return Quad(
            topLeft.move(
                -imagePreviewBounds.left,
                -imagePreviewBounds.top
            ).multiply(1 / ratio),
            topRight.move(
                -imagePreviewBounds.left,
                -imagePreviewBounds.top
            ).multiply(1 / ratio),
            bottomRight.move(
                -imagePreviewBounds.left,
                -imagePreviewBounds.top
            ).multiply(1 / ratio),
            bottomLeft.move(
                -imagePreviewBounds.left,
                -imagePreviewBounds.top
            ).multiply(1 / ratio)
        )
    }
}
