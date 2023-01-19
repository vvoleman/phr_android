package cz.vvoleman.phr.ui

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import cz.vvoleman.phr.data.patient.Patient

class PatientsSpinnerAdapter (context: Context, patients: List<Patient>) :
    ArrayAdapter<Patient>(context, android.R.layout.simple_list_item_1, patients) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        val textView = view as TextView
        textView.text = getItem(position)?.name
        return view
    }
}