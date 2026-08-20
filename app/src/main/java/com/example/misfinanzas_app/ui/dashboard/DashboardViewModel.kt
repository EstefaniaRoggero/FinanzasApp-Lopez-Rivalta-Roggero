package com.example.misfinanzas_app.ui.dashboard

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DashboardViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState.sample())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    fun onEvent(event: DashboardUiEvent) {
        when (event) {
            DashboardUiEvent.ToggleBalanceVisibility -> toggleBalanceVisibility()
        }
    }

    private fun toggleBalanceVisibility() {
        _uiState.update { current ->
            current.copy(isBalanceVisible = !current.isBalanceVisible)
        }
    }
}
