package cz.vvoleman.phr.ui.overview

import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import cz.vvoleman.phr.R
import cz.vvoleman.phr.data.patient.Patient
import cz.vvoleman.phr.databinding.FragmentOverviewBinding
import cz.vvoleman.phr.ui.main.MainViewModel
import cz.vvoleman.phr.data.AdapterPair
import cz.vvoleman.phr.ui.views.dialog_spinner.DialogSpinner
import cz.vvoleman.phr.util.network.IConnectivityObserver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OverviewFragment : Fragment(R.layout.fragment_overview) {

    private var _binding: FragmentOverviewBinding? = null
    private val binding get() = _binding!!

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

        viewModel.connectionStatus.observe(viewLifecycleOwner) {status ->
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}