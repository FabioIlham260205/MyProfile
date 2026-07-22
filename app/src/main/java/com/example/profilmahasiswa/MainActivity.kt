package com.example.profilmahasiswa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.profilmahasiswa.screens.GradeScreen
import com.example.profilmahasiswa.screens.ProfileEditScreen
import com.example.profilmahasiswa.ui.theme.ProfilMahasiswaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            var selectedTab by remember { mutableIntStateOf(0) }
            // State untuk Dark Mode Toggle
            var isDarkMode by remember { mutableStateOf(true) }

            ProfilMahasiswaTheme(darkTheme = isDarkMode) {
                Scaffold(
                    bottomBar = {
                        NavigationBar(
                            containerColor = if (isDarkMode) Color(0xFF1C2128) else Color(0xFFABC9EF),
                            contentColor = if (isDarkMode) Color(0xFF9FCAFF) else Color(0xFF1A3D5D)
                        ) {
                            NavigationBarItem(
                                selected = selectedTab == 0,
                                onClick = { selectedTab = 0 },
                                icon = { Icon(Icons.Default.AccountCircle, contentDescription = "Profil", modifier = Modifier.size(24.dp)) },
                                label = { Text("Profil") },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = if (isDarkMode) Color(0xFF9FCAFF) else Color(0xFF1A3D5D),
                                    unselectedIconColor = Color.Gray,
                                    selectedTextColor = if (isDarkMode) Color(0xFF9FCAFF) else Color(0xFF1A3D5D),
                                    unselectedTextColor = Color.Gray,
                                    indicatorColor = if (isDarkMode) Color(0xFF003D6D) else Color(0xFFDEE6F0)
                                )
                            )
                            NavigationBarItem(
                                selected = selectedTab == 1,
                                onClick = { selectedTab = 1 },
                                icon = { Icon(Icons.Default.ListAlt, contentDescription = "Data Nilai", modifier = Modifier.size(24.dp)) },
                                label = { Text("Nilai") },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = if (isDarkMode) Color(0xFF9FCAFF) else Color(0xFF1A3D5D),
                                    unselectedIconColor = Color.Gray,
                                    selectedTextColor = if (isDarkMode) Color(0xFF9FCAFF) else Color(0xFF1A3D5D),
                                    unselectedTextColor = Color.Gray,
                                    indicatorColor = if (isDarkMode) Color(0xFF003D6D) else Color(0xFFDEE6F0)
                                )
                            )
                        }
                    }
                ) { innerPadding ->
                    when (selectedTab) {
                        0 -> ProfileEditScreen(
                            paddingValues = innerPadding,
                            onNavigateToGrades = { selectedTab = 1 },
                            isDarkMode = isDarkMode,
                            onThemeToggle = { isDarkMode = it }
                        )
                        1 -> GradeScreen(innerPadding)
                    }
                }
            }
        }
    }
}
