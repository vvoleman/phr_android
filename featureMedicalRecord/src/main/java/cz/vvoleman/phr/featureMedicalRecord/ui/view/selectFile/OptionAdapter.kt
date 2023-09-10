package cz.vvoleman.phr.featureMedicalRecord.ui.view.selectFile

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import cz.vvoleman.phr.featureMedicalRecord.R

class OptionAdapter(context: Context, resource: Int, listItems: List<OptionItem>) :
    ArrayAdapter<OptionItem>(context, resource, listItems) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val listItem = getItem(position)

        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_select_file_option, parent, false)

        val valueTextView = view.findViewById<TextView>(R.id.textViewValue)
        val displayTextView = view.findViewById<TextView>(R.id.textViewDisplay)

        valueTextView.text = listItem?.value
        displayTextView.text = listItem?.display

        return view
    }
}
