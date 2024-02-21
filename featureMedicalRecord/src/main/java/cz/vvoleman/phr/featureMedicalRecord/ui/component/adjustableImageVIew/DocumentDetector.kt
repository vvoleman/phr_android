package cz.vvoleman.phr.featureMedicalRecord.ui.component.adjustableImageVIew

import android.graphics.Bitmap
import android.graphics.PointF
import android.util.Log
import cz.vvoleman.phr.featureMedicalRecord.ui.component.adjustableImageVIew.extension.toPointF
import org.opencv.android.Utils
import org.opencv.core.Core
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.core.MatOfPoint
import org.opencv.core.Point
import org.opencv.core.Scalar
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc

class DocumentDetector {

    fun detectCorners(image: Bitmap): List<PointF> {
        val mat = Mat()
        Utils.bitmapToMat(image, mat)

        val shrunkImageHeight = 500.0
        Imgproc.resize(
            mat,
            mat,
            Size(
                shrunkImageHeight * image.width / image.height,
                shrunkImageHeight
            )
        )

        // convert photo to LUV colorspace to avoid glares caused by lights
        Imgproc.cvtColor(mat, mat, Imgproc.COLOR_BGR2Luv)

        // separate photo into 3 parts, (L, U, and V)
        val imageSplitByColorChannel: MutableList<Mat> = mutableListOf()
        Core.split(mat, imageSplitByColorChannel)
        // Remove second channel
        imageSplitByColorChannel.removeAt(2)
        imageSplitByColorChannel.removeAt(1)
        Log.d("AdjustableImageView", "here")

        val contours = imageSplitByColorChannel
            .mapNotNull { getContours(it) }

        val maskL = Mat.zeros(mat.size(), CvType.CV_8U)
        Imgproc.fillPoly(maskL, listOf(contours[0]), Scalar(255.0))

//        val maskV = Mat.zeros(mat.size(), CvType.CV_8U)
//        Imgproc.fillPoly(maskV, listOf(contours[1]), Scalar(255.0))

        val masks = contours.map {
            Mat.zeros(mat.size(), CvType.CV_8U)
        }
            .map {
                // fill poly
                Imgproc.fillPoly(it, contours, Scalar(255.0))
                it
            }

        // From masks, create corner points
        val corners = masks.mapNotNull { mask ->
            val corner = MatOfPoint()
            Imgproc.goodFeaturesToTrack(
                mask,
                corner,
                4,
                0.01,
                10.0
            )
            corner
        }.map {
            Point(
                it.toList()[0].x,
                it.toList()[0].y
            )
        }

        return corners.map {
            it.toPointF()
        }
    }

    private fun getContours(image: Mat): MatOfPoint? {
        val outputImage = Mat()

        // blur image to help remove noise
        Imgproc.GaussianBlur(image, outputImage, Size(5.0, 5.0), 0.0)

        // convert all pixels to either black or white (document should be black after this), but
        // there might be other parts of the photo that turn black
//        Imgproc.threshold(
//            outputImage,
//            outputImage,
//            0.0,
//            255.0,
//            Imgproc.THRESH_BINARY + Imgproc.THRESH_OTSU
//        )
        Imgproc.threshold(
            image,
            outputImage,
            125.0,
            255.0,
            Imgproc.THRESH_BINARY
        )

        // detect the document's border using the Canny edge detection algorithm
        Imgproc.Canny(outputImage, outputImage, 50.0, 200.0)

        // the detect edges might have gaps, so try to close those
        Imgproc.morphologyEx(
            outputImage,
            outputImage,
            Imgproc.MORPH_CLOSE,
            Mat.ones(Size(5.0, 5.0), CvType.CV_8U)
        )

        // get outline of document edges, and outlines of other shapes in photo
        val contours: MutableList<MatOfPoint> = mutableListOf()
        Imgproc.findContours(
            outputImage,
            contours,
            Mat(),
            Imgproc.RETR_LIST,
            Imgproc.CHAIN_APPROX_SIMPLE
        )

        // Return max of contours
        return contours.maxByOrNull { Imgproc.contourArea(it) }
    }

}
