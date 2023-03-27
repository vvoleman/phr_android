package cz.vvoleman.phr.feature_medicalrecord.domain.mapper

import android.graphics.Point
import com.google.mlkit.vision.text.Text
import cz.vvoleman.phr.feature_medicalrecord.domain.model.select_file.Position
import cz.vvoleman.phr.feature_medicalrecord.domain.model.select_file.TextDomainModel

class TextToTextDomainModelMapper {

    fun toDomain(text: Text): TextDomainModel {
        val blocks = mutableListOf<TextDomainModel>()
        for (block in text.textBlocks) {
            if (block.lines.size == 0) {
                continue
            }
            val lines = mutableListOf<TextDomainModel>()
            for (line in block.lines) {
                if (line.elements.size == 0) {
                    continue
                }
                val elements = mutableListOf<TextDomainModel>()
                for (element in line.elements) {
                    if (element.cornerPoints == null) {
                        continue
                    }

                    elements.add(
                        TextDomainModel(
                            cornerPoints = element.cornerPoints?.let{cornersToPositions(it)} ?: emptyList(),
                            texts = emptyList(),
                            value = element.text
                        )
                    )
                }

                lines.add(
                    TextDomainModel(
                        texts = elements,
                        cornerPoints = line.cornerPoints?.let{cornersToPositions(it)} ?: emptyList(),
                    )
                )
            }
            blocks.add(
                TextDomainModel(
                    cornerPoints = block.cornerPoints?.let{cornersToPositions(it)} ?: emptyList(),
                    texts = lines,
                )
            )
        }

        return TextDomainModel(
            // Todo: Add corner points
            texts = blocks
        )
    }

    private fun cornersToPositions(corners: Array<Point>): List<Position> {
        return corners.map { point ->
            Position(point.x, point.y)
        }
    }



}