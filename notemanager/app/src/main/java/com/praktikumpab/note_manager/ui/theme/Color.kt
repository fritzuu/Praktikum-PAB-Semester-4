package com.praktikumpab.note_manager.ui.theme

import androidx.compose.ui.graphics.Color

// Light Mode Palette
val LightPrimary = Color(0xFF6C63FF)
val LightPrimaryVariant = Color(0xFF5A52E6)
val LightSecondary = Color(0xFF8B5CF6)
val LightAccent = Color(0xFF22C55E)
val LightBackground = Color(0xFFF5F7FB)
val LightSurface = Color(0xFFFFFFFF)
val LightOnSurface = Color(0xFF111827)
val LightTextSecondary = Color(0xFF6B7280)
val LightOutline = Color(0xFFE5E7EB)
val LightSuccess = Color(0xFF22C55E)
val LightWarning = Color(0xFFF59E0B)
val LightError = Color(0xFFEF4444)

// Dark Mode Palette
val DarkPrimary = Color(0xFF8B5CF6)
val DarkPrimaryVariant = Color(0xFF7C3AED)
val DarkSecondary = Color(0xFFA78BFA)
val DarkAccent = Color(0xFF22C55E)
val DarkBackground = Color(0xFF0F172A)
val DarkSurface = Color(0xFF1E293B)
val DarkOnSurface = Color(0xFFF8FAFC)
val DarkTextSecondary = Color(0xFFCBD5E1)
val DarkOutline = Color(0xFF334155)
val DarkSuccess = Color(0xFF22C55E)
val DarkWarning = Color(0xFFFBBF24)
val DarkError = Color(0xFFF87171)

// Gradients
val IndigoPurpleGradient = listOf(LightPrimary, LightSecondary)
val PurpleCyanGradient = listOf(LightSecondary, Color(0xFF06B6D4))
val GreenAccentGradient = listOf(Color(0xFF10B981), Color(0xFF22C55E))

// Category Colors (for badges and labels)
fun getCategoryColor(category: String, isDark: Boolean): Color {
    return when (category) {
        "Kuliah" -> if (isDark) Color(0xFF818CF8) else Color(0xFF4F46E5)
        "Tugas" -> if (isDark) Color(0xFFF87171) else Color(0xFFEF4444)
        "Ide" -> if (isDark) Color(0xFFFBBF24) else Color(0xFFD97706)
        "Pribadi" -> if (isDark) Color(0xFF34D399) else Color(0xFF059669)
        "Organisasi" -> if (isDark) Color(0xFFC084FC) else Color(0xFF7C3AED)
        "Project" -> if (isDark) Color(0xFF22D3EE) else Color(0xFF0891B2)
        else -> if (isDark) Color(0xFF94A3B8) else Color(0xFF64748B)
    }
}

