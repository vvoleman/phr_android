package cz.vvoleman.phr.common.utils

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView.OnItemClickListener
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.AutoCompleteTextView
import android.widget.EditText
import androidx.lifecycle.LifecycleCoroutineScope
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlin.math.min
import kotlin.math.sign

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

@ExperimentalCoroutinesApi
fun EditText.textChanges(emitOnStart: Boolean = false): Flow<CharSequence?> {
    return callbackFlow<CharSequence?> {
        val listener = object : TextWatcher {
            override fun afterTextChanged(s: Editable?)  {
                Log.d("EditTextUtil", "afterTextChanged: $s")
                trySend(s)
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
        }
        addTextChangedListener(listener)
        awaitClose { removeTextChangedListener(listener) }
    }.onStart { if (emitOnStart) emit(text) }
}

fun EditText.setTextIfNotFocused(text: CharSequence?) {
    setText(text)
}

@ExperimentalCoroutinesApi
fun AutoCompleteTextView.itemChanges(): Flow<Any?> {
    return callbackFlow {
        val listener = OnItemClickListener { parent, _, position, _ ->
            val item = parent.getItemAtPosition(position)
            trySend(item)
        }
        onItemClickListener = listener
        awaitClose { onItemClickListener = null }
    }
}
