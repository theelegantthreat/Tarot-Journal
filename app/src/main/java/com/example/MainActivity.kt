package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.Today
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ui.components.CardDetailDialog
import com.example.ui.screens.DailyAlignmentScreen
import com.example.ui.screens.HistoryLogScreen
import com.example.ui.screens.SpreadStudioScreen
import com.example.ui.screens.WeeklySynthesisScreen
import com.example.ui.theme.AmethystPurple
import com.example.ui.theme.CelestialCyan
import com.example.ui.theme.EncryptedGreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.ObsidianBorder
import com.example.ui.theme.ObsidianCard
import com.example.ui.theme.ObsidianDeep
import com.example.ui.theme.ObsidianSurface
import com.example.ui.theme.ObsidianSurfaceVariant
import com.example.ui.theme.StarlightGold
import com.example.ui.theme.StarlightMuted
import com.example.ui.theme.StarlightWhite
import com.example.ui.viewmodel.TarotViewModel

sealed class Screen(val route: String, val label: String, val icon: ImageVector, val tag: String) {
    object Journal : Screen("journal", "Journal", Icons.AutoMirrored.Filled.MenuBook, "tab_journal")
    object Spreads : Screen("spreads", "Spreads", Icons.Default.GridView, "tab_spreads")
    object Weekly : Screen("weekly", "Weekly Synthesis", Icons.Default.CalendarMonth, "tab_weekly")
    object History : Screen("history", "History & Trends", Icons.Default.History, "tab_history")
}

class MainActivity : ComponentActivity() {

    private val viewModel: TarotViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route ?: Screen.Journal.route

                val isSynced by viewModel.isCloudSynced.collectAsState()
                val selectedCardForDetail by viewModel.selectedCardForDetail.collectAsState()

                val items = listOf(
                    Screen.Journal,
                    Screen.Spreads,
                    Screen.Weekly,
                    Screen.History
                )

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = ObsidianDeep,
                    topBar = {
                        TopAppBar(
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = ObsidianDeep,
                                titleContentColor = StarlightGold
                            ),
                            title = {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = "✧ ARCANA",
                                            fontSize = 18.sp,
                                            fontFamily = FontFamily.Serif,
                                            fontWeight = FontWeight.Bold,
                                            color = StarlightGold,
                                            letterSpacing = 2.sp
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = "JOURNAL",
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = StarlightWhite,
                                            letterSpacing = 1.sp
                                        )
                                    }

                                    Surface(
                                        color = ObsidianSurfaceVariant,
                                        shape = RoundedCornerShape(8.dp),
                                        border = BorderStroke(0.5.dp, EncryptedGreen.copy(alpha = 0.6f)),
                                        modifier = Modifier.padding(end = 12.dp)
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Lock,
                                                contentDescription = "Encrypted Cloud Active",
                                                tint = EncryptedGreen,
                                                modifier = Modifier.size(12.dp)
                                            )
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text(
                                                text = "AES-256 SYNC",
                                                color = EncryptedGreen,
                                                fontSize = 9.sp,
                                                fontWeight = FontWeight.Bold,
                                                letterSpacing = 0.5.sp
                                            )
                                        }
                                    }
                                }
                            }
                        )
                    },
                    bottomBar = {
                        NavigationBar(
                            containerColor = ObsidianSurface,
                            tonalElevation = 8.dp
                        ) {
                            items.forEach { screen ->
                                val selected = currentRoute == screen.route
                                NavigationBarItem(
                                    icon = {
                                        Icon(
                                            imageVector = screen.icon,
                                            contentDescription = screen.label
                                        )
                                    },
                                    label = {
                                        Text(
                                            text = screen.label,
                                            fontSize = 10.sp,
                                            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                                            maxLines = 1
                                        )
                                    },
                                    selected = selected,
                                    onClick = {
                                        if (currentRoute != screen.route) {
                                            navController.navigate(screen.route) {
                                                popUpTo(navController.graph.findStartDestination().id) {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        }
                                    },
                                    colors = NavigationBarItemDefaults.colors(
                                        selectedIconColor = StarlightGold,
                                        selectedTextColor = StarlightGold,
                                        unselectedIconColor = StarlightMuted,
                                        unselectedTextColor = StarlightMuted,
                                        indicatorColor = ObsidianSurfaceVariant
                                    ),
                                    modifier = Modifier.testTag(screen.tag)
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Journal.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(Screen.Journal.route) {
                            DailyAlignmentScreen(
                                viewModel = viewModel,
                                onNavigateToHistory = {
                                    navController.navigate(Screen.History.route)
                                }
                            )
                        }
                        composable("daily") {
                            DailyAlignmentScreen(
                                viewModel = viewModel,
                                onNavigateToHistory = {
                                    navController.navigate(Screen.History.route)
                                }
                            )
                        }
                        composable(Screen.Spreads.route) {
                            SpreadStudioScreen(
                                viewModel = viewModel,
                                onNavigateToHistory = {
                                    navController.navigate(Screen.History.route)
                                }
                            )
                        }
                        composable(Screen.Weekly.route) {
                            WeeklySynthesisScreen(
                                viewModel = viewModel,
                                onNavigateToHistory = {
                                    navController.navigate(Screen.History.route)
                                }
                            )
                        }
                        composable(Screen.History.route) {
                            HistoryLogScreen(
                                viewModel = viewModel
                            )
                        }
                    }
                }

                // Global Card Inspection Modal
                selectedCardForDetail?.let { (card, isReversed) ->
                    CardDetailDialog(
                        card = card,
                        isReversed = isReversed,
                        onDismiss = { viewModel.closeCardDetail() }
                    )
                }
            }
        }
    }
}
