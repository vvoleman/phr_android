package cz.vvoleman.phr.featureMedicalRecord.ui.view.selectFile.adapter

data class OptionItem(val value: String, val display: String) {
    override fun toString(): String {
        return display
    }
}
