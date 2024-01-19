package cz.vvoleman.phr.featureMedicalRecord.ui.view.addEdit.adapter

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cz.vvoleman.phr.featureMedicalRecord.databinding.ItemFileThumbnailBinding
import cz.vvoleman.phr.featureMedicalRecord.ui.model.ImageItemUiModel

class ImageAdapter(
    private val listener: FileAdapterListener,
    private val allowDelete: Boolean = true
) : ListAdapter<ImageItemUiModel, ImageAdapter.ImageViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        Log.d("ImageAdapter", "onCreateViewHolder: ")
        val binding =
            ItemFileThumbnailBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ImageViewHolder(binding, allowDelete)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class ImageViewHolder(private val binding: ItemFileThumbnailBinding, private val allowDelete: Boolean) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                root.setOnClickListener {
                    val position = bindingAdapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val item = getItem(position)
                        listener.onFileClicked(item)
                    }
                }
                buttonDelete.setOnClickListener {
                    val position = bindingAdapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val item = getItem(position)
                        listener.onFileDeleted(item)
                    }
                }
            }

            if (!allowDelete) {
                binding.buttonDelete.visibility = android.view.View.GONE
            }
        }

        fun bind(item: ImageItemUiModel) {
            Log.d("ImageAdapter", "bind: $item")
            binding.apply {
                imageViewThumbnail.setImageURI(Uri.parse(item.asset.uri))
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<ImageItemUiModel>() {
        override fun areItemsTheSame(oldItem: ImageItemUiModel, newItem: ImageItemUiModel): Boolean {
            Log.d("ImageAdapter", "areItemsTheSame: ${oldItem.asset.uri} == ${newItem.asset.uri}")
            return oldItem.asset.uri == newItem.asset.uri
        }

        override fun areContentsTheSame(oldItem: ImageItemUiModel, newItem: ImageItemUiModel): Boolean {
            return oldItem == newItem
        }
    }

    interface FileAdapterListener {
        fun onFileClicked(item: ImageItemUiModel)
        fun onFileDeleted(item: ImageItemUiModel)
    }
}
