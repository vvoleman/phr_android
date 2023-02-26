package cz.vvoleman.phr.util

import androidx.core.app.ComponentActivity
import kotlinx.coroutines.flow.Flow

val <T> T.exhaustive: T
    get() = this