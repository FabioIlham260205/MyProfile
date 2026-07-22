package com.example.profilmahasiswa.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// =============================================================
// WARNA KUSTOM
// Definisikan warna aplikasi di satu tempat agar konsisten.
// Material 3 menggunakan sistem "color roles" untuk theming.
// =============================================================

// Warna utama (Primary) - Biru
val md_theme_light_primary = Color(0xFF1E88E5)          // Biru
val md_theme_light_onPrimary = Color(0xFFFFFFFF)
val md_theme_light_primaryContainer = Color(0xFFD6E7FF)
val md_theme_light_onPrimaryContainer = Color(0xFF001C3A)

// Warna sekunder (Secondary) - Biru muda
val md_theme_light_secondary = Color(0xFF2196F3)         // Biru terang
val md_theme_light_onSecondary = Color(0xFFFFFFFF)
val md_theme_light_secondaryContainer = Color(0xFFE3F2FD)
val md_theme_light_onSecondaryContainer = Color(0xFF0B1F2E)

// Warna tersier (Tertiary) - Biru keabu-abuan
val md_theme_light_tertiary = Color(0xFF4F6D8C)          // Muted Blue
val md_theme_light_onTertiary = Color(0xFFFFFFFF)

// Background & Surface
val md_theme_light_background = Color(0xFFF7FAFF)        // Putih semu biru
val md_theme_light_surface = Color(0xFFFFFFFF)
val md_theme_light_surfaceVariant = Color(0xFFDEE6F0)

// Error
val md_theme_light_error = Color(0xFFBA1A1A)

// Dark Theme Colors (Bluish Dark)
val md_theme_dark_primary = Color(0xFF9FCAFF)
val md_theme_dark_onPrimary = Color(0xFF003258)
val md_theme_dark_primaryContainer = Color(0xFF00497D)
val md_theme_dark_onPrimaryContainer = Color(0xFFD6E7FF)

val md_theme_dark_secondary = Color(0xFFBBDEFB)
val md_theme_dark_secondaryContainer = Color(0xFF23425A)
val md_theme_dark_onSecondaryContainer = Color(0xFFE3F2FD)

val md_theme_dark_tertiary = Color(0xFFB6C9DD)

val md_theme_dark_background = Color(0xFF191C1E)
val md_theme_dark_surface = Color(0xFF191C1E)
val md_theme_dark_surfaceVariant = Color(0xFF41474D)

// =============================================================
// COLOR SCHEMES
// ColorScheme mendefinisikan semua "color roles" Material 3.
// Compose otomatis menggunakan warna yang sesuai di seluruh UI.
// =============================================================

private val LightColorScheme = lightColorScheme(
    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    primaryContainer = md_theme_light_primaryContainer,
    onPrimaryContainer = md_theme_light_onPrimaryContainer,
    secondary = md_theme_light_secondary,
    onSecondary = md_theme_light_onSecondary,
    secondaryContainer = md_theme_light_secondaryContainer,
    onSecondaryContainer = md_theme_light_onSecondaryContainer,
    tertiary = md_theme_light_tertiary,
    onTertiary = md_theme_light_onTertiary,
    background = md_theme_light_background,
    surface = md_theme_light_surface,
    surfaceVariant = md_theme_light_surfaceVariant,
    error = md_theme_light_error,
)

private val DarkColorScheme = darkColorScheme(
    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_onPrimary,
    primaryContainer = md_theme_dark_primaryContainer,
    onPrimaryContainer = md_theme_dark_onPrimaryContainer,
    secondary = md_theme_dark_secondary,
    secondaryContainer = md_theme_dark_secondaryContainer,
    onSecondaryContainer = md_theme_dark_onSecondaryContainer,
    tertiary = md_theme_dark_tertiary,
    background = md_theme_dark_background,
    surface = md_theme_dark_surface,
    surfaceVariant = md_theme_dark_surfaceVariant,
)

// =============================================================
// THEME COMPOSABLE
// Fungsi utama untuk menerapkan tema ke seluruh aplikasi.
// Dipanggil di MainActivity: ProfilMahasiswaTheme { ... }
// =============================================================

@Composable
fun ProfilMahasiswaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),   // Mengikuti setting sistem
    dynamicColor: Boolean = false,                 // Dimatikan agar warna biru kustom muncul
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        // Dynamic Color mengambil warna dari wallpaper pengguna (Android 12+)
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context)
            else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography(),       // Menggunakan default Material 3 typography
        content = content
    )
}