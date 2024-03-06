package cz.vvoleman.phr.common.ui.view.healthcare.detailMedicalWorker.groupie

import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.xwray.groupie.viewbinding.BindableItem
import cz.vvoleman.phr.common_datasource.R
import cz.vvoleman.phr.common_datasource.databinding.ItemDetailMedicalWorkerContactBinding

class ContactItem(
    @DrawableRes private val icon: Int,
    private val value: String,
    @StringRes private val buttonText: Int,
    private val onClick: (String) -> Unit
) : BindableItem<ItemDetailMedicalWorkerContactBinding>() {

    override fun bind(viewBinding: ItemDetailMedicalWorkerContactBinding, position: Int) {
        viewBinding.textViewMail.text = value
        viewBinding.textViewMail.setCompoundDrawablesWithIntrinsicBounds(icon, 0, 0, 0)
        viewBinding.buttonMail.text = viewBinding.root.resources.getText(buttonText)
        viewBinding.buttonMail.setOnClickListener {
            onClick(value)
        }
    }

    override fun getLayout() = R.layout.item_detail_medical_worker_contact

    override fun initializeViewBinding(view: View): ItemDetailMedicalWorkerContactBinding {
        return ItemDetailMedicalWorkerContactBinding.bind(view)
    }
}
