package com.amanuel.evscsystem.ui.record

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.amanuel.evscsystem.data.repository.RecordRepository
import com.amanuel.evscsystem.ui.record.SortOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class RecordsViewModel @Inject internal constructor(
    recordRepository: RecordRepository
) : ViewModel() {

    val searchQuery = MutableStateFlow("")

    val sortOrder = MutableStateFlow(SortOrder.BY_DATE)

//    val records = recordRepository.getRecords().asLiveData()

    private val recordsFlow = combine(
        searchQuery,
        sortOrder,
    ) { query, sortOrder ->
        Pair(query, sortOrder)
    }.flatMapLatest { (query, sortOrder) ->
        recordRepository.getRecords(query, sortOrder)
    }

    val records = recordsFlow.asLiveData()

}

enum class SortOrder { BY_PLATE_NUMBER, BY_DATE }
