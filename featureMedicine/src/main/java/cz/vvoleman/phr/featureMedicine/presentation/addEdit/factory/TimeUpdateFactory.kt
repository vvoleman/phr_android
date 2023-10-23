package cz.vvoleman.phr.featureMedicine.presentation.addEdit.factory

import cz.vvoleman.phr.featureMedicine.presentation.model.addEdit.TimePresentationModel

class TimeUpdateFactory {

    companion object {

        fun groupTimes(times: List<TimePresentationModel>): List<TimePresentationModel> {
            val map = mutableMapOf<String, TimePresentationModel>()

            times.forEach {
                val key = it.time.toString()

                if (!map.containsKey(key)) {
                    map[key] = it
                } else {
                    val time = map[key]!!
                    map[key] = time.copy(number = time.number.toDouble() + it.number.toDouble())
                }
            }

            return map.values.toList()
        }

    }

}