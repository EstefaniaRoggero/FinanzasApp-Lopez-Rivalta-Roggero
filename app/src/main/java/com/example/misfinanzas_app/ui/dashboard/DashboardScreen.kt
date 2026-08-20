package com.example.misfinanzas_app.ui.dashboard

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.misfinanzas_app.ui.theme.MisFinanzasAppTheme

@Composable
fun DashboardRoute(
    viewModel: DashboardViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    DashboardScreen(
        uiState = uiState,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun DashboardScreen(
    uiState: DashboardUiState,
    onEvent: (DashboardUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.TopCenter
        ) {
            LazyColumn(
                modifier = Modifier
                    .widthIn(max = 720.dp)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item(key = "header") {
                    DashboardHeader(
                        userName = uiState.userName,
                        periodLabel = uiState.periodLabel
                    )
                }
                item(key = "balance") {
                    BalanceCard(
                        balance = uiState.totalBalance,
                        isVisible = uiState.isBalanceVisible,
                        onToggleBalance = {
                            onEvent(DashboardUiEvent.ToggleBalanceVisibility)
                        }
                    )
                }
                item(key = "monthly-summary") {
                    MonthlySummaryRow(
                        monthlyIncome = uiState.monthlyIncome,
                        monthlyExpenses = uiState.monthlyExpenses,
                        isAmountVisible = uiState.isBalanceVisible
                    )
                }
                item(key = "transactions-title") {
                    Text(
                        text = "Últimos movimientos",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                items(
                    items = uiState.recentTransactions,
                    key = { transaction -> transaction.id }
                ) { transaction ->
                    TransactionItem(
                        transaction = transaction,
                        isAmountVisible = uiState.isBalanceVisible
                    )
                }
            }
        }
    }
}

@Composable
fun DashboardHeader(
    userName: String,
    periodLabel: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "FinanzasApp",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Hola, $userName",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Resumen de $periodLabel",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun BalanceCard(
    balance: Double,
    isVisible: Boolean,
    onToggleBalance: () -> Unit,
    modifier: Modifier = Modifier
) {
    val toggleLabel = if (isVisible) "Ocultar saldo" else "Mostrar saldo"
    val displayedBalance = if (isVisible) balance.toMoneyLabel() else "$ ••••••"

    Card(
        modifier = modifier
            .fillMaxWidth()
            .semantics { contentDescription = "Saldo total $displayedBalance" },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = MaterialTheme.shapes.large
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Text(
                text = "Saldo total",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = displayedBalance,
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            TextButton(onClick = onToggleBalance) {
                Text(text = toggleLabel)
            }
        }
    }
}

@Composable
fun MonthlySummaryRow(
    monthlyIncome: Double,
    monthlyExpenses: Double,
    isAmountVisible: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        SummaryMetricCard(
            title = "Ingresos",
            amount = monthlyIncome,
            isAmountVisible = isAmountVisible,
            trendLabel = "+",
            trendDescription = "Ingresos del mes",
            useTertiaryPalette = true,
            modifier = Modifier.weight(1f)
        )
        SummaryMetricCard(
            title = "Gastos",
            amount = monthlyExpenses,
            isAmountVisible = isAmountVisible,
            trendLabel = "−",
            trendDescription = "Gastos del mes",
            useTertiaryPalette = false,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun SummaryMetricCard(
    title: String,
    amount: Double,
    isAmountVisible: Boolean,
    trendLabel: String,
    trendDescription: String,
    useTertiaryPalette: Boolean,
    modifier: Modifier = Modifier
) {
    val containerColor = if (useTertiaryPalette) {
        MaterialTheme.colorScheme.tertiaryContainer
    } else {
        MaterialTheme.colorScheme.errorContainer
    }
    val contentColor = if (useTertiaryPalette) {
        MaterialTheme.colorScheme.onTertiaryContainer
    } else {
        MaterialTheme.colorScheme.onErrorContainer
    }
    val displayedAmount = if (isAmountVisible) amount.toMoneyLabel() else "$ ••••"

    Card(
        modifier = modifier.semantics {
            contentDescription = "$trendDescription $displayedAmount"
        },
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = trendLabel,
                style = MaterialTheme.typography.headlineSmall,
                color = contentColor,
                modifier = Modifier.semantics { contentDescription = trendDescription }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge,
                color = contentColor
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = displayedAmount,
                style = MaterialTheme.typography.titleMedium,
                color = contentColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun TransactionItem(
    transaction: Transaction,
    isAmountVisible: Boolean,
    modifier: Modifier = Modifier
) {
    val isIncome = transaction.type == TransactionType.INCOME
    val amountColor = if (isIncome) {
        MaterialTheme.colorScheme.tertiary
    } else {
        MaterialTheme.colorScheme.error
    }
    val amountPrefix = if (isIncome) "+" else "−"
    val displayedAmount = if (isAmountVisible) {
        "$amountPrefix${transaction.amount.toMoneyLabel()}"
    } else {
        "$ ••••"
    }
    val typeLabel = if (isIncome) "Ingreso" else "Gasto"
    val avatarBackground = if (isIncome) {
        MaterialTheme.colorScheme.tertiaryContainer
    } else {
        MaterialTheme.colorScheme.errorContainer
    }
    val avatarContent = if (isIncome) {
        MaterialTheme.colorScheme.onTertiaryContainer
    } else {
        MaterialTheme.colorScheme.onErrorContainer
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .semantics {
                contentDescription =
                    "${transaction.name}, ${transaction.category}, ${transaction.date}, $typeLabel $displayedAmount"
            },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(avatarBackground),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = transaction.name.take(1),
                    style = MaterialTheme.typography.titleMedium,
                    color = avatarContent
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = transaction.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "${transaction.category} · ${transaction.date}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Text(
                text = displayedAmount,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                color = amountColor
            )
        }
    }
}

@Preview(name = "Dashboard claro", showBackground = true, showSystemUi = true)
@Composable
private fun DashboardLightPreview() {
    var uiState by remember { mutableStateOf(DashboardUiState.sample()) }
    MisFinanzasAppTheme(darkTheme = false) {
        DashboardScreen(
            uiState = uiState,
            onEvent = { event ->
                if (event is DashboardUiEvent.ToggleBalanceVisibility) {
                    uiState = uiState.copy(isBalanceVisible = !uiState.isBalanceVisible)
                }
            }
        )
    }
}

@Preview(
    name = "Dashboard oscuro",
    showBackground = true,
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun DashboardDarkPreview() {
    var uiState by remember { mutableStateOf(DashboardUiState.sample()) }
    MisFinanzasAppTheme(darkTheme = true) {
        DashboardScreen(
            uiState = uiState,
            onEvent = { event ->
                if (event is DashboardUiEvent.ToggleBalanceVisibility) {
                    uiState = uiState.copy(isBalanceVisible = !uiState.isBalanceVisible)
                }
            }
        )
    }
}
