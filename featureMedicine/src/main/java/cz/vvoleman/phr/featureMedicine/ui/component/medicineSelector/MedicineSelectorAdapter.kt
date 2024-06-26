package cz.vvoleman.phr.featureMedicine.ui.component.medicineSelector

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import cz.vvoleman.phr.featureMedicine.databinding.ItemMedicineSelectorBinding
import cz.vvoleman.phr.featureMedicine.ui.list.model.MedicineUiModel

class MedicineSelectorAdapter(
    private val listener: OnItemClickListener
) :
    PagingDataAdapter<MedicineUiModel, MedicineSelectorAdapter.MedicineSelectorViewHolder>(
        DiffCallback()
    ) {

    private var selectedItemPosition: Int = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineSelectorViewHolder {
        val binding =
            ItemMedicineSelectorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MedicineSelectorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MedicineSelectorViewHolder, position: Int) {
        val item = getItem(position)

        if (item != null) {
            holder.bind(item, position == selectedItemPosition)
        }
    }

    inner class MedicineSelectorViewHolder(private val binding: ItemMedicineSelectorBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                root.setOnClickListener {
                    if (selectedItemPosition == bindingAdapterPosition) {
                        selectedItemPosition = RecyclerView.NO_POSITION
                        listener.onItemClick(null)
                        notifyItemChanged(bindingAdapterPosition)
                        return@setOnClickListener
                    }

                    val previousSelectedItemPosition = selectedItemPosition
                    selectedItemPosition = bindingAdapterPosition
                    notifyItemChanged(selectedItemPosition)
                    if (previousSelectedItemPosition != RecyclerView.NO_POSITION) {
                        notifyItemChanged(previousSelectedItemPosition)
                    }
                    listener.onItemClick(getItem(selectedItemPosition))
                }
            }
        }

        fun bind(item: MedicineUiModel, isSelected: Boolean) {
            binding.apply {
                textViewName.text = item.name
                textViewForm.text = item.packaging.form.name
                textViewPackaging.text = item.packaging.packaging

                if (isSelected) {
                    root.setBackgroundResource(cz.vvoleman.phr.base.R.color.beige_200)
                } else {
                    root.setBackgroundResource(cz.vvoleman.phr.base.R.color.white)
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(item: MedicineUiModel?)
    }

    class DiffCallback : DiffUtil.ItemCallback<MedicineUiModel>() {
        override fun areItemsTheSame(oldItem: MedicineUiModel, newItem: MedicineUiModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: MedicineUiModel,
            newItem: MedicineUiModel
        ): Boolean {
            return oldItem == newItem
        }
    }
}
