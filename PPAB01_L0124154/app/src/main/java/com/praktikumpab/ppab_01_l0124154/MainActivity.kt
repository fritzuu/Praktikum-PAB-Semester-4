package com.praktikumpab.ppab_01_l0124154 // Pastikan package name-nya sudah benar sesuai project-mu

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                // Background utama menggunakan warna Krem ala kain mori Batik
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFFDFBF7)
                ) {
                    SoloModernApp()
                }
            }
        }
    }
}

@Composable
fun SoloModernApp() {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    // Warna Kustom "Sogan Solo"
    val SoganBrown = Color(0xFF4A3728)
    val GoldAccent = Color(0xFFD4AF37)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState) // Bikin layar bisa di-scroll
    ) {
        // --- 1. HERO SECTION ---
        Box(modifier = Modifier.fillMaxWidth().height(280.dp)) {
            Image(
                painter = painterResource(id = R.drawable.keraton), // Pastikan file keraton.jpg ada!
                contentDescription = "Keraton Surakarta",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, SoganBrown.copy(alpha = 0.9f)),
                            startY = 300f
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(20.dp)
            ) {
                Text(
                    text = "Keraton Surakarta",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )
                Text(
                    text = "Jantung Budaya Jawa, Spirit of Java",
                    fontSize = 14.sp,
                    fontStyle = FontStyle.Italic,
                    color = GoldAccent
                )
            }
        }

        // --- 2. DESKRIPSI SECTION ---
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .offset(y = (-30).dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Tentang Destinasi",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = SoganBrown
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Istana megah yang dibangun pada tahun 1745 oleh Pakubuwono II. Jelajahi arsitektur memukau, museum benda pusaka, hingga tradisi yang masih hidup berdampingan dengan modernisasi kota Solo.",
                    fontSize = 14.sp,
                    lineHeight = 22.sp,
                    color = Color.DarkGray,
                    textAlign = TextAlign.Justify
                )
            }
        }

        // --- 3. INTERACTIVE ACTIONS ---
        Column(modifier = Modifier.padding(horizontal = 20.dp).offset(y = (-15).dp)) {
            Text(
                text = "Pusat Layanan Turis",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = SoganBrown,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // Baris 1: Maps & Call
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                ModernIntentButton(
                    modifier = Modifier.weight(1f),
                    text = "Rute Lokasi",
                    icon = Icons.Filled.LocationOn,
                    color = SoganBrown,
                    onClick = {
                        val gmmIntentUri = Uri.parse("geo:-7.5775,110.8283?q=Keraton+Surakarta")
                        context.startActivity(Intent(Intent.ACTION_VIEW, gmmIntentUri))
                    }
                )
                ModernIntentButton(
                    modifier = Modifier.weight(1f),
                    text = "Telepon",
                    icon = Icons.Filled.Call,
                    color = SoganBrown,
                    onClick = {
                        context.startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:0271644786")))
                    }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Baris 2: Share & Web
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                ModernIntentButton(
                    modifier = Modifier.weight(1f),
                    text = "Bagikan",
                    icon = Icons.Filled.Share,
                    color = GoldAccent,
                    textColor = SoganBrown,
                    onClick = {
                        val sendIntent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, "Eksplorasi cantiknya Keraton Surakarta! Wajib mampir kalau ke Solo. \uD83C\uDFEF")
                            type = "text/plain"
                        }
                        context.startActivity(Intent.createChooser(sendIntent, "Bagikan via..."))
                    }
                )
                ModernIntentButton(
                    modifier = Modifier.weight(1f),
                    text = "Website",
                    icon = Icons.Filled.Info,
                    color = SoganBrown,
                    onClick = {
                        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://pariwisatasolo.surakarta.go.id/")))
                    }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Baris 3: YouTube & Email
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                ModernIntentButton(
                    modifier = Modifier.weight(1f),
                    text = "Video Tur",
                    icon = Icons.Filled.PlayArrow,
                    color = Color(0xFFD32F2F),
                    onClick = {
                        val ytIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/results?search_query=dokumenter+keraton+surakarta"))
                        context.startActivity(ytIntent)
                    }
                )
                ModernIntentButton(
                    modifier = Modifier.weight(1f),
                    text = "Email Admin",
                    icon = Icons.Filled.Email,
                    color = SoganBrown,
                    onClick = {
                        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                            data = Uri.parse("mailto:info@pariwisatasolo.go.id")
                            putExtra(Intent.EXTRA_SUBJECT, "Tanya Info Wisata Keraton")
                        }
                        context.startActivity(emailIntent)
                    }
                )
            }
        }

        // --- 4. FOOTER SECTION (BARU) ---
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Created by Revan © 2026",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp), // Memberikan ruang di bawah agar nyaman di-scroll
            textAlign = TextAlign.Center, // Tulisan persis di tengah
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = SoganBrown.copy(alpha = 0.6f) // Memakai warna cokelat Sogan tapi agak transparan (60%) agar estetik
        )
    }
}

// Custom Composable untuk tombol
@Composable
fun ModernIntentButton(
    modifier: Modifier = Modifier,
    text: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
    textColor: Color = Color.White,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(50.dp),
        colors = ButtonDefaults.buttonColors(containerColor = color),
        shape = RoundedCornerShape(12.dp)
    ) {
        Icon(icon, contentDescription = text, tint = textColor, modifier = Modifier.size(18.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, color = textColor, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
    }
}