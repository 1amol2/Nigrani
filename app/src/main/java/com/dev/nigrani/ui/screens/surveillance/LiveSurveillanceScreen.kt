package com.dev.nigrani.ui.screens.surveillance

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.outlined.Business
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


// ============================================================
// COLORS
// ============================================================

private val Navy = Color(0xFF123B63)
private val Blue = Color(0xFF21639D)
private val Green = Color(0xFF159A72)
private val Red = Color(0xFFD23838)

private val Background = Color(0xFFF6F8FB)
private val SurfaceWhite = Color.White

private val SoftBlue = Color(0xFFEAF3FA)
private val SoftGreen = Color(0xFFE8F7F1)
private val SoftRed = Color(0xFFFFEEEE)

private val TextGray = Color(0xFF7C8794)
private val Border = Color(0xFFE1E6EC)

private val VideoBackground = Color(0xFF142A3A)


// ============================================================
// CAMERA MODEL
// ============================================================

private data class Camera(
    val number: String,
    val name: String,
    val online: Boolean,
    val location: String
)

private val cameras = listOf(

    Camera(
        number = "01",
        name = "Main Hall",
        online = true,
        location = "Main Building"
    ),

    Camera(
        number = "02",
        name = "Entrance",
        online = true,
        location = "Front Gate"
    ),

    Camera(
        number = "03",
        name = "Activity Area",
        online = false,
        location = "Block B"
    ),

    Camera(
        number = "04",
        name = "Dormitory",
        online = true,
        location = "Residential Block"
    )
)


// ============================================================
// MAIN SCREEN
// ============================================================

@Composable
fun LiveSurveillanceScreen(
    onBackClick: () -> Unit = {},
    onOverviewClick: () -> Unit = {},
    onAlertsClick: () -> Unit = {}
) {

    var selectedCamera by remember {
        mutableStateOf(cameras.first())
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Background,

        bottomBar = {
            SurveillanceBottomBar(
                onOverviewClick = onOverviewClick,
                onAlertsClick = onAlertsClick
            )
        }

    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),

            contentPadding = PaddingValues(
                start = 18.dp,
                end = 18.dp,
                top = 12.dp,
                bottom = 24.dp
            ),

            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {

            item {

                SurveillanceHeader(
                    onBackClick = onBackClick
                )
            }

            item {
                InstituteInfo()
            }

            item {
                CameraFeed(
                    camera = selectedCamera
                )
            }

            item {

                CameraSelector(
                    cameras = cameras,
                    selectedCamera = selectedCamera,
                    onCameraSelected = {
                        selectedCamera = it
                    }
                )
            }

            item {
                AiMonitoring()
            }

            item {
                MonitoringLocation()
            }
        }
    }
}


// ============================================================
// HEADER
// ============================================================

@Composable
private fun SurveillanceHeader(
    onBackClick: () -> Unit
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(RoundedCornerShape(13.dp))
                .background(SurfaceWhite)
                .clickable {
                    onBackClick()
                },

            contentAlignment = Alignment.Center
        ) {

            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Navy,
                modifier = Modifier.size(21.dp)
            )
        }

        Spacer(
            modifier = Modifier.width(12.dp)
        )

        Column(
            modifier = Modifier.weight(1f)
        ) {

            Text(
                text = "Live Surveillance",
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold,
                color = Navy
            )

            Text(
                text = "Real-time camera monitoring",
                fontSize = 12.sp,
                color = TextGray
            )
        }

        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(RoundedCornerShape(13.dp))
                .background(SurfaceWhite),

            contentAlignment = Alignment.Center
        ) {

            Icon(
                imageVector = Icons.Outlined.Notifications,
                contentDescription = "Notifications",
                tint = Navy,
                modifier = Modifier.size(21.dp)
            )

            Box(
                modifier = Modifier
                    .padding(5.dp)
                    .size(7.dp)
                    .clip(CircleShape)
                    .background(Red)
                    .align(Alignment.TopEnd)
            )
        }
    }
}


// ============================================================
// INSTITUTE INFORMATION
// ============================================================

@Composable
private fun InstituteInfo() {

    Card(
        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(16.dp),

        colors = CardDefaults.cardColors(
            containerColor = SurfaceWhite
        ),

        border = BorderStroke(
            1.dp,
            Border
        )
    ) {

        Row(
            modifier = Modifier.padding(13.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(11.dp))
                    .background(SoftBlue),

                contentAlignment = Alignment.Center
            ) {

                Icon(
                    imageVector = Icons.Outlined.Business,
                    contentDescription = null,
                    tint = Blue,
                    modifier = Modifier.size(22.dp)
                )
            }

            Spacer(
                modifier = Modifier.width(10.dp)
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = "Sunrise Rehabilitation Centre",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Navy
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = TextGray,
                        modifier = Modifier.size(13.dp)
                    )

                    Spacer(
                        modifier = Modifier.width(3.dp)
                    )

                    Text(
                        text = "Varanasi, Uttar Pradesh",
                        fontSize = 10.sp,
                        color = TextGray
                    )
                }
            }

            Column(
                horizontalAlignment = Alignment.End
            ) {

                Text(
                    text = "3 / 4",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Navy
                )

                Text(
                    text = "Cameras online",
                    fontSize = 9.sp,
                    color = TextGray
                )
            }
        }
    }
}


// ============================================================
// CAMERA FEED
// ============================================================

@Composable
private fun CameraFeed(
    camera: Camera
) {

    Column {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Camera ${camera.number}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Navy,
                modifier = Modifier.weight(1f)
            )

            if (camera.online) {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(Green)
                    )

                    Spacer(
                        modifier = Modifier.width(5.dp)
                    )

                    Text(
                        text = "LIVE",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Green
                    )
                }
            }
        }

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),

            shape = RoundedCornerShape(18.dp),

            colors = CardDefaults.cardColors(
                containerColor = VideoBackground
            ),

            border = BorderStroke(
                1.dp,
                Border
            )
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(215.dp)
                    .background(VideoBackground),

                contentAlignment = Alignment.Center
            ) {

                if (camera.online) {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Box(
                            modifier = Modifier
                                .size(58.dp)
                                .clip(CircleShape)
                                .background(
                                    Color.White.copy(alpha = 0.12f)
                                ),

                            contentAlignment = Alignment.Center
                        ) {

                            Icon(
                                imageVector = Icons.Outlined.PlayArrow,
                                contentDescription = "Play camera",
                                tint = Color.White,
                                modifier = Modifier.size(32.dp)
                            )
                        }

                        Spacer(
                            modifier = Modifier.height(10.dp)
                        )

                        Text(
                            text = camera.name,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )

                        Spacer(
                            modifier = Modifier.height(4.dp)
                        )

                        Text(
                            text = "Live video feed",
                            fontSize = 11.sp,
                            color = Color(0xFFB8C7D3)
                        )
                    }

                    Row(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(12.dp),

                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF48D597))
                        )

                        Spacer(
                            modifier = Modifier.width(5.dp)
                        )

                        Text(
                            text = "LIVE",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    Text(
                        text = "1080p",
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(12.dp),

                        fontSize = 9.sp,
                        color = Color.White
                    )

                } else {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Icon(
                            imageVector = Icons.Default.Videocam,
                            contentDescription = null,
                            tint = Color(0xFF81909C),
                            modifier = Modifier.size(42.dp)
                        )

                        Spacer(
                            modifier = Modifier.height(9.dp)
                        )

                        Text(
                            text = "Camera Offline",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )

                        Spacer(
                            modifier = Modifier.height(4.dp)
                        )

                        Text(
                            text = "No live feed available",
                            fontSize = 11.sp,
                            color = Color(0xFF9EADB8)
                        )
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SurfaceWhite)
                    .padding(12.dp),

                verticalAlignment = Alignment.CenterVertically
            ) {

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = camera.name,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Navy
                    )

                    Text(
                        text = camera.location,
                        fontSize = 10.sp,
                        color = TextGray
                    )
                }

                Icon(
                    imageVector = Icons.Outlined.Refresh,
                    contentDescription = "Refresh feed",
                    tint = Blue,
                    modifier = Modifier.size(19.dp)
                )
            }
        }
    }
}


// ============================================================
// CAMERA SELECTOR
// ============================================================

@Composable
private fun CameraSelector(
    cameras: List<Camera>,
    selectedCamera: Camera,
    onCameraSelected: (Camera) -> Unit
) {

    Column {

        Text(
            text = "Cameras",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Navy
        )

        Spacer(
            modifier = Modifier.height(9.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            cameras.forEach { camera ->

                CameraTile(
                    camera = camera,
                    selected = camera.number == selectedCamera.number,

                    onClick = {
                        onCameraSelected(camera)
                    },

                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}


// ============================================================
// CAMERA TILE
// ============================================================

@Composable
private fun CameraTile(
    camera: Camera,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier
) {

    val background = when {

        selected -> Navy

        camera.online -> SurfaceWhite

        else -> SoftRed
    }

    val titleColor = when {

        selected -> Color.White

        camera.online -> Navy

        else -> Red
    }

    Card(
        modifier = modifier
            .clickable {
                onClick()
            },

        shape = RoundedCornerShape(12.dp),

        colors = CardDefaults.cardColors(
            containerColor = background
        ),

        border = BorderStroke(
            1.dp,
            if (selected) Navy else Border
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 9.dp,
                    horizontal = 5.dp
                ),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(
                        if (!camera.online) Red else Green
                    )
            )

            Spacer(
                modifier = Modifier.height(5.dp)
            )

            Text(
                text = camera.number,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = titleColor
            )

            Text(
                text = if (camera.online) "Online" else "Offline",
                fontSize = 8.sp,
                color = if (selected) {
                    Color(0xFFD6E3ED)
                } else {
                    TextGray
                }
            )
        }
    }
}


// ============================================================
// AI MONITORING
// ============================================================

@Composable
private fun AiMonitoring() {

    Column {

        Text(
            text = "AI Monitoring",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Navy
        )

        Spacer(
            modifier = Modifier.height(9.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),

            shape = RoundedCornerShape(17.dp),

            colors = CardDefaults.cardColors(
                containerColor = SurfaceWhite
            ),

            border = BorderStroke(
                1.dp,
                Border
            )
        ) {

            Column(
                modifier = Modifier.padding(14.dp)
            ) {

                AiAlertRow(
                    title = "Attendance anomaly",
                    description = "Detected attendance below reported level",
                    color = Red,
                    background = SoftRed,
                    active = true
                )

                Spacer(
                    modifier = Modifier.height(11.dp)
                )

                AiAlertRow(
                    title = "Crowd density",
                    description = "No abnormal crowd detected",
                    color = Green,
                    background = SoftGreen,
                    active = false
                )

                Spacer(
                    modifier = Modifier.height(11.dp)
                )

                AiAlertRow(
                    title = "Restricted area",
                    description = "No unauthorized movement detected",
                    color = Green,
                    background = SoftGreen,
                    active = false
                )

                Spacer(
                    modifier = Modifier.height(11.dp)
                )

                AiAlertRow(
                    title = "Camera tampering",
                    description = "All active cameras functioning normally",
                    color = Green,
                    background = SoftGreen,
                    active = false
                )
            }
        }
    }
}


// ============================================================
// AI ALERT ROW
// ============================================================

@Composable
private fun AiAlertRow(
    title: String,
    description: String,
    color: Color,
    background: Color,
    active: Boolean
) {

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(38.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(background),

            contentAlignment = Alignment.Center
        ) {

            if (active) {

                Icon(
                    imageVector = Icons.Default.Error,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(20.dp)
                )

            } else {

                Box(
                    modifier = Modifier
                        .size(9.dp)
                        .clip(CircleShape)
                        .background(color)
                )
            }
        }

        Spacer(
            modifier = Modifier.width(10.dp)
        )

        Column(
            modifier = Modifier.weight(1f)
        ) {

            Text(
                text = title,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = Navy
            )

            Text(
                text = description,
                fontSize = 10.sp,
                color = TextGray
            )
        }

        Text(
            text = if (active) "ALERT" else "NORMAL",
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            color = color
        )
    }
}


// ============================================================
// LOCATION
// ============================================================

@Composable
private fun MonitoringLocation() {

    Card(
        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(16.dp),

        colors = CardDefaults.cardColors(
            containerColor = SurfaceWhite
        ),

        border = BorderStroke(
            1.dp,
            Border
        )
    ) {

        Row(
            modifier = Modifier.padding(13.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(SoftBlue),

                contentAlignment = Alignment.Center
            ) {

                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = Blue,
                    modifier = Modifier.size(21.dp)
                )
            }

            Spacer(
                modifier = Modifier.width(10.dp)
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = "Monitoring Location",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Navy
                )

                Text(
                    text = "Sunrise Rehabilitation Centre",
                    fontSize = 10.sp,
                    color = TextGray
                )

                Text(
                    text = "Varanasi, Uttar Pradesh",
                    fontSize = 10.sp,
                    color = TextGray
                )
            }

            Text(
                text = "LIVE",
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                color = Green
            )
        }
    }
}


// ============================================================
// BOTTOM NAVIGATION
// ============================================================

@Composable
private fun SurveillanceBottomBar(
    onOverviewClick: () -> Unit,
    onAlertsClick: () -> Unit
) {

    NavigationBar(
        modifier = Modifier.navigationBarsPadding(),
        containerColor = SurfaceWhite,
        tonalElevation = 0.dp
    ) {

        // OVERVIEW
        NavigationBarItem(
            selected = false,

            onClick = {
                onOverviewClick()
            },

            icon = {
                Icon(
                    imageVector = Icons.Outlined.Business,
                    contentDescription = "Overview"
                )
            },

            label = {
                Text("Overview")
            }
        )

        // CCTV
        NavigationBarItem(
            selected = true,

            onClick = {},

            icon = {
                Icon(
                    imageVector = Icons.Default.Videocam,
                    contentDescription = "CCTV"
                )
            },

            label = {
                Text("CCTV")
            }
        )

        // ALERTS
        NavigationBarItem(
            selected = false,

            onClick = {
                onAlertsClick()
            },

            icon = {
                Icon(
                    imageVector = Icons.Default.Error,
                    contentDescription = "Alerts"
                )
            },

            label = {
                Text("Alerts")
            }
        )
    }
}