package com.amanuel.evscsystem.ui.record

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.amanuel.evscsystem.data.repository.RecordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecordsViewModel @Inject internal constructor(
    recordRepository: RecordRepository
): ViewModel(){

    val records = recordRepository.getRecords().asLiveData()
}