package cz.vvoleman.phr.featureMedicalRecord.ui.component.adjustableImageVIew.utils

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import cz.vvoleman.phr.featureMedicalRecord.ui.component.adjustableImageVIew.extension.distance
import cz.vvoleman.phr.featureMedicalRecord.ui.component.adjustableImageVIew.extension.toOpenCVPoint
import cz.vvoleman.phr.featureMedicalRecord.ui.component.adjustableImageVIew.model.Quad
import org.opencv.android.Utils
import org.opencv.core.Mat
import org.opencv.core.MatOfPoint2f
import org.opencv.core.Point
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc
import java.io.File
import kotlin.math.min

class ImageUtil {

    init {

    }

    private fun getImageMatrixFromFilePath(filePath: String, bitmap: Bitmap): Mat {
        //

        // read image as matrix using OpenCV
        val image: Mat = Mat()
        Utils.bitmapToMat(bitmap, image)

        // if OpenCV fails to read the image then it's empty
        if (!image.empty()) {
            // convert image to RGB color space since OpenCV reads it using BGR color space
            Imgproc.cvtColor(image, image, Imgproc.COLOR_BGR2RGB)
            return image
        }

        if (!File(filePath).exists()) {
            throw Exception("File doesn't exist - $filePath")
        }

        if (!File(filePath).canRead()) {
            throw Exception("You don't have permission to read $filePath")
        }

        // try reading image without OpenCV
        var imageBitmap = BitmapFactory.decodeFile(filePath)
        val rotation = when (ExifInterface(filePath).getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_NORMAL
        )) {
            ExifInterface.ORIENTATION_ROTATE_90 -> 90
            ExifInterface.ORIENTATION_ROTATE_180 -> 180
            ExifInterface.ORIENTATION_ROTATE_270 -> 270
            else -> 0
        }
        imageBitmap = Bitmap.createBitmap(
            imageBitmap,
            0,
            0,
            imageBitmap.width,
            imageBitmap.height,
            Matrix().apply { postRotate(rotation.toFloat()) },
            true
        )
        Utils.bitmapToMat(imageBitmap, image)

        return image
    }

    /**
     * take a photo with a document, crop everything out but document, and force it to display
     * as a rectangle
     *
     * @param photoFilePath original image is saved here
     * @param corners the 4 document corners
     * @return bitmap with cropped and warped document
     */
    fun crop(photoFilePath: String, corners: Quad, bitmap: Bitmap): Bitmap {
        // read image with OpenCV
        val image = this.getImageMatrixFromFilePath(photoFilePath, bitmap)

        // convert top left, top right, bottom right, and bottom left document corners from
        // Android points to OpenCV points
        val tLC = corners.topLeft.toOpenCVPoint()
        val tRC = corners.topRight.toOpenCVPoint()
        val bRC = corners.bottomRight.toOpenCVPoint()
        val bLC = corners.bottomLeft.toOpenCVPoint()

        // Calculate the document edge distances. The user might take a skewed photo of the
        // document, so the top left corner to top right corner distance might not be the same
        // as the bottom left to bottom right corner. We could take an average of the 2, but
        // this takes the smaller of the 2. It does the same for height.
        val width = min(tLC.distance(tRC), bLC.distance(bRC))
        val height = min(tLC.distance(bLC), tRC.distance(bRC))

        // create empty image matrix with cropped and warped document width and height
        val croppedImage = MatOfPoint2f(
            Point(0.0, 0.0),
            Point(width, 0.0),
            Point(width, height),
            Point(0.0, height),
        )

        // This crops the document out of the rest of the photo. Since the user might take a
        // skewed photo instead of a straight on photo, the document might be rotated and
        // skewed. This corrects that problem. output is an image matrix that contains the
        // corrected image after this fix.
        val output = Mat()
        Imgproc.warpPerspective(
            image,
            output,
            Imgproc.getPerspectiveTransform(
                MatOfPoint2f(tLC, tRC, bRC, bLC),
                croppedImage
            ),
            Size(width, height)
        )

        // convert output image matrix to bitmap
        val croppedBitmap = Bitmap.createBitmap(
            output.cols(),
            output.rows(),
            Bitmap.Config.ARGB_8888
        )
        Utils.matToBitmap(output, croppedBitmap)

        return croppedBitmap
    }

    /**
     * get bitmap image from file uri
     *
     * @param fileUriString image is saved here and starts with file:///
     * @return bitmap image
     */
    fun readBitmapFromFileUriString(
        fileUriString: String,
        contentResolver: ContentResolver
    ): Bitmap {
        return BitmapFactory.decodeStream(
            contentResolver.openInputStream(Uri.parse(fileUriString))
        )
    }
}