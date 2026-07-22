package com.example.profilmahasiswa.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class GradeInfo(val subject: String, val score: String, val grade: String)

@Composable
fun GradeScreen(paddingValues: PaddingValues) {
    val isDarkMode = isSystemInDarkTheme() || true // Defaulting for consistency with requested design
    
    val grades = listOf(
        GradeInfo("Pemrograman Perangkat Bergerak", "90", "A"),
        GradeInfo("Basis Data", "85", "A-"),
        GradeInfo("Sistem Informasi", "88", "A"),
        GradeInfo("Analisis Desain Sistem", "82", "B+"),
        GradeInfo("Kecerdasan Buatan", "92", "A")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(if (isDarkMode) ColorBackground else MaterialTheme.colorScheme.background)
            .padding(paddingValues)
            .padding(24.dp)
    ) {
        Text(
            text = "Data Nilai Mahasiswa",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = if (isDarkMode) Color.White else Color.Black
        )
        
        Spacer(modifier = Modifier.height(20.dp))

        // BOX - Header Informasi Mahasiswa
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(if (isDarkMode) ColorCardBackground else MaterialTheme.colorScheme.surfaceVariant)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Nama", fontSize = 12.sp, color = Color.Gray)
                    Text("Fabio Ilham M", fontWeight = FontWeight.Bold, color = if (isDarkMode) Color.White else Color.Black)
                }
                
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Semester", fontSize = 12.sp, color = Color.Gray)
                    Text("6", fontWeight = FontWeight.Bold, color = if (isDarkMode) Color.White else Color.Black)
                }
                
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.End
                ) {
                    Text("NIM", fontSize = 12.sp, color = Color.Gray)
                    Text("23083000111", fontWeight = FontWeight.Bold, color = if (isDarkMode) Color.White else Color.Black)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // BOX - Header List Mata Kuliah
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(ColorAccentBlue)
                .padding(16.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text("Mata Kuliah", modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold, color = Color.Black)
                Text("Nilai", modifier = Modifier.width(60.dp), fontWeight = FontWeight.Bold, color = Color.Black)
                Text("Huruf", modifier = Modifier.width(60.dp), fontWeight = FontWeight.Bold, color = Color.Black)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // LAZY COLUMN - Daftar Nilai
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(grades) { grade ->
                GradeItem(grade, isDarkMode)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // BOX - Ringkasan IPK
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = if (isDarkMode) Color(0xFF003D6D) else MaterialTheme.colorScheme.primaryContainer),
            shape = RoundedCornerShape(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Indeks Prestasi Kumulatif (IPK)",
                    color = if (isDarkMode) Color.White else MaterialTheme.colorScheme.onPrimaryContainer,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp
                )
                Text(
                    text = "4.00",
                    color = if (isDarkMode) ColorAccentBlue else MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
            }
        }
    }
}

@Composable
fun GradeItem(grade: GradeInfo, isDarkMode: Boolean) {
    Card(
        colors = CardDefaults.cardColors(containerColor = if (isDarkMode) ColorCardBackground else MaterialTheme.colorScheme.surfaceVariant),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = grade.subject,
                modifier = Modifier.weight(1f),
                color = if (isDarkMode) Color.White else Color.Black,
                fontSize = 14.sp
            )
            Text(
                text = grade.score,
                modifier = Modifier.width(60.dp),
                color = if (isDarkMode) ColorAccentBlue else MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
            Box(
                modifier = Modifier
                    .width(40.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (isDarkMode) Color(0xFF003D6D) else MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = grade.grade,
                    color = if (isDarkMode) ColorAccentBlue else MaterialTheme.colorScheme.primary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}
