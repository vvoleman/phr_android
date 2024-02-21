package cz.vvoleman.phr.featureMedicalRecord.ui.component.adjustableImageVIew.extension

import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.RectF
import cz.vvoleman.phr.featureMedicalRecord.ui.component.adjustableImageVIew.model.Line
import cz.vvoleman.phr.featureMedicalRecord.ui.component.adjustableImageVIew.model.Quad
import cz.vvoleman.phr.featureMedicalRecord.ui.component.adjustableImageVIew.model.QuadCorner

fun Canvas.drawQuad(
    quad: Quad,
    pointRadius: Float,
    cropperLinesAndCornersStyles: Paint,
    cropperSelectedCornerFillStyles: Paint,
    selectedCorner: QuadCorner?,
    imagePreviewBounds: RectF,
    ratio: Float,
    selectedCornerRadiusMagnification: Float,
    selectedCornerBackgroundMagnification: Float,
) {
    // draw 4 corner points
    for ((quadCorner: QuadCorner, cornerPoint: PointF) in quad.corners) {
        var circleRadius = pointRadius

        if (quadCorner === selectedCorner) {
            // the cropper corner circle grows when you touch and drag it
            circleRadius = selectedCornerRadiusMagnification * pointRadius
            val matrix = Matrix()
            matrix.postScale(ratio, ratio, ratio / cornerPoint.x, ratio / cornerPoint.y)
            matrix.postTranslate(imagePreviewBounds.left, imagePreviewBounds.top)
            matrix.postScale(
                selectedCornerBackgroundMagnification,
                selectedCornerBackgroundMagnification,
                cornerPoint.x,
                cornerPoint.y
            )
            cropperSelectedCornerFillStyles.shader.setLocalMatrix(matrix)
            // fill selected corner circle with magnified image, so it's easier to crop
            drawCircle(cornerPoint.x, cornerPoint.y, circleRadius, cropperSelectedCornerFillStyles)
        }

        // draw corner circles
        drawCircle(
            cornerPoint.x,
            cornerPoint.y,
            circleRadius,
            cropperLinesAndCornersStyles
        )
    }

    // draw 4 connecting lines
    for (edge: Line in quad.edges) {
        drawLine(edge.from.x, edge.from.y, edge.to.x, edge.to.y, cropperLinesAndCornersStyles)
    }
}
