package cz.vvoleman.phr.featureMedicine.domain.model.schedule

import android.os.Parcelable
import cz.vvoleman.phr.common.domain.model.patient.PatientDomainModel
import cz.vvoleman.phr.featureMedicine.domain.model.medicine.MedicineDomainModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class ScheduleItemWithDetailsDomainModel(
    val scheduleItem: ScheduleItemDomainModel,
    val medicine: MedicineDomainModel,
    val patient: PatientDomainModel,
    val medicineScheduleId: String,
    val isAlarmEnabled: Boolean
) : Parcelable
