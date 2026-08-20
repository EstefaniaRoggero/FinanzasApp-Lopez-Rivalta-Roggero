package com.example.misfinanzas_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.misfinanzas_app.ui.dashboard.DashboardRoute
import com.example.misfinanzas_app.ui.theme.MisFinanzasAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MisFinanzasAppTheme {
                DashboardRoute()
            }
        }
    }
}
