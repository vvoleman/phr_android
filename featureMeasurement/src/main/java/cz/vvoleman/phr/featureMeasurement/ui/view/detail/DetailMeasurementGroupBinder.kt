package cz.vvoleman.phr.featureMeasurement.ui.view.detail

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.patrykandpatrick.vico.core.entry.FloatEntry
import com.patrykandpatrick.vico.core.entry.entryOf
import com.xwray.groupie.GroupieAdapter
import cz.vvoleman.phr.base.ui.mapper.BaseViewStateBinder
import cz.vvoleman.phr.common.ui.adapter.MarginItemDecoration
import cz.vvoleman.phr.common.utils.SizingConstants
import cz.vvoleman.phr.featureMeasurement.databinding.FragmentDetailMeasurementGroupBinding
import cz.vvoleman.phr.featureMeasurement.presentation.model.detail.DetailMeasurementGroupViewState
import cz.vvoleman.phr.featureMeasurement.ui.adapter.detail.FieldStatsAdapter
import cz.vvoleman.phr.featureMeasurement.ui.component.fieldInfoTable.FieldInfoTableAdapter
import cz.vvoleman.phr.featureMeasurement.ui.factory.TableFactory
import cz.vvoleman.phr.featureMeasurement.ui.mapper.detail.EntryInfoUiModelToMeasurementGroupMapper
import cz.vvoleman.phr.featureMeasurement.ui.mapper.detail.FieldStatsUiModelToPresentationMapper
import cz.vvoleman.phr.featureMeasurement.ui.model.detail.EntryInfoUiModel
import java.time.LocalDate
import kotlin.random.Random

class DetailMeasurementGroupBinder(
    private val entryInfoMapper: EntryInfoUiModelToMeasurementGroupMapper,
    private val statsMapper: FieldStatsUiModelToPresentationMapper
) :
    BaseViewStateBinder<DetailMeasurementGroupViewState, FragmentDetailMeasurementGroupBinding, DetailMeasurementGroupBinder.Notification>(),
    FieldInfoTableAdapter.FieldInfoTableListener {

    override fun firstBind(
        viewBinding: FragmentDetailMeasurementGroupBinding,
        viewState: DetailMeasurementGroupViewState
    ) {
        super.firstBind(viewBinding, viewState)

        val statsAdapter = FieldStatsAdapter()
        viewBinding.recyclerViewStats.apply {
            adapter = statsAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(MarginItemDecoration(SizingConstants.MARGIN_SIZE))
            setHasFixedSize(false)
        }

        statsAdapter.submitList(viewState.fieldStats.map { statsMapper.toUi(it) })

        val containers = TableFactory().create(entryInfoMapper.toUi(viewState.measurementGroup))

        val tableAdapter = GroupieAdapter().apply { add(containers) }
        viewBinding.recyclerViewTable.apply {
            adapter = tableAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(false)
        }
    }

    override fun bind(viewBinding: FragmentDetailMeasurementGroupBinding, viewState: DetailMeasurementGroupViewState) {
        super.bind(viewBinding, viewState)

    }

    private fun getRandomEntries(): List<FloatEntry> {
        val data = getRandomData()
        return data.map { entryOf(it.key.toEpochDay().toFloat(), it.value) }
    }

    private fun getRandomData(): Map<LocalDate, Float> {
        val map = mutableMapOf<LocalDate, Float>()
        var date = startDate
        repeat(100) {
            map[date] = Random.nextFloat() * 16f
            date = date.plusDays(1)
        }
        return map
    }

    sealed class Notification {
        data class ShowTableItemOptionsMenu(val item: EntryInfoUiModel, val anchorView: View) : Notification()
    }

    companion object {
        private val startDate = LocalDate.of(2023, 1, 22)
    }

    override fun onItemOptionsMenuClicked(item: EntryInfoUiModel, anchorView: View) {
        notify(Notification.ShowTableItemOptionsMenu(item, anchorView))
    }

}
