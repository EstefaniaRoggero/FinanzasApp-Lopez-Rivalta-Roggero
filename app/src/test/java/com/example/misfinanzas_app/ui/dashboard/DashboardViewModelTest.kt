package com.example.misfinanzas_app.ui.dashboard

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class DashboardViewModelTest {

    @Test
    fun initialState_exposesSampleDashboardData() {
        val viewModel = DashboardViewModel()
        val state = viewModel.uiState.value

        assertEquals("Lucía", state.userName)
        assertEquals("Agosto 2026", state.periodLabel)
        assertEquals(248_750.50, state.totalBalance, 0.001)
        assertTrue(state.isBalanceVisible)
        assertEquals(7, state.recentTransactions.size)
    }

    @Test
    fun onEvent_toggleBalanceVisibility_hidesBalance() {
        val viewModel = DashboardViewModel()

        viewModel.onEvent(DashboardUiEvent.ToggleBalanceVisibility)

        assertFalse(viewModel.uiState.value.isBalanceVisible)
    }

    @Test
    fun onEvent_toggleBalanceVisibilityTwice_showsBalanceAgain() {
        val viewModel = DashboardViewModel()

        viewModel.onEvent(DashboardUiEvent.ToggleBalanceVisibility)
        viewModel.onEvent(DashboardUiEvent.ToggleBalanceVisibility)

        assertTrue(viewModel.uiState.value.isBalanceVisible)
    }
}
