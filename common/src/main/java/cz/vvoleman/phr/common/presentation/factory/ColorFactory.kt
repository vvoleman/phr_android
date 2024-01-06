package cz.vvoleman.phr.common.presentation.factory

import android.content.Context
import cz.vvoleman.phr.common.presentation.model.problemCategory.ColorPresentationModel
import cz.vvoleman.phr.common_datasource.R

class ColorFactory(
    private val context: Context,
) {

    fun getStandardColors(): List<ColorPresentationModel> {
        val names = context.resources.getStringArray(R.array.names)
        val colors = context.resources.getStringArray(R.array.colors)

        return names.zip(colors) { name, color ->
            ColorPresentationModel(
                name = name,
                color = color
            )
        }
    }

}
