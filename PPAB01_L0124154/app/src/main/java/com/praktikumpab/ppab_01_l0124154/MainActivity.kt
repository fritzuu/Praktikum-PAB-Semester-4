package com.praktikumpab.ppab_01_l0124154

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.praktikumpab.ppab_01_l0124154.ui.theme.PPAB01_L0124154Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PPAB01_L0124154Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SoloExplorerApp()
                }
            }
        }
    }
}

@Composable
fun SoloExplorerApp() {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Keraton Surakarta Hadiningrat",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Istana resmi Kesunanan Surakarta yang terletak di Kota Surakarta. Tempat wisata bersejarah yang wajib dikunjungi.",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Aksi Interaktif:",
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Button(
            onClick = {
                val gmmIntentUri = Uri.parse("geo:-7.5775,110.8283?q=Keraton+Surakarta")
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                context.startActivity(mapIntent)
            },
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
        ) {
            Icon(Icons.Filled.LocationOn, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text("Lihat di Maps")
        }

        Button(
            onClick = {
                val dialIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:0271644786"))
                context.startActivity(dialIntent)
            },
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
        ) {
            Icon(Icons.Filled.Call, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text("Hubungi Pengelola")
        }

        Button(
            onClick = {
                val sendIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "Ayo liburan ke Keraton Surakarta!")
                    type = "text/plain"
                }
                context.startActivity(Intent.createChooser(sendIntent, "Bagikan via..."))
            },
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
        ) {
            Icon(Icons.Filled.Share, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text("Bagikan Info")
        }

        Button(
            onClick = {
                val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://pariwisatasolo.surakarta.go.id/"))
                context.startActivity(webIntent)
            },
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
        ) {
            Icon(Icons.Filled.Info, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text("Buka Website")
        }
    }
}
