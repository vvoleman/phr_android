package cz.vvoleman.phr.common.utils

import android.view.inputmethod.EditorInfo
import android.widget.EditText

fun EditText.setClearFocusOnDoneAction() {
    setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            clearFocus()
            false
        } else {
            false
        }
    }
}
