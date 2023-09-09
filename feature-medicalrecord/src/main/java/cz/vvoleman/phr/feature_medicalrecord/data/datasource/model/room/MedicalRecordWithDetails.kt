package cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.room

import androidx.room.Embedded
import androidx.room.Relation
import cz.vvoleman.phr.common.data.datasource.model.PatientDataSourceModel
import cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.room.asset.MedicalRecordAssetDataSourceModel
import cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.room.category.ProblemCategoryDataSourceModel
import cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.room.diagnose.DiagnoseDataSourceModel
import cz.vvoleman.phr.feature_medicalrecord.data.datasource.model.room.worker.MedicalWorkerDataSourceModel

data class MedicalRecordWithDetails(
    @Embedded val medicalRecord: MedicalRecordDataSourceModel,
    @Relation(
        parentColumn = "patient_id",
        entityColumn = "id"
    )
    val patient: PatientDataSourceModel,

    @Relation(
        parentColumn = "diagnose_id",
        entityColumn = "id"
    )
    val diagnose: DiagnoseDataSourceModel?,

    @Relation(
        parentColumn = "medical_worker_id",
        entityColumn = "id"
    )
    val medicalWorker: MedicalWorkerDataSourceModel?,

    @Relation(
        parentColumn = "id",
        entityColumn = "medical_record_id"
    )
    val assets: List<MedicalRecordAssetDataSourceModel>,

    @Relation(
        parentColumn = "problem_category_id",
        entityColumn = "id"
    )
    val problemCategory: ProblemCategoryDataSourceModel?
)
