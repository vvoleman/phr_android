package cz.vvoleman.phr.featureMedicine.domain.model.schedule

import android.os.Parcelable
import cz.vvoleman.phr.common.domain.model.PatientDomainModel
import cz.vvoleman.phr.featureMedicine.domain.model.medicine.MedicineDomainModel
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class MedicineScheduleDomainModel(
    val id: String,
    val patient: PatientDomainModel,
    val medicine: MedicineDomainModel,
    val schedules: List<ScheduleItemDomainModel>,
    val createdAt: LocalDateTime,
    val isAlarmEnabled: Boolean,
) : Parcelable
