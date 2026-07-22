package com.example.profilmahasiswa.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.profilmahasiswa.ui.theme.ProfilMahasiswaTheme
import java.io.File
import java.io.FileOutputStream

/**
 * ProfileEditScreen - Halaman utama profil mahasiswa mode edit dengan UI sesuai gambar.
 * Ditambahkan: Hobby Section, Share Button, dan Dark Mode Toggle.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileEditScreen(
    paddingValues: PaddingValues = PaddingValues(0.dp),
    onNavigateToGrades: () -> Unit = {},
    isDarkMode: Boolean = true,
    onThemeToggle: (Boolean) -> Unit = {},
) {
    var editCount by remember { mutableIntStateOf(0) }
    var isEditing by remember { mutableStateOf(false) }
    val context = LocalContext.current

    var email by rememberSaveable { mutableStateOf("23083000111@unmer.student.ac.id") }
    var phone by rememberSaveable { mutableStateOf("+62 813-3151-68703") }
    var address by rememberSaveable { mutableStateOf("Malang, Jawa Timur") }
    
    // Custom saver untuk Uri agar bisa disimpan di rememberSaveable
    val uriSaver = Saver<Uri?, String>(
        save = { it?.toString() },
        restore = { it?.let { Uri.parse(it) } }
    )
    
    // Inisialisasi awal: cek apakah sudah ada file di internal storage
    var imageUri by rememberSaveable(stateSaver = uriSaver) { 
        val file = File(context.filesDir, "profile_picture.jpg")
        val initialUri = if (file.exists()) Uri.fromFile(file) else null
        mutableStateOf(initialUri)
    }

    // Fungsi untuk menyimpan gambar ke storage internal aplikasi
    fun saveImageToInternalStorage(context: Context, uri: Uri): Uri? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
            val file = File(context.filesDir, "profile_picture.jpg")
            val outputStream = FileOutputStream(file)
            inputStream?.use { input ->
                outputStream.use { output ->
                    input.copyTo(output)
                }
            }
            Uri.fromFile(file)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val localUri = saveImageToInternalStorage(context, it)
            if (localUri != null) {
                // Tambahkan query parameter unik agar Coil menganggap ini data baru
                // sehingga foto langsung berubah di layar saat itu juga
                val updatedUri = Uri.parse(localUri.toString() + "?t=" + System.currentTimeMillis())
                imageUri = updatedUri
            }
        }
    }

    val nama = "Fabio Ilham M"
    val nim = "23083000111"
    val jurusan = "Sistem Informasi"
    val hobbies = listOf("Coding", "Gaming", "Music", "Photography")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "Profil Mahasiswa", 
                            fontWeight = FontWeight.Bold,
                            color = if (isDarkMode) ColorTopBarText else Color.Black
                        )
                        // Dark Mode Toggle menggunakan IconButton (Ikon Bulan & Matahari)
                        IconButton(onClick = { onThemeToggle(!isDarkMode) }) {
                            Icon(
                                imageVector = if (isDarkMode) Icons.Default.WbSunny else Icons.Default.NightsStay,
                                contentDescription = "Toggle Theme",
                                tint = if (isDarkMode) Color.Yellow else Color.Black
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = if (isDarkMode) ColorTopBar else MaterialTheme.colorScheme.primaryContainer
                )
            )
        },
        containerColor = if (isDarkMode) ColorBackground else MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(bottom = paddingValues.calculateBottomPadding())
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
                color = if (isDarkMode) Color.White else Color.Black
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
                onAddressChange = { address = it },
                isDarkMode = isDarkMode
            )

            Spacer(modifier = Modifier.height(24.dp))

            // HOBBY SECTION
            HobbyCard(hobbies = hobbies, isDarkMode = isDarkMode)

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
            Button(
                onClick = onNavigateToGrades,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ColorCardBackground,
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

            Spacer(modifier = Modifier.height(12.dp))

            // TOMBOL SHARE PROFIL (OutlinedButton)
            OutlinedButton(
                onClick = {
                    val sendIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, "Profil Mahasiswa: $nama ($nim) - $jurusan")
                        type = "text/plain"
                    }
                    val shareIntent = Intent.createChooser(sendIntent, null)
                    context.startActivity(shareIntent)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(2.dp, ColorAccentBlue),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = ColorAccentBlue
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = null,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Share Profil",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

/**
 * HobbyCard - Menampilkan daftar hobi.
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun HobbyCard(hobbies: List<String>, isDarkMode: Boolean) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isDarkMode) ColorCardBackground else MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Hobi",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isDarkMode) Color.White else Color.Black
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Menggunakan FlowRow agar hobi bisa pindah ke baris baru jika tidak cukup tempat
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                for (hobby in hobbies) {
                    AssistChip(
                        onClick = { },
                        label = { 
                            Text(
                                text = hobby,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            ) 
                        },
                        colors = AssistChipDefaults.assistChipColors(
                            labelColor = if (isDarkMode) ColorAccentBlue else MaterialTheme.colorScheme.primary,
                            containerColor = if (isDarkMode) ColorInnerRowBackground else MaterialTheme.colorScheme.surface
                        ),
                        border = BorderStroke(
                            width = 1.dp,
                            color = if (isDarkMode) ColorAccentBlue.copy(alpha = 0.5f) else MaterialTheme.colorScheme.outline
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )
                }
            }
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
    onAddressChange: (String) -> Unit,
    isDarkMode: Boolean
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isDarkMode) ColorCardBackground else MaterialTheme.colorScheme.surfaceVariant
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
                    color = if (isDarkMode) Color.White else Color.Black
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            if (isEditing) {
                EditableContactRow(
                    icon = Icons.Default.Email,
                    label = "Email",
                    value = email,
                    onValueChange = onEmailChange,
                    isDarkMode = isDarkMode
                )
                Spacer(modifier = Modifier.height(12.dp))

                EditableContactRow(
                    icon = Icons.Default.Phone,
                    label = "Telepon",
                    value = phone,
                    onValueChange = onPhoneChange,
                    isDarkMode = isDarkMode
                )
                Spacer(modifier = Modifier.height(12.dp))

                EditableContactRow(
                    icon = Icons.Default.LocationOn,
                    label = "Alamat",
                    value = address,
                    onValueChange = onAddressChange,
                    isDarkMode = isDarkMode
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
    onValueChange: (String) -> Unit,
    isDarkMode: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = if (isDarkMode) ColorInnerRowBackground else MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(if (isDarkMode) Color(0xFF003D6D) else MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = if (isDarkMode) ColorAccentBlue else MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label, color = if (isDarkMode) Color.Gray else MaterialTheme.colorScheme.onSurfaceVariant) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = if (isDarkMode) Color.White else Color.Black,
                unfocusedTextColor = if (isDarkMode) Color.White else Color.Black,
                focusedBorderColor = if (isDarkMode) ColorAccentBlue else MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f),
                cursorColor = if (isDarkMode) ColorAccentBlue else MaterialTheme.colorScheme.primary
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
