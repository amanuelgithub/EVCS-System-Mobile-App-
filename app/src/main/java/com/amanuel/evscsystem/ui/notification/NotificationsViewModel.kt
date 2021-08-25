package com.amanuel.evscsystem.ui.notification

import androidx.lifecycle.*
import com.amanuel.evscsystem.data.repository.NotificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject internal constructor(
    repository: NotificationRepository
) : ViewModel() {

    val searchQuery = MutableStateFlow("")

    val sortOrder = MutableStateFlow(SortOrder.BY_DATE)

    private val notificationsFlow = combine(
        searchQuery,
        sortOrder,
    ) { query, sortOrder->
        Pair(query, sortOrder)
    }.flatMapLatest {(query, sortOrder) ->
        repository.getNotifications(query, sortOrder)
    }

    // Note: this .asLiveData() does multiple things including launching a coroutine.
    val notifications = notificationsFlow.asLiveData()
}

enum class SortOrder { BY_PLATE_NUMBER, BY_DATE }
