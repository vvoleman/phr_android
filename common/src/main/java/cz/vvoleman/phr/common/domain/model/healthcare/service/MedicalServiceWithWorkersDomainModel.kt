package cz.vvoleman.phr.common.domain.model.healthcare.service

import android.os.Parcelable
import cz.vvoleman.phr.common.domain.model.healthcare.worker.MedicalWorkerWithInfoDomainModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class MedicalServiceWithWorkersDomainModel(
    val medicalService: MedicalServiceDomainModel,
    val workers: List<MedicalWorkerWithInfoDomainModel>
) : Parcelable
