package cz.vvoleman.phr.featureMedicine.ui.list.model.schedule

import android.os.Parcelable
import cz.vvoleman.phr.common.ui.model.patient.PatientUiModel
import cz.vvoleman.phr.featureMedicine.ui.list.model.MedicineUiModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class ScheduleItemWithDetailsUiModel(
    val scheduleItem: ScheduleItemUiModel,
    val medicine: MedicineUiModel,
    val patient: PatientUiModel,
    val medicineScheduleId: String,
    val isAlarmEnabled: Boolean
) : Parcelable
