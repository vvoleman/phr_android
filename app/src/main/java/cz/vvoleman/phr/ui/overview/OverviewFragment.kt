package cz.vvoleman.phr.ui.overview

import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import cz.vvoleman.phr.R
import cz.vvoleman.phr.data.core.Medicine
import cz.vvoleman.phr.data.core.Substance
import cz.vvoleman.phr.data.repository.MedicineRepository
import cz.vvoleman.phr.data.room.medicine.MedicineDao
import cz.vvoleman.phr.data.room.medicine.MedicineEntity
import cz.vvoleman.phr.data.room.medicine.MedicineWithSubstances
import cz.vvoleman.phr.data.room.medicine.substance.SubstanceDao
import cz.vvoleman.phr.data.room.medicine.substance.SubstanceEntity
import cz.vvoleman.phr.databinding.FragmentOverviewBinding
import cz.vvoleman.phr.util.network.IConnectivityObserver
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@AndroidEntryPoint
class OverviewFragment: Fragment(R.layout.fragment_overview) {

    @Inject lateinit var medicineRepository: MedicineRepository
    @Inject lateinit var medicineDao: MedicineDao

    private var _binding: FragmentOverviewBinding? = null
    private val binding get() = _binding!!

    private var clicked = false

    private val viewModel: OverviewViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentOverviewBinding.bind(view)

        val colors = mapOf(
            Pair(IConnectivityObserver.ConnectionStatus.UNAVAILABLE, "#ff0000"),
            Pair(IConnectivityObserver.ConnectionStatus.AVAILABLE, "#00FF00"),
            Pair(IConnectivityObserver.ConnectionStatus.LOSING, "#FF03A9F4"),
            Pair(IConnectivityObserver.ConnectionStatus.LOST, "#FFFF9800"),
        )
        val texts = mapOf(
            Pair(IConnectivityObserver.ConnectionStatus.UNAVAILABLE, "Offline"),
            Pair(IConnectivityObserver.ConnectionStatus.AVAILABLE, "Online"),
            Pair(IConnectivityObserver.ConnectionStatus.LOSING, "Losing connection"),
            Pair(IConnectivityObserver.ConnectionStatus.LOST, "Lost connection"),
        )

        binding.loadButton.setOnClickListener {
            lifecycleScope.launchWhenCreated {
                if (!clicked) {
                    setData()
                    clicked = true
                } else {
                    displayData()
                }
            }

        }

        viewModel.connectionStatus.observe(viewLifecycleOwner) { status ->
            Log.d("OverviewFragment", "Connection changed to $status")
            val color = colors[status]

            if (color != null) {
                val drawable = DrawableCompat.wrap(binding.textViewStatusIcon.background)
                DrawableCompat.setTint(drawable, android.graphics.Color.parseColor(color))
                binding.textViewStatusIcon.background = drawable
            }

            val text = texts[status]
            if (text != null) {
                binding.overviewFragmentTitle.text = text
            }
        }
    }

    private suspend fun setData() {
        val medicine = Medicine(
            "_666",
            "Medicine",
            "1",
            "Description",
            LocalDate.now().plusDays(30),
            substances = listOf(
                Substance(
                    "_1",
                    "Substance 1",
                    true,
                    true
                ),
                Substance(
                    "_2",
                    "Substance 2",
                    false,
                    false
                ),
            )
        )

        medicineRepository.create(medicine)
    }

    private suspend fun displayData() {
        val medicine = medicineDao.getById("_666")
        medicine.collectLatest {
            Log.d("OverviewFragment", "Medicine: $it")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}