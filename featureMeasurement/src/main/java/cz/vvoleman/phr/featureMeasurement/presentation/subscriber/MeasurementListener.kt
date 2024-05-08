package cz.vvoleman.phr.featureMeasurement.presentation.subscriber

import android.net.Uri
import cz.vvoleman.phr.base.domain.ModuleListener
import cz.vvoleman.phr.common.domain.model.problemCategory.request.DataDeleteType
import cz.vvoleman.phr.common.presentation.event.problemCategory.DeleteProblemCategoryEvent
import cz.vvoleman.phr.common.presentation.event.problemCategory.ExportProblemCategoryEvent
import cz.vvoleman.phr.common.presentation.event.problemCategory.GetProblemCategoryDetailSectionEvent
import cz.vvoleman.phr.common.presentation.eventBus.CommonEventBus
import cz.vvoleman.phr.common.ui.export.usecase.DocumentPage
import cz.vvoleman.phr.common.ui.view.problemCategory.detail.groupie.SectionContainer
import cz.vvoleman.phr.featureMeasurement.domain.model.detail.GetFieldStatsRequest
import cz.vvoleman.phr.featureMeasurement.domain.model.list.DeleteMeasurementGroupRequest
import cz.vvoleman.phr.featureMeasurement.domain.repository.GetMeasurementGroupsByProblemCategoryRepository
import cz.vvoleman.phr.featureMeasurement.domain.repository.UpdateMeasurementGroupProblemCategoryRepository
import cz.vvoleman.phr.featureMeasurement.domain.usecase.detail.GetFieldStatsUseCase
import cz.vvoleman.phr.featureMeasurement.domain.usecase.list.DeleteMeasurementGroupUseCase
import cz.vvoleman.phr.featureMeasurement.presentation.mapper.core.MeasurementGroupPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMeasurement.presentation.mapper.detail.FieldStatsPresentationModelToDomainMapper
import cz.vvoleman.phr.featureMeasurement.presentation.mapper.export.ExportDetailParamsPresentationModel
import cz.vvoleman.phr.featureMeasurement.presentation.model.detail.MeasurementGroupWithStatsPresentationModel
import cz.vvoleman.phr.featureMeasurement.ui.mapper.detail.EntryInfoUiModelToMeasurementGroupMapper
import cz.vvoleman.phr.featureMeasurement.ui.view.export.DetailMeasurementGroupPage

class MeasurementListener(
    private val commonEventBus: CommonEventBus,
    private val fieldStatsUseCase: GetFieldStatsUseCase,
    private val deleteMeasurementGroupUseCase: DeleteMeasurementGroupUseCase,
    private val updateMeasurementGroupProblemCategoryRepository: UpdateMeasurementGroupProblemCategoryRepository,
    private val getMeasurementsByProblemCategoryRepository: GetMeasurementGroupsByProblemCategoryRepository,
    private val measurementGroupMapper: MeasurementGroupPresentationModelToDomainMapper,
    private val entryMapper: EntryInfoUiModelToMeasurementGroupMapper,
    private val fieldStatsMapper: FieldStatsPresentationModelToDomainMapper,
    private val problemCategoryDetailProvider: ProblemCategoryDetailProvider,
) : ModuleListener() {

    override val TAG = "MeasurementListener"

    override suspend fun onInit() {
        super.onInit()

        commonEventBus.getCategoryDetailSection.addListener(TAG) {
            return@addListener onGetCategoryDetailSection(it)
        }

        commonEventBus.exportProblemCategoryBus.addListener(TAG) {
            return@addListener onExportProblemCategory(it)
        }

        commonEventBus.deleteProblemCategoryBus.addListener(TAG) {
            return@addListener onDeleteProblemCategory(it)
        }
    }

    private suspend fun onDeleteProblemCategory(event: DeleteProblemCategoryEvent) {
        val groups = getMeasurementsByProblemCategoryRepository
            .getMeasurementGroupsByProblemCategory(event.problemCategory.id)

        when (event.deleteType) {
            is DataDeleteType.DeleteData -> {
                groups.forEach {
                    deleteMeasurementGroupUseCase.executeInBackground(DeleteMeasurementGroupRequest(
                        measurementGroup = it
                    ))
                }
            }
            is DataDeleteType.MoveToAnother -> {
                val anotherCategory = (event.deleteType as DataDeleteType.MoveToAnother).backupProblemCategory
                groups.forEach {
                    updateMeasurementGroupProblemCategoryRepository.updateMeasurementGroupProblemCategory(
                        measurementGroup = it,
                        problemCategory = anotherCategory
                    )
                }
            }
        }
    }

    private suspend fun onExportProblemCategory(
        event: ExportProblemCategoryEvent
    ): List<DocumentPage> {
        val groups = getMeasurementsByProblemCategoryRepository
            .getMeasurementGroupsByProblemCategory(event.problemCategory.id)
            .map { measurementGroupMapper.toPresentation(it) }

        return groups.map { group ->
            val entries = entryMapper.toUi(group)
            val params = ExportDetailParamsPresentationModel(
                measurementGroup = group,
                entries = entries
            )

            splitEntriesToPages(params)
        }.flatten()
    }

    private suspend fun onGetCategoryDetailSection(
        event: GetProblemCategoryDetailSectionEvent
    ): List<SectionContainer> {
        // Get all records for category
        val groups = getMeasurementsByProblemCategoryRepository
            .getMeasurementGroupsByProblemCategory(event.problemCategory.id)

        val stats = mutableListOf<MeasurementGroupWithStatsPresentationModel>()

        groups.forEach { group ->
            val request = GetFieldStatsRequest(
                measurementGroup = group
            )
            val result = fieldStatsUseCase.executeInBackground(request)
            stats.add(MeasurementGroupWithStatsPresentationModel(
                measurementGroup = measurementGroupMapper.toPresentation(group),
                fieldStats = fieldStatsMapper.toPresentation(result)
            ))
        }

        val section = problemCategoryDetailProvider.getBindingItems(stats) { id ->
            val uri = Uri.parse("phr://list/?medicalRecordId=$id")
//            navController?.navigate(uri)
        }

        return listOf(section)
    }

    private fun splitEntriesToPages(params: ExportDetailParamsPresentationModel): List<DetailMeasurementGroupPage> {
        val pages = mutableListOf<DetailMeasurementGroupPage>()
        val entries = params.entries.sortedBy { it.entry.createdAt }
        val pageCount = entries.size / EXPORT_LIMIT_PER_PAGE + 1
        for (i in 0 until pageCount) {
            val start = i * EXPORT_LIMIT_PER_PAGE
            val end = (i + 1) * EXPORT_LIMIT_PER_PAGE
            val subEntries = entries.subList(start, end.coerceAtMost(entries.size))
            val subParams = params.copy(entries = subEntries)
            pages.add(DetailMeasurementGroupPage(subParams))
        }
        return pages
    }

    companion object {
        const val EXPORT_LIMIT_PER_PAGE = 11
    }

}
