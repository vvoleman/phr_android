package cz.vvoleman.phr.common.presentation.eventBus

import cz.vvoleman.phr.base.domain.eventBus.EventBusChannel
import cz.vvoleman.phr.common.presentation.event.GetMedicalFacilitiesAdditionalInfoEvent
import cz.vvoleman.phr.common.presentation.event.GetMedicalWorkersAdditionalInfoEvent
import cz.vvoleman.phr.common.presentation.event.MedicalWorkerDeletedEvent
import cz.vvoleman.phr.common.presentation.event.problemCategory.DeleteProblemCategoryEvent
import cz.vvoleman.phr.common.presentation.event.problemCategory.GetProblemCategoriesAdditionalInfoEvent
import cz.vvoleman.phr.common.presentation.event.problemCategory.GetProblemCategoryDetailSectionEvent
import cz.vvoleman.phr.common.domain.model.healthcare.AdditionalInfoDomainModel
import cz.vvoleman.phr.common.domain.model.healthcare.facility.MedicalFacilityDomainModel
import cz.vvoleman.phr.common.domain.model.healthcare.worker.MedicalWorkerDomainModel
import cz.vvoleman.phr.common.domain.model.problemCategory.ProblemCategoryDomainModel
import cz.vvoleman.phr.common.domain.model.problemCategory.ProblemCategoryInfoDomainModel
import cz.vvoleman.phr.common.ui.view.problemCategory.detail.groupie.SectionContainer

object CommonEventBus {

    val getWorkerAdditionalInfoBus =
        EventBusChannel<GetMedicalWorkersAdditionalInfoEvent, Map<MedicalWorkerDomainModel, List<AdditionalInfoDomainModel<MedicalWorkerDomainModel>>>>()

    val getFacilityAdditionalInfoBus =
        EventBusChannel<GetMedicalFacilitiesAdditionalInfoEvent, Map<MedicalFacilityDomainModel, List<AdditionalInfoDomainModel<MedicalFacilityDomainModel>>>>()

    val medicalWorkerDeletedEvent = EventBusChannel<MedicalWorkerDeletedEvent, Unit>()

    val getCategoryAdditionalInfoBus =
        EventBusChannel<GetProblemCategoriesAdditionalInfoEvent,
                Map<ProblemCategoryDomainModel, ProblemCategoryInfoDomainModel>>()

    val deleteProblemCategoryBus = EventBusChannel<DeleteProblemCategoryEvent, Unit>()

    val getCategoryDetailSection = EventBusChannel<GetProblemCategoryDetailSectionEvent, SectionContainer>()
}
