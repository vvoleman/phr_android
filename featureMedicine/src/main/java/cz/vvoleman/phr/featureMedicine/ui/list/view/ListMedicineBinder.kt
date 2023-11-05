package cz.vvoleman.phr.featureMedicine.ui.list.view

import cz.vvoleman.phr.base.ui.mapper.BaseViewStateBinder
import cz.vvoleman.phr.featureMedicine.databinding.FragmentListMedicineBinding
import cz.vvoleman.phr.featureMedicine.presentation.list.model.ListMedicineViewState
import cz.vvoleman.phr.featureMedicine.presentation.mapper.list.ScheduleItemPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMedicine.ui.mapper.list.MedicineUiModelToPresentationMapper
import cz.vvoleman.phr.featureMedicine.ui.mapper.list.NextScheduleItemUiModelToPresentationMapper
import cz.vvoleman.phr.featureMedicine.ui.mapper.list.ScheduleItemUiModelToPresentationMapper

@Suppress("UnusedPrivateProperty")
class ListMedicineBinder(
    private val nextScheduleItemMapper: NextScheduleItemUiModelToPresentationMapper,
    private val medicineMapper: MedicineUiModelToPresentationMapper,
    private val scheduleItemMapper: ScheduleItemPresentationModelToDomainMapper,
    private val scheduleItemUiMapper: ScheduleItemUiModelToPresentationMapper,
) : BaseViewStateBinder<ListMedicineViewState, FragmentListMedicineBinding, ListMedicineBinder.Notification>() {

    override fun bind(viewBinding: FragmentListMedicineBinding, viewState: ListMedicineViewState) {
        if (viewState.selectedNextSchedule?.dateTime != viewBinding.nextSchedule.getSchedule()?.dateTime) {
            val nextScheduleUi = viewState.selectedNextSchedule?.let { nextScheduleItemMapper.toUi(it) }
            viewBinding.nextSchedule.setSchedule(nextScheduleUi)
        }
    }

    sealed class Notification

    companion object {
        const val TAG = "ListMedicineBinder"
    }
}
