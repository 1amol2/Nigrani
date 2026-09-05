package com.dev.nigrani.ui.screens.inspections

import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.graphics.asImageBitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.clickable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.draw.clip
import androidx.core.content.FileProvider
import java.io.File

@Composable
fun EvidenceCaptureScreen(
    onBackClick: () -> Unit = {},
    onSubmitClick: (Uri) -> Unit = {}
) {

    val context = LocalContext.current

    var evidenceUri by remember {
        mutableStateOf<Uri?>(null)
    }

    var evidenceCategory by remember {
        mutableStateOf("Select category")
    }

    var pendingCameraUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val scrollState = rememberScrollState()

    /*
     * Creates a real image file inside the application's
     * external pictures directory.
     */
    fun createEvidenceUri(): Uri? {

        val picturesDir = context.getExternalFilesDir(
            Environment.DIRECTORY_PICTURES
        ) ?: return null

        val evidenceDir = File(
            picturesDir,
            "nigrani_evidence"
        )

        if (!evidenceDir.exists()) {
            evidenceDir.mkdirs()
        }

        val imageFile = File(
            evidenceDir,
            "evidence_${System.currentTimeMillis()}.jpg"
        )

        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            imageFile
        )
    }

    /*
     * TakePicture stores the full captured image into
     * the URI that we provide.
     */
    val cameraLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.TakePicture()
        ) { success ->

            if (success && pendingCameraUri != null) {
                evidenceUri = pendingCameraUri
            }
        }

    fun openCamera() {

        val uri = createEvidenceUri()

        if (uri != null) {
            pendingCameraUri = uri
            cameraLauncher.launch(uri)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        // ─────────────────────────────────────
        // TOP BAR
        // ─────────────────────────────────────

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(
                onClick = onBackClick
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }

            Text(
                text = "Evidence Capture",
                modifier = Modifier.weight(1f),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            IconButton(
                onClick = {}
            ) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notifications"
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            // ─────────────────────────────────────
            // INSTITUTE INFO
            // ─────────────────────────────────────

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                border = BorderStroke(
                    1.dp,
                    Color(0xFFE2E8F0)
                )
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(
                        text = "INSPECTION EVIDENCE",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF64748B),
                        letterSpacing = 0.5.sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Sunrise Rehabilitation Centre",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = Color(0xFF2563A6)
                        )

                        Spacer(modifier = Modifier.size(5.dp))

                        Text(
                            text = "Varanasi, Uttar Pradesh",
                            fontSize = 12.sp,
                            color = Color(0xFF64748B)
                        )
                    }
                }
            }

            // ─────────────────────────────────────
            // EVIDENCE STATUS
            // ─────────────────────────────────────

            val evidenceCaptured = evidenceUri != null

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor =
                        if (evidenceCaptured)
                            Color(0xFFF0FDF4)
                        else
                            Color(0xFFEFF6FF)
                ),
                border = BorderStroke(
                    1.dp,
                    if (evidenceCaptured)
                        Color(0xFFBBF7D0)
                    else
                        Color(0xFFBFDBFE)
                )
            ) {

                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector =
                            if (evidenceCaptured)
                                Icons.Default.CheckCircle
                            else
                                Icons.Default.AddAPhoto,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint =
                            if (evidenceCaptured)
                                Color(0xFF16A34A)
                            else
                                Color(0xFF2563A6)
                    )

                    Spacer(modifier = Modifier.size(10.dp))

                    Column {

                        Text(
                            text =
                                if (evidenceCaptured)
                                    "EVIDENCE CAPTURED"
                                else
                                    "EVIDENCE REQUIRED",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color =
                                if (evidenceCaptured)
                                    Color(0xFF166534)
                                else
                                    Color(0xFF1E5A92)
                        )

                        Spacer(modifier = Modifier.height(3.dp))

                        Text(
                            text =
                                if (evidenceCaptured)
                                    "1 evidence item captured"
                                else
                                    "Capture photographic evidence for this inspection.",
                            fontSize = 12.sp,
                            color = Color(0xFF64748B)
                        )
                    }
                }
            }

            // ─────────────────────────────────────
            // CATEGORY
            // ─────────────────────────────────────

            Text(
                text = "EVIDENCE CATEGORY",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF64748B),
                letterSpacing = 0.5.sp
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        evidenceCategory =
                            if (evidenceCategory == "Infrastructure")
                                "Beneficiary Attendance"
                            else
                                "Infrastructure"
                    },
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                border = BorderStroke(
                    1.dp,
                    Color(0xFFE2E8F0)
                )
            ) {

                Row(
                    modifier = Modifier.padding(15.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector = Icons.Default.Image,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = Color(0xFF2563A6)
                    )

                    Spacer(modifier = Modifier.size(10.dp))

                    Text(
                        text = evidenceCategory,
                        modifier = Modifier.weight(1f),
                        fontSize = 14.sp,
                        fontWeight =
                            if (evidenceCategory != "Select category")
                                FontWeight.SemiBold
                            else
                                FontWeight.Normal,
                        color =
                            if (evidenceCategory != "Select category")
                                Color(0xFF1E293B)
                            else
                                Color(0xFF94A3B8)
                    )

                    Text(
                        text = "Tap to change",
                        fontSize = 10.sp,
                        color = Color(0xFF94A3B8)
                    )
                }
            }

            // ─────────────────────────────────────
            // PHOTOGRAPHIC EVIDENCE
            // ─────────────────────────────────────

            Text(
                text = "PHOTOGRAPHIC EVIDENCE",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF64748B),
                letterSpacing = 0.5.sp
            )

            if (evidenceUri == null) {

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFE8EEF3)
                    ),
                    border = BorderStroke(
                        1.dp,
                        Color(0xFFD5DEE7)
                    )
                ) {

                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Box(
                                modifier = Modifier
                                    .size(64.dp)
                                    .background(
                                        Color.White,
                                        RoundedCornerShape(18.dp)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {

                                Icon(
                                    imageVector = Icons.Default.AddAPhoto,
                                    contentDescription = null,
                                    modifier = Modifier.size(32.dp),
                                    tint = Color(0xFF174A7E)
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = "No evidence captured",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF334155)
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = "Capture a photo using the inspection camera.",
                                fontSize = 11.sp,
                                color = Color(0xFF64748B)
                            )

                            Spacer(modifier = Modifier.height(14.dp))

                            Button(
                                onClick = {
                                    openCamera()
                                },
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF174A7E)
                                )
                            ) {

                                Icon(
                                    imageVector = Icons.Default.AddAPhoto,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )

                                Spacer(modifier = Modifier.size(7.dp))

                                Text(
                                    text = "CAPTURE PHOTO",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }

            } else {

                // ─────────────────────────────────
                // REAL PHOTO PREVIEW
                // ─────────────────────────────────

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(260.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Black
                    )
                ) {

                    AndroidPhotoPreview(
                        uri = evidenceUri!!,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(16.dp))
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    OutlinedButton(
                        onClick = {
                            openCamera()
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp)
                    ) {

                        Icon(
                            imageVector = Icons.Default.AddAPhoto,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )

                        Spacer(modifier = Modifier.size(7.dp))

                        Text(
                            text = "RETAKE",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    OutlinedButton(
                        onClick = {
                            evidenceUri = null
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp)
                    ) {

                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )

                        Spacer(modifier = Modifier.size(7.dp))

                        Text(
                            text = "REMOVE",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // ─────────────────────────────────────
            // METADATA
            // ─────────────────────────────────────

            if (evidenceUri != null) {

                Text(
                    text = "EVIDENCE METADATA",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF64748B),
                    letterSpacing = 0.5.sp
                )

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    border = BorderStroke(
                        1.dp,
                        Color(0xFFE2E8F0)
                    )
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {

                        MetadataRow(
                            icon = Icons.Default.LocationOn,
                            label = "GPS location",
                            value = "Verified • 18 metres"
                        )

                        MetadataRow(
                            icon = Icons.Default.Schedule,
                            label = "Captured",
                            value = "05 Sep 2026, 14:32"
                        )

                        MetadataRow(
                            icon = Icons.Default.CheckCircle,
                            label = "Evidence status",
                            value = "Authentic capture"
                        )

                        MetadataRow(
                            icon = Icons.Default.Image,
                            label = "Storage",
                            value = "Local evidence URI ready"
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(2.dp))

            // ─────────────────────────────────────
            // SUBMIT
            // ─────────────────────────────────────

            Button(
                onClick = {

                    evidenceUri?.let { uri ->
                        onSubmitClick(uri)
                    }

                },
                enabled = evidenceUri != null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF174A7E),
                    disabledContainerColor = Color(0xFFCBD5E1),
                    disabledContentColor = Color(0xFF64748B)
                )
            ) {

                Text(
                    text = "SUBMIT EVIDENCE",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun AndroidPhotoPreview(
    uri: Uri,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current

    var bitmap by remember(uri) {
        mutableStateOf<android.graphics.Bitmap?>(null)
    }

    LaunchedEffect(uri) {

        bitmap = try {

            context.contentResolver.openInputStream(uri)?.use {
                BitmapFactory.decodeStream(it)
            }

        } catch (e: Exception) {
            null
        }
    }

    if (bitmap != null) {

        Image(
            bitmap = bitmap!!.asImageBitmap(),
            contentDescription = "Captured evidence",
            modifier = modifier,
            contentScale = ContentScale.Crop
        )

    } else {

        Box(
            modifier = modifier
                .background(Color(0xFFE2E8F0)),
            contentAlignment = Alignment.Center
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Icon(
                    imageVector = Icons.Default.Image,
                    contentDescription = null,
                    modifier = Modifier.size(42.dp),
                    tint = Color(0xFF64748B)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Unable to preview image",
                    fontSize = 12.sp,
                    color = Color(0xFF64748B)
                )
            }
        }
    }
}

@Composable
private fun MetadataRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = Color(0xFF2563A6)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {

            Text(
                text = label,
                fontSize = 11.sp,
                color = Color(0xFF64748B)
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = value,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF1E293B)
            )
        }
    }
}