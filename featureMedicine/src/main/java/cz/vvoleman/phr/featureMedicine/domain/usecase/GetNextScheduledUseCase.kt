package cz.vvoleman.phr.featureMedicine.domain.usecase

import cz.vvoleman.phr.base.domain.coroutine.CoroutineContextProvider
import cz.vvoleman.phr.base.domain.usecase.BackgroundExecutingUseCase
import cz.vvoleman.phr.featureMedicine.domain.factory.WeekdayDateFactory
import cz.vvoleman.phr.featureMedicine.domain.model.schedule.MedicineScheduleDomainModel
import cz.vvoleman.phr.featureMedicine.domain.model.timeline.NextScheduledRequestDomainModel
import cz.vvoleman.phr.featureMedicine.domain.repository.timeline.GetSchedulesByPatientRepository
import java.time.LocalDateTime

class GetNextScheduledUseCase(
    private val getSchedulesByPatientRepository: GetSchedulesByPatientRepository,
    coroutineContextProvider: CoroutineContextProvider,
) : BackgroundExecutingUseCase<NextScheduledRequestDomainModel, List<MedicineScheduleDomainModel>>(coroutineContextProvider) {

    override suspend fun executeInBackground(request: NextScheduledRequestDomainModel): List<MedicineScheduleDomainModel> {
        val currentWeekDay = request.currentLocalDateTime.dayOfWeek
        val currentTime = request.currentLocalDateTime.toLocalTime()
        val currentDate = request.currentLocalDateTime.toLocalDate()

        val schedules = getSchedulesByPatientRepository.getSchedulesByPatient(request.patientId)
        val schedulesMap = convertToMap(schedules)
        val translatedTimes = mutableMapOf<LocalDateTime, MutableList<String>>()

        // Translate to real date times
        schedules.forEach {schedule ->
            schedule.schedules.forEach {
                val translated = if (currentWeekDay === it.dayOfWeek && it.time.isBefore(currentTime)){
                    currentDate.plusDays(7L).atTime(it.time)
                } else {
                    WeekdayDateFactory.getNextWeekdayDate(it.dayOfWeek, currentDate).atTime(it.time)
                }

                if (translatedTimes.containsKey(translated)) {
                    translatedTimes[translated]!!.add(schedule.id)
                } else {
                    translatedTimes[translated] = mutableListOf(schedule.id)
                }
            }
        }

        // Sort by date time
        val sorted = translatedTimes.toSortedMap()

        // Get list of next schedules
        val nextSchedules = mutableListOf<MedicineScheduleDomainModel>()
        val totalSize = request.limit ?: sorted.size
        val values = sorted.values.map {it.sorted()}.flatten()

        for (i in 0 until totalSize) {
            val next = values.elementAt(i)

            nextSchedules.add(schedulesMap[next]!!)
        }


        return nextSchedules
    }

    private fun convertToMap(schedules: List<MedicineScheduleDomainModel>): Map<String, MedicineScheduleDomainModel> {
        val map = mutableMapOf<String, MedicineScheduleDomainModel>()

        schedules.forEach { schedule ->

            map[schedule.id] = schedule
        }

        return map
    }
}