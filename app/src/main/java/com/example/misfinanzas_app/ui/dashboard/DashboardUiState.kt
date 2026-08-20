package com.example.misfinanzas_app.ui.dashboard

import java.text.NumberFormat
import java.util.Locale

data class DashboardUiState(
    val userName: String,
    val periodLabel: String,
    val totalBalance: Double,
    val isBalanceVisible: Boolean,
    val monthlyIncome: Double,
    val monthlyExpenses: Double,
    val recentTransactions: List<Transaction>
) {
    companion object {
        fun sample(): DashboardUiState = DashboardUiState(
            userName = "Lucía",
            periodLabel = "Agosto 2026",
            totalBalance = 248_750.50,
            isBalanceVisible = true,
            monthlyIncome = 213_000.00,
            monthlyExpenses = 97_250.75,
            recentTransactions = sampleTransactions()
        )

        private fun sampleTransactions(): List<Transaction> = listOf(
            Transaction(
                id = "tx-001",
                name = "Sueldo",
                category = "Ingresos",
                date = "01 ago",
                amount = 185_000.00,
                type = TransactionType.INCOME
            ),
            Transaction(
                id = "tx-002",
                name = "Supermercado",
                category = "Alimentación",
                date = "18 ago",
                amount = 32_450.20,
                type = TransactionType.EXPENSE
            ),
            Transaction(
                id = "tx-003",
                name = "Alquiler",
                category = "Vivienda",
                date = "05 ago",
                amount = 45_000.00,
                type = TransactionType.EXPENSE
            ),
            Transaction(
                id = "tx-004",
                name = "Netflix",
                category = "Entretenimiento",
                date = "12 ago",
                amount = 6_499.00,
                type = TransactionType.EXPENSE
            ),
            Transaction(
                id = "tx-005",
                name = "Subte y colectivo",
                category = "Transporte",
                date = "19 ago",
                amount = 8_320.00,
                type = TransactionType.EXPENSE
            ),
            Transaction(
                id = "tx-006",
                name = "Freelance diseño",
                category = "Ingresos",
                date = "15 ago",
                amount = 28_000.00,
                type = TransactionType.INCOME
            ),
            Transaction(
                id = "tx-007",
                name = "Farmacia",
                category = "Salud",
                date = "16 ago",
                amount = 4_981.55,
                type = TransactionType.EXPENSE
            )
        )
    }
}

data class Transaction(
    val id: String,
    val name: String,
    val category: String,
    val date: String,
    val amount: Double,
    val type: TransactionType
)

enum class TransactionType {
    INCOME,
    EXPENSE
}

sealed interface DashboardUiEvent {
    data object ToggleBalanceVisibility : DashboardUiEvent
}

fun Double.toMoneyLabel(): String {
    val formatter = NumberFormat.getNumberInstance(Locale.forLanguageTag("es-AR")).apply {
        minimumFractionDigits = 2
        maximumFractionDigits = 2
    }
    return "$ ${formatter.format(this)}"
}
