package com.example.profilmahasiswa.screens

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import coil.compose.AsyncImage
import com.example.profilmahasiswa.R

// Definisi warna sesuai gambar
val ColorBackground = Color(0xFF121212)
val ColorCardBackground = Color(0xFF2D333B)
val ColorInnerRowBackground = Color(0xFF1C2128)
val ColorAccentBlue = Color(0xFF9FCAFF)
val ColorTopBar = Color(0xFFABC9EF)
val ColorTopBarText = Color(0xFF1A3D5D)

/**
 * ProfilePhotoSection - Foto profil dengan Box (overlapping).
 * Diperbarui untuk mendukung pemilihan gambar dari galeri dan bentuk lingkaran sesuai gambar.
 */
@Composable
fun ProfilePhotoSection(
    imageUri: Uri? = null,
    onImageClick: () -> Unit = {},
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.padding(8.dp),
    ) {
        Box(
            modifier = Modifier
                .size(130.dp)
                .clip(CircleShape)
                .border(
                    width = 2.dp,
                    color = ColorAccentBlue,
                    shape = CircleShape
                )
                .clickable { onImageClick() },
            contentAlignment = Alignment.Center
        ) {
            if (imageUri != null) {
                AsyncImage(
                    model = imageUri,
                    contentDescription = "Foto Profil",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Foto Profil",
                    modifier = Modifier.size(70.dp),
                    tint = Color.White.copy(alpha = 0.6f)
                )
            }
        }

        // Badge Kamera (Putih dengan border tipis)
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = (-4).dp, y = (-4).dp)
                .size(32.dp)
                .clip(CircleShape)
                .background(Color.White)
                .border(1.dp, Color.Gray.copy(alpha = 0.3f), CircleShape)
                .clickable { onImageClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.CameraAlt,
                contentDescription = "Ubah Foto",
                tint = ColorAccentBlue,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

/**
 * ContactRow - Satu baris informasi kontak dengan gaya sesuai gambar.
 */
@Composable
fun ContactRow(
    icon: ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = ColorInnerRowBackground,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icon di dalam lingkaran biru gelap
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(Color(0xFF003D6D)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = ColorAccentBlue,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = label,
                fontSize = 12.sp,
                color = Color.Gray,
                fontWeight = FontWeight.Normal
            )
            Text(
                text = value,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        }
    }
}

/**
 * AcademicStatsCard - Card berisi statistik akademik sesuai gambar.
 */
@Composable
fun AcademicStatsCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = ColorCardBackground
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "\uD83D\uDCCA", // Chart icon emoji as per image
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Statistik Akademik",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatItem(
                    modifier = Modifier.weight(1f),
                    value = "4.00",
                    label = "IPK",
                    valueColor = ColorAccentBlue
                )
                StatItem(
                    modifier = Modifier.weight(1f),
                    value = "120",
                    label = "SKS",
                    valueColor = Color.White
                )
                StatItem(
                    modifier = Modifier.weight(1f),
                    value = "6",
                    label = "Semester",
                    valueColor = Color.White
                )
            }
        }
    }
}

/**
 * StatItem - Satu item statistik.
 */
@Composable
fun StatItem(
    modifier: Modifier = Modifier,
    value: String,
    label: String,
    valueColor: Color
) {
    Column(
        modifier = modifier
            .background(
                color = ColorInnerRowBackground,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = valueColor
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            fontSize = 12.sp,
            color = Color.Gray
        )
    }
}
