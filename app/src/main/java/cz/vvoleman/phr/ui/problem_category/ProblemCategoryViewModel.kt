package cz.vvoleman.phr.ui.problem_category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.vvoleman.phr.data.PreferencesManager
import cz.vvoleman.phr.data.core.medical_record.ProblemCategory
import cz.vvoleman.phr.data.room.medical_record.category.ProblemCategoryDao
import cz.vvoleman.phr.data.room.medical_record.category.ProblemCategoryEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class ProblemCategoryViewModel @Inject constructor(
    private val problemCategoryDao: ProblemCategoryDao,
    val preferencesManager: PreferencesManager
) : ViewModel() {

    private val problemCategoryEventChannel = Channel<ProblemCategoryEvent>()
    val problemCategoryEvent = problemCategoryEventChannel.receiveAsFlow()

    private val preferencesFlow = preferencesManager.preferencesFlow

    val patientId = preferencesFlow.map { userPreferences ->
        userPreferences.patientId
    }

    val problemCategories = patientId.flatMapLatest { patientId ->
        problemCategoryDao.getByPatientId(patientId)
    }

    fun onRecordDeleteClick(problemCategory: ProblemCategory) = viewModelScope.launch {
        problemCategoryDao.delete(ProblemCategoryEntity.from(problemCategory))
        problemCategoryEventChannel.send(ProblemCategoryEvent.ShowUndoDeleteProblemCategoryMessage(problemCategory))
    }

    fun onUndoDeleteClick(problemCategory: ProblemCategory) = viewModelScope.launch {
        problemCategoryDao.insert(ProblemCategoryEntity.from(problemCategory))
    }

    sealed class ProblemCategoryEvent {
        data class ShowUndoDeleteProblemCategoryMessage(val problemCategory: ProblemCategory) : ProblemCategoryEvent()
    }
}