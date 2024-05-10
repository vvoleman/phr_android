package cz.vvoleman.phr.featureMedicalRecord.ui.adapter

import android.net.Uri
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ortiz.touchview.TouchImageView
import cz.vvoleman.phr.featureMedicalRecord.ui.model.ImageItemUiModel

class GalleryAdapter(
    private val photoList: List<ImageItemUiModel>
) : RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return photoList.size
    }

    class ViewHolder(view: TouchImageView) : RecyclerView.ViewHolder(view) {
        val imagePlace = view
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(TouchImageView(parent.context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            setOnTouchListener { view, event ->
                var result = true
                //can scroll horizontally checks if there's still a part of the image
                //that can be scrolled until you reach the edge
                if (event.pointerCount >= 2 || view.canScrollHorizontally(1) && canScrollHorizontally(-1)) {
                    //multi-touch event
                    result = when (event.action) {
                        MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                            // Disallow RecyclerView to intercept touch events.
                            parent.requestDisallowInterceptTouchEvent(true)
                            // Disable touch on view
                            false
                        }
                        MotionEvent.ACTION_UP -> {
                            // Allow RecyclerView to intercept touch events.
                            parent.requestDisallowInterceptTouchEvent(false)
                            true
                        }
                        else -> true
                    }
                }
                view.performClick()
                result
            }
        })
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val uri = Uri.parse(photoList[position].asset.uri)
        holder.imagePlace.setImageURI(uri)
    }

    override fun getItemViewType(i: Int): Int {
        return 0
    }

}
