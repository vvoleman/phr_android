package cz.vvoleman.phr.common.ui.adapter.problemCategory

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import cz.vvoleman.phr.common.ui.model.problemCategory.ColorUiModel
import cz.vvoleman.phr.common_datasource.R

class ColorAdapter(
    context: Context,
    private val colors: List<ColorUiModel>
) : ArrayAdapter<ColorUiModel>(context, 0, colors) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getItemView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getItemView(position, convertView, parent)
    }

    private fun getItemView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_color, parent, false)

        val colorSquare = view.findViewById<View>(R.id.view_color)
        val colorName = view.findViewById<TextView>(R.id.text_view_name)

        val model = colors[position]
        colorSquare.setBackgroundColor(Color.parseColor(model.color))
        colorName.text = model.name

        return view
    }
}
