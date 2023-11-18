package cz.vvoleman.phr.featureMedicine.ui.component.medicineDetailSheet

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import cz.vvoleman.phr.common.ui.adapter.MarginItemDecoration
import cz.vvoleman.phr.common.utils.SizingConstants
import cz.vvoleman.phr.featureMedicine.R
import cz.vvoleman.phr.featureMedicine.databinding.ModalMedicineDetailSheetBinding
import cz.vvoleman.phr.featureMedicine.ui.list.model.MedicineUiModel

class MedicineDetailSheet : BottomSheetDialogFragment() {

    private var _binding: ModalMedicineDetailSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ModalMedicineDetailSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!arguments?.containsKey(ARG_MEDICINE)!!) {
            Log.e(TAG, "MedicineDetailSheet: No medicine provided in bundle")
            dismiss()
            return
        }

        val medicine = arguments?.getParcelable<MedicineUiModel>(ARG_MEDICINE)!!

        val baseAdapter = MedicineDetailAdapter()
        baseAdapter.submitList(
            listOf(
                MedicineInfoUiModel(R.string.medicine_detail_package_form.toString(), medicine.packaging.form.name),
                MedicineInfoUiModel(R.string.medicine_detail_package_size.toString(), medicine.packaging.packaging),
                MedicineInfoUiModel(R.string.medicine_detail_country.toString(), "SK"),
                MedicineInfoUiModel(R.string.medicine_detail_expiration.toString(), "-"),
            )
        )

        val substanceAdapter = MedicineDetailAdapter()
        substanceAdapter.submitList(
            medicine.substances.map {
                MedicineInfoUiModel(it.substance.name, it.amount)
            }
        )

        binding.textViewName.text = medicine.name
        binding.buttonOpenLeaflet.setOnClickListener {
            val url = "http://vvoleman.eu:9999/api/medical-product/list?search=${medicine.name}"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }

        binding.recyclerViewBase.apply {
            val layout = layoutManager as GridLayoutManager
            layout.spanCount = 2
            layoutParams
            addItemDecoration(MarginItemDecoration(SizingConstants.MARGIN_SIZE, 2))
            adapter = baseAdapter
            layoutManager = layout
            setHasFixedSize(true)
        }

        binding.recyclerViewSubstances.apply {
            val layout = layoutManager as GridLayoutManager
            layout.spanCount = 1
            adapter = substanceAdapter
            addItemDecoration(MarginItemDecoration(SizingConstants.MARGIN_SIZE, 1))
            layoutManager = layout
            setHasFixedSize(true)
        }
    }

    companion object {
        const val TAG = "MedicineDetailSheet"
        private const val ARG_MEDICINE = "medicine"

        fun newInstance(medicine: MedicineUiModel): MedicineDetailSheet {
            val sheet = MedicineDetailSheet()
            sheet.arguments = Bundle().apply {
                putParcelable(ARG_MEDICINE, medicine)
            }
            return sheet
        }
    }
}
