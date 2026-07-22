package com.example.profilmahasiswa.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.profilmahasiswa.ui.theme.ProfilMahasiswaTheme

/**
 * ProfileEditScreen - Halaman utama profil mahasiswa mode edit dengan UI sesuai gambar.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileEditScreen(
    paddingValues: PaddingValues = PaddingValues(0.dp),
    onNavigateToGrades: () -> Unit = {}
) {
    var editCount by remember { mutableIntStateOf(0) }
    var isEditing by remember { mutableStateOf(false) }

    var email by remember { mutableStateOf("23083000111@unmer.student.ac.id") }
    var phone by remember { mutableStateOf("+62 813-3151-68703") }
    var address by remember { mutableStateOf("Malang, Jawa Timur") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    val nama = "Fabio Ilham M"
    val nim = "23083000111"
    val jurusan = "SIstem Informasi"

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
                .padding(bottom = paddingValues.calculateBottomPadding()) // Tambahan padding Navbar
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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

            ContactEditCard(
                email = email,
                phone = phone,
                address = address,
                isEditing = isEditing,
                onEmailChange = { email = it },
                onPhoneChange = { phone = it },
                onAddressChange = { address = it }
            )

            Spacer(modifier = Modifier.height(24.dp))

            AcademicStatsCard()

            Spacer(modifier = Modifier.height(32.dp))

            // TOMBOL EDIT PROFIL
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
                    containerColor = if (isEditing)
                        MaterialTheme.colorScheme.error
                    else
                        ColorAccentBlue,
                    contentColor = if (isEditing) Color.White else Color.Black
                )
            ) {
                Icon(
                    imageVector = if (isEditing) Icons.Default.Close else Icons.Default.Edit,
                    contentDescription = null,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (isEditing) "Batal Edit" else "Edit Profil",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // TOMBOL DATA NILAI
            OutlinedButton(
                onClick = onNavigateToGrades,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(2.dp, ColorAccentBlue),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = ColorAccentBlue
                )
            ) {
                Icon(
                    imageVector = Icons.Default.ListAlt,
                    contentDescription = null,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Data Nilai Mahasiswa",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

/**
 * ContactEditCard - Card berisi informasi kontak dengan mode edit sesuai gambar.
 */
@Composable
fun ContactEditCard(
    email: String,
    phone: String,
    address: String,
    isEditing: Boolean,
    onEmailChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    onAddressChange: (String) -> Unit
) {
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

            if (isEditing) {
                EditableContactRow(
                    icon = Icons.Default.Email,
                    label = "Email",
                    value = email,
                    onValueChange = onEmailChange
                )
                Spacer(modifier = Modifier.height(12.dp))

                EditableContactRow(
                    icon = Icons.Default.Phone,
                    label = "Telepon",
                    value = phone,
                    onValueChange = onPhoneChange
                )
                Spacer(modifier = Modifier.height(12.dp))

                EditableContactRow(
                    icon = Icons.Default.LocationOn,
                    label = "Alamat",
                    value = address,
                    onValueChange = onAddressChange
                )
            } else {
                ContactRow(
                    icon = Icons.Default.Email,
                    label = "Email",
                    value = email
                )
                Spacer(modifier = Modifier.height(12.dp))

                ContactRow(
                    icon = Icons.Default.Phone,
                    label = "Telepon",
                    value = phone
                )
                Spacer(modifier = Modifier.height(12.dp))

                ContactRow(
                    icon = Icons.Default.LocationOn,
                    label = "Alamat",
                    value = address
                )
            }
        }
    }
}

@Composable
fun EditableContactRow(
    icon: ImageVector,
    label: String,
    value: String,
    onValueChange: (String) -> Unit
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

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label, color = Color.Gray) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedBorderColor = ColorAccentBlue,
                unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f),
                cursorColor = ColorAccentBlue
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileEditPreview() {
    ProfilMahasiswaTheme(darkTheme = true) {
        ProfileEditScreen()
    }
}
