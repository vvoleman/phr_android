package cz.vvoleman.phr.data.diagnose

import androidx.paging.PagingSource
import androidx.paging.PagingState
import cz.vvoleman.phr.api.backend.BackendApi
import cz.vvoleman.phr.data.room.diagnose.DiagnoseEntity
import cz.vvoleman.phr.data.room.diagnose.DiagnoseGroupEntity
import cz.vvoleman.phr.data.room.diagnose.DiagnoseWithGroup

private const val STARTING_PAGE_INDEX = 1

