package cz.vvoleman.phr.common.ui.view.problemCategory.export

import android.view.LayoutInflater
import android.view.View
import androidx.viewbinding.ViewBinding
import cz.vvoleman.phr.common.presentation.model.problemCategory.export.ProblemCategoryParams
import cz.vvoleman.phr.common.ui.export.usecase.DocumentPage
import cz.vvoleman.phr.common.utils.toLocalString
import cz.vvoleman.phr.common_datasource.databinding.DocumentProblemCategoryBinding
import java.time.LocalDate

class ProblemCategoryDocumentPage(
    private val params: ProblemCategoryParams
) : DocumentPage() {

    override fun bind(binding: ViewBinding) {
        val viewBinding = binding as DocumentProblemCategoryBinding

        val startedAt = params.problemCategory.createdAt.toLocalDate()
        val endedAt = LocalDate.now()

        viewBinding.textViewHeader.text = params.problemCategory.name
        viewBinding.textViewHeaderStartAt.text = startedAt.toLocalString()
        viewBinding.textViewHeaderEndAt.text = endedAt.toLocalString()
        viewBinding.textViewPatientName.text = params.patient.name

        if (params.patient.birthDate != null) {
            viewBinding.textViewPatientBirthdate.text = params.patient.birthDate.toLocalString()
            viewBinding.textViewPatientBirthdate.visibility = View.VISIBLE
        } else {
            viewBinding.textViewPatientBirthdate.visibility = View.GONE
        }
    }

    override fun getViewBinding(inflater: LayoutInflater): ViewBinding {
        return DocumentProblemCategoryBinding.inflate(inflater)
    }
}
