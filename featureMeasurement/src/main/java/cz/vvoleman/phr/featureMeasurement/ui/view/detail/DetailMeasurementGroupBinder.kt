package cz.vvoleman.phr.featureMeasurement.ui.view.detail

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupieAdapter
import cz.vvoleman.phr.base.ui.mapper.BaseViewStateBinder
import cz.vvoleman.phr.common.ui.adapter.MarginItemDecoration
import cz.vvoleman.phr.common.utils.SizingConstants
import cz.vvoleman.phr.featureMeasurement.databinding.FragmentDetailMeasurementGroupBinding
import cz.vvoleman.phr.featureMeasurement.presentation.model.detail.DetailMeasurementGroupViewState
import cz.vvoleman.phr.featureMeasurement.ui.adapter.detail.FieldStatsAdapter
import cz.vvoleman.phr.featureMeasurement.ui.factory.TableFactory
import cz.vvoleman.phr.featureMeasurement.ui.mapper.detail.EntryInfoUiModelToMeasurementGroupMapper
import cz.vvoleman.phr.featureMeasurement.ui.mapper.detail.FieldStatsUiModelToPresentationMapper
import cz.vvoleman.phr.featureMeasurement.ui.model.detail.EntryInfoUiModel

class DetailMeasurementGroupBinder(
    private val entryInfoMapper: EntryInfoUiModelToMeasurementGroupMapper,
    private val statsMapper: FieldStatsUiModelToPresentationMapper
) :
    BaseViewStateBinder<DetailMeasurementGroupViewState, FragmentDetailMeasurementGroupBinding, DetailMeasurementGroupBinder.Notification>(),
    TableFactory.EntryTableInterface {

    override fun firstBind(
        viewBinding: FragmentDetailMeasurementGroupBinding,
        viewState: DetailMeasurementGroupViewState
    ) {
        super.firstBind(viewBinding, viewState)
    }

    override fun bind(viewBinding: FragmentDetailMeasurementGroupBinding, viewState: DetailMeasurementGroupViewState) {
        super.bind(viewBinding, viewState)

        val statsAdapter = FieldStatsAdapter()
        viewBinding.recyclerViewStats.apply {
            adapter = statsAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(MarginItemDecoration(SizingConstants.MARGIN_SIZE))
            setHasFixedSize(false)
        }

        statsAdapter.submitList(viewState.fieldStats.map { statsMapper.toUi(it) })

        val containers = TableFactory(this)
            .create(entryInfoMapper.toUi(viewState.measurementGroup))

        val tableAdapter = GroupieAdapter().apply { add(containers) }
        viewBinding.recyclerViewTable.apply {
            adapter = tableAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(false)
        }
    }

    sealed class Notification {
        data class ShowTableItemOptionsMenu(val item: EntryInfoUiModel, val anchorView: View) : Notification()
    }

    override fun onItemOptionsMenuClicked(item: EntryInfoUiModel, anchorView: View) {
        notify(Notification.ShowTableItemOptionsMenu(item, anchorView))
    }

}
