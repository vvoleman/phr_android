package cz.vvoleman.phr.featureMeasurement.ui.component.entryField

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class EntryFieldAdapter(
    private val items: List<EntryFieldItem>
) : RecyclerView.Adapter<EntryFieldAdapter.EntryFieldViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryFieldViewHolder {
        val type = EntryFieldItem.Type.values().find { it.layoutId == viewType }
            ?: throw IllegalArgumentException("Unknown view type: $viewType")

        val inflater = LayoutInflater.from(parent.context)
        // Get binding by layoutId
        val binding = type.getBinding(inflater, parent)

        return EntryFieldViewHolder(binding)
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].getType().layoutId
    }

    override fun onBindViewHolder(holder: EntryFieldViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class EntryFieldViewHolder(private val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
        private var isInitialized = false
        fun bind(field: EntryFieldItem) {
            if (!isInitialized) {
                field.initField(binding)
                isInitialized = true
            }
            field.bind(binding)
        }
    }
}
