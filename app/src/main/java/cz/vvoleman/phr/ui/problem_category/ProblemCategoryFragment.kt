package cz.vvoleman.phr.ui.problem_category

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import cz.vvoleman.phr.R
import cz.vvoleman.phr.databinding.FragmentProblemCategoryBinding
import cz.vvoleman.phr.util.collectLatestLifecycleFlow
import cz.vvoleman.phr.util.exhaustive

class ProblemCategoryFragment : Fragment(R.layout.fragment_problem_category) {

    private var _binding: FragmentProblemCategoryBinding? = null
    private val binding get() = _binding!!

    val viewModel: ProblemCategoryViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentProblemCategoryBinding.bind(view)

        collectLatestLifecycleFlow(viewModel.problemCategoryEvent) { event ->
            when (event) {
                is ProblemCategoryViewModel.ProblemCategoryEvent.ShowUndoDeleteProblemCategoryMessage -> {
                }
            }.exhaustive
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}