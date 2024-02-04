package cz.vvoleman.phr.featureMedicalRecord.ui.provider.problemCategoryDetail

import android.view.View
import com.xwray.groupie.viewbinding.BindableItem
import cz.vvoleman.phr.common.utils.toLocalString
import cz.vvoleman.phr.featureMedicalRecord.R
import cz.vvoleman.phr.featureMedicalRecord.databinding.ItemProblemCategoryDetailMedicalRecordBinding
import cz.vvoleman.phr.featureMedicalRecord.presentation.model.core.MedicalRecordPresentationModel

class MedicalRecordItem(
    private val medicalRecord: MedicalRecordPresentationModel,
    private val onClick: (String) -> Unit
) : BindableItem<ItemProblemCategoryDetailMedicalRecordBinding>() {

    override fun bind(viewBinding: ItemProblemCategoryDetailMedicalRecordBinding, position: Int) {
        viewBinding.root.setOnClickListener {
            onClick(medicalRecord.id)
        }

        viewBinding.apply {
            textViewName.text = medicalRecord.diagnose?.name ?: root.context.getString(R.string.no_diagnose)
            textViewCreatedAt.text = medicalRecord.createdAt.toLocalString()
            textViewMedicalWorker.text = medicalRecord.specificMedicalWorker?.medicalWorker?.name
        }
    }

    override fun getLayout(): Int {
        return R.layout.item_problem_category_detail_medical_record
    }

    override fun initializeViewBinding(view: View): ItemProblemCategoryDetailMedicalRecordBinding {
        return ItemProblemCategoryDetailMedicalRecordBinding.bind(view)
    }
}
