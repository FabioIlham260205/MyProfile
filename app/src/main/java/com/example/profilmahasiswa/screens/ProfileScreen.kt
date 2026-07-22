package com.example.profilmahasiswa.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.profilmahasiswa.ui.theme.ProfilMahasiswaTheme

/**
 * ProfileScreen - Halaman utama profil mahasiswa dengan UI sesuai gambar.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen() {
    var editCount by remember { mutableStateOf(0) }
    var isEditing by remember { mutableStateOf(false) }

    var nama by remember { mutableStateOf("Fabio Ilham M") }
    var nim by remember { mutableStateOf("23083000111") }
    var jurusan by remember { mutableStateOf("Sistem Informasi") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "Profil Mahasiswa", 
                        fontWeight = FontWeight.Bold,
                        color = ColorTopBarText
                    ) 
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = ColorTopBar
                )
            )
        },
        containerColor = ColorBackground
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            ProfilePhotoSection(
                imageUri = imageUri,
                onImageClick = {
                    launcher.launch("image/*")
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = nama,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "NIM: $nim",
                fontSize = 18.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.School,
                    contentDescription = "Jurusan",
                    tint = ColorAccentBlue,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = jurusan,
                    fontSize = 16.sp,
                    color = ColorAccentBlue,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            ContactInfoCard()

            Spacer(modifier = Modifier.height(24.dp))

            AcademicStatsCard()

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    editCount++
                    isEditing = !isEditing
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ColorAccentBlue,
                    contentColor = Color.Black
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = null,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Edit Profil",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

/**
 * ContactInfoCard - Card berisi informasi kontak (Static) sesuai gambar.
 */
@Composable
fun ContactInfoCard() {
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
                Icon(
                    imageVector = Icons.Default.Call,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Informasi Kontak",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            ContactRow(
                icon = Icons.Default.Email,
                label = "Email",
                value = "23083000111@unmer.student.ac.id"
            )

            Spacer(modifier = Modifier.height(12.dp))

            ContactRow(
                icon = Icons.Default.Phone,
                label = "Telepon",
                value = "+62 813-3151-68703"
            )

            Spacer(modifier = Modifier.height(12.dp))

            ContactRow(
                icon = Icons.Default.LocationOn,
                label = "Alamat",
                value = "Malang, Jawa Timur"
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreenPreview() {
    ProfilMahasiswaTheme(darkTheme = true) {
        ProfileScreen()
    }
}
