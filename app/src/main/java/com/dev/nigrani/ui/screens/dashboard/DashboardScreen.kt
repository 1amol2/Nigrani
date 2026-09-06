package com.dev.nigrani.ui.screens.dashboard

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apartment
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Business
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp



// ============================================================================
// COLORS
// ============================================================================

private val Navy = Color(0xFF174A7E)
private val Blue = Color(0xFF2563A6)
private val Green = Color(0xFF159A72)
private val Red = Color(0xFFD23838)
private val Amber = Color(0xFFD99520)

private val Background = Color(0xFFF7F9FC)
private val SurfaceColor = Color.White

private val SoftBlue = Color(0xFFEAF3FA)
private val SoftGreen = Color(0xFFEAF8F3)
private val SoftRed = Color(0xFFFFEEEE)
private val SoftAmber = Color(0xFFFFF6E6)

private val TextGray = Color(0xFF687687)
private val Border = Color(0xFFE1E7EE)


// ============================================================================
// DASHBOARD SCREEN
// ============================================================================

@Composable
fun DashboardScreen(
    onInstitutesClick: () -> Unit = {},
    onInspectionClick: () -> Unit = {},
    onAlertsClick: () -> Unit = {},
    onReportsClick: () -> Unit = {},
    onSurveillanceClick: () -> Unit = {}
) {

    var selectedItem by remember {
        mutableIntStateOf(0)
    }

    val scrollState = rememberScrollState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Background,

        bottomBar = {

            NavigationBar(
                modifier = Modifier.navigationBarsPadding(),
                containerColor = SurfaceColor,
                tonalElevation = 2.dp
            ) {

                // DASHBOARD

                NavigationBarItem(
                    selected = selectedItem == 0,

                    onClick = {
                        selectedItem = 0
                    },

                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.Home,
                            contentDescription = "Dashboard"
                        )
                    },

                    label = {
                        Text("Dashboard")
                    }
                )


                // INSTITUTES

                NavigationBarItem(
                    selected = selectedItem == 1,

                    onClick = {
                        selectedItem = 1
                        onInstitutesClick()
                    },

                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.Business,
                            contentDescription = "Institutes"
                        )
                    },

                    label = {
                        Text("Institutes")
                    }
                )


                // INSPECTIONS

                NavigationBarItem(
                    selected = selectedItem == 2,

                    onClick = {
                        selectedItem = 2
                        onInspectionClick()
                    },

                    icon = {
                        Icon(
                            imageVector = Icons.Default.Assignment,
                            contentDescription = "Inspections"
                        )
                    },

                    label = {
                        Text("Inspections")
                    }
                )


                // ALERTS

                NavigationBarItem(
                    selected = selectedItem == 3,

                    onClick = {
                        selectedItem = 3
                        onAlertsClick()
                    },

                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.Notifications,
                            contentDescription = "Alerts"
                        )
                    },

                    label = {
                        Text("Alerts")
                    }
                )


                // REPORTS

                NavigationBarItem(
                    selected = selectedItem == 4,

                    onClick = {
                        selectedItem = 4
                        onReportsClick()
                    },

                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.Description,
                            contentDescription = "Reports"
                        )
                    },

                    label = {
                        Text("Reports")
                    }
                )
            }
        }

    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .statusBarsPadding()
                .verticalScroll(scrollState)
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 8.dp,
                    bottom = 24.dp
                ),

            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            DashboardHeader(
                onNotificationClick = onAlertsClick
            )

            SystemStatus()

            SectionTitle(
                title = "Monitoring Overview"
            )

            StatisticsSection(
                onInstitutesClick = onInstitutesClick,
                onInspectionClick = onInspectionClick,
                onAlertsClick = onAlertsClick,
                onSurveillanceClick = onSurveillanceClick
            )

            SectionTitle(
                title = "Attention Required",
                action = "View all",
                onActionClick = onAlertsClick
            )

            AttentionCard(
                onClick = onAlertsClick
            )

            SectionTitle(
                title = "Live Monitoring",
                action = "View institutes",
                onActionClick = onInstitutesClick
            )

            LiveMonitoringCard(
                onClick = onSurveillanceClick
            )

            SectionTitle(
                title = "Attendance Monitoring"
            )

            AttendanceCard(
                onClick = onAlertsClick
            )

            SectionTitle(
                title = "CCTV Monitoring"
            )

            CctvCard(
                onClick = onSurveillanceClick
            )

            SectionTitle(
                title = "Inspection Status",
                action = "View inspections",
                onActionClick = onInspectionClick
            )

            InspectionCard(
                onClick = onInspectionClick
            )

            Text(
                text = "Nigrani • Monitoring Command Center",

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp),

                fontSize = 10.sp,
                color = Color(0xFF9AA5B1)
            )
        }
    }
}


// ============================================================================
// HEADER
// ============================================================================

@Composable
private fun DashboardHeader(
    onNotificationClick: () -> Unit
) {

    val calendar = java.util.Calendar.getInstance()

    val hour = calendar.get(java.util.Calendar.HOUR_OF_DAY)

    val greeting = when {

        hour < 12 ->
            "Good morning"

        hour < 17 ->
            "Good afternoon"

        else ->
            "Good evening"
    }

    val dateFormat =
        java.text.SimpleDateFormat(
            "EEEE, d MMMM yyyy",
            java.util.Locale.getDefault()
        )

    val date =
        dateFormat.format(
            calendar.time
        )
    Row(
        modifier = Modifier.fillMaxWidth(),

        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(
            modifier = Modifier.weight(1f)
        ) {

            Text(
                text = "$greeting, Officer",

                fontSize = 22.sp,

                fontWeight = FontWeight.Bold,

                color = Navy
            )

            Spacer(
                modifier = Modifier.height(3.dp)
            )

            Text(
                text = "Monitoring Command Center",

                fontSize = 13.sp,

                color = TextGray
            )

            Spacer(
                modifier = Modifier.height(3.dp)
            )

            Text(
                text = date,

                fontSize = 11.sp,

                color = Color(0xFF96A1AE)
            )
        }


        // WORKING NOTIFICATION BUTTON

        Box(
            modifier = Modifier
                .size(46.dp)
                .clip(
                    RoundedCornerShape(13.dp)
                )
                .background(SurfaceColor)
                .clickable {
                    onNotificationClick()
                },

            contentAlignment = Alignment.Center
        ) {

            Icon(
                imageVector = Icons.Outlined.Notifications,

                contentDescription = "Notifications",

                tint = Navy,

                modifier = Modifier.size(22.dp)
            )

            // notification dot

            Box(
                modifier = Modifier
                    .size(9.dp)
                    .clip(CircleShape)
                    .background(Red)
                    .align(Alignment.TopEnd)
            )
        }
    }
}


// ============================================================================
// SYSTEM STATUS
// ============================================================================

@Composable
private fun SystemStatus() {

    Surface(
        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(11.dp),

        color = SoftGreen,

        border = BorderStroke(
            1.dp,
            Color(0xFFCDEBDD)
        )
    ) {

        Row(
            modifier = Modifier.padding(
                horizontal = 12.dp,
                vertical = 9.dp
            ),

            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(Green)
            )

            Spacer(
                modifier = Modifier.width(8.dp)
            )

            Text(
                text = "Monitoring systems operational",

                modifier = Modifier.weight(1f),

                fontSize = 11.sp,

                fontWeight = FontWeight.SemiBold,

                color = Color(0xFF176B50)
            )

            Text(
                text = "LIVE",

                fontSize = 9.sp,

                fontWeight = FontWeight.Bold,

                color = Green
            )
        }
    }
}


// ============================================================================
// SECTION TITLE
// ============================================================================

@Composable
private fun SectionTitle(
    title: String,
    action: String? = null,
    onActionClick: (() -> Unit)? = null
) {

    Row(
        modifier = Modifier.fillMaxWidth(),

        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = title,

            modifier = Modifier.weight(1f),

            fontSize = 16.sp,

            fontWeight = FontWeight.Bold,

            color = Navy
        )

        if (action != null) {

            Text(
                text = action,

                modifier = Modifier.clickable {
                    onActionClick?.invoke()
                },

                fontSize = 11.sp,

                fontWeight = FontWeight.SemiBold,

                color = Blue
            )
        }
    }
}


// ============================================================================
// STATISTICS
// ============================================================================

@Composable
private fun StatisticsSection(
    onInstitutesClick: () -> Unit,
    onInspectionClick: () -> Unit,
    onAlertsClick: () -> Unit,
    onSurveillanceClick: () -> Unit
) {

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),

            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            StatCard(
                modifier = Modifier.weight(1f),

                icon = Icons.Default.Apartment,

                iconTint = Blue,

                iconBackground = SoftBlue,

                value = "128",

                label = "Active Projects",

                onClick = onInstitutesClick
            )

            StatCard(
                modifier = Modifier.weight(1f),

                icon = Icons.Default.Videocam,

                iconTint = Green,

                iconBackground = SoftGreen,

                value = "113",

                label = "CCTV Online",
                onClick = onSurveillanceClick


            )
        }


        Row(
            modifier = Modifier.fillMaxWidth(),

            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            StatCard(
                modifier = Modifier.weight(1f),

                icon = Icons.Default.Assignment,

                iconTint = Amber,

                iconBackground = SoftAmber,

                value = "17",

                label = "Inspections Pending",

                onClick = onInspectionClick
            )

            StatCard(
                modifier = Modifier.weight(1f),

                icon = Icons.Default.Error,

                iconTint = Red,

                iconBackground = SoftRed,

                value = "08",

                label = "Critical Alerts",

                onClick = onAlertsClick
            )
        }
    }
}


// ============================================================================
// STAT CARD
// ============================================================================

@Composable
private fun StatCard(
    modifier: Modifier,
    icon: ImageVector,
    iconTint: Color,
    iconBackground: Color,
    value: String,
    label: String,
    onClick: () -> Unit
) {

    Card(
        modifier = modifier
            .height(96.dp)
            .clickable {
                onClick()
            },

        shape = RoundedCornerShape(14.dp),

        colors = CardDefaults.cardColors(
            containerColor = SurfaceColor
        ),

        border = BorderStroke(
            1.dp,
            Border
        ),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(11.dp),

            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(
                        RoundedCornerShape(11.dp)
                    )
                    .background(iconBackground),

                contentAlignment = Alignment.Center
            ) {

                Icon(
                    imageVector = icon,

                    contentDescription = null,

                    tint = iconTint,

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
                    text = value,

                    fontSize = 23.sp,

                    fontWeight = FontWeight.Bold,

                    color = Navy
                )

                Text(
                    text = label,

                    fontSize = 10.sp,

                    color = TextGray,

                    maxLines = 2
                )
            }
        }
    }
}


// ============================================================================
// ATTENTION CARD
// ============================================================================

@Composable
private fun AttentionCard(
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },

        shape = RoundedCornerShape(15.dp),

        colors = CardDefaults.cardColors(
            containerColor = SurfaceColor
        ),

        border = BorderStroke(
            1.dp,
            Border
        )
    ) {

        Column(
            modifier = Modifier.padding(14.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(
                            RoundedCornerShape(11.dp)
                        )
                        .background(SoftRed),

                    contentAlignment = Alignment.Center
                ) {

                    Icon(
                        imageVector = Icons.Default.Warning,

                        contentDescription = null,

                        tint = Red,

                        modifier = Modifier.size(22.dp)
                    )
                }

                Spacer(
                    modifier = Modifier.width(10.dp)
                )

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Surface(
                        shape = RoundedCornerShape(5.dp),

                        color = Red
                    ) {

                        Text(
                            text = "CRITICAL",

                            modifier = Modifier.padding(
                                horizontal = 7.dp,
                                vertical = 3.dp
                            ),

                            fontSize = 8.sp,

                            fontWeight = FontWeight.Bold,

                            color = Color.White
                        )
                    }

                    Spacer(
                        modifier = Modifier.height(4.dp)
                    )

                    Text(
                        text = "Attendance anomaly",

                        fontSize = 16.sp,

                        fontWeight = FontWeight.Bold,

                        color = Navy
                    )
                }

                Icon(
                    imageVector = Icons.Default.ArrowForward,

                    contentDescription = "Open alert",

                    tint = Blue,

                    modifier = Modifier.size(19.dp)
                )
            }

            Spacer(
                modifier = Modifier.height(11.dp)
            )

            Text(
                text = "Sunrise Rehabilitation Centre",

                fontSize = 13.sp,

                fontWeight = FontWeight.Medium,

                color = Color(0xFF526477)
            )

            Text(
                text = "Varanasi, Uttar Pradesh",

                fontSize = 11.sp,

                color = TextGray
            )

            Spacer(
                modifier = Modifier.height(11.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.SpaceBetween
            ) {

                Metric(
                    title = "Reported",
                    value = "94%"
                )

                Metric(
                    title = "Detected",
                    value = "71%",
                    valueColor = Red
                )

                Metric(
                    title = "Variance",
                    value = "-23%",
                    valueColor = Red
                )

                Metric(
                    title = "Updated",
                    value = "12 min"
                )
            }
        }
    }
}


// ============================================================================
// METRIC
// ============================================================================

@Composable
private fun Metric(
    title: String,
    value: String,
    valueColor: Color = Navy
) {

    Column {

        Text(
            text = title,

            fontSize = 9.sp,

            color = TextGray
        )

        Spacer(
            modifier = Modifier.height(2.dp)
        )

        Text(
            text = value,

            fontSize = 14.sp,

            fontWeight = FontWeight.Bold,

            color = valueColor
        )
    }
}


// ============================================================================
// LIVE MONITORING CARD
// ============================================================================

@Composable
private fun LiveMonitoringCard(
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },

        shape = RoundedCornerShape(15.dp),

        colors = CardDefaults.cardColors(
            containerColor = SurfaceColor
        ),

        border = BorderStroke(
            1.dp,
            Border
        )
    ) {

        Column(
            modifier = Modifier.padding(14.dp)
        ) {

            // MAP VISUALIZATION

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(145.dp)
                    .clip(
                        RoundedCornerShape(12.dp)
                    )
                    .background(
                        Color(0xFFEAF1F6)
                    )
            ) {

                // horizontal map lines

                Column(
                    modifier = Modifier.fillMaxSize(),

                    verticalArrangement =
                        Arrangement.SpaceEvenly
                ) {

                    repeat(5) {

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(
                                    Color(0xFFD7E0E8)
                                )
                        )
                    }
                }


                // vertical map lines

                Row(
                    modifier = Modifier.fillMaxSize(),

                    horizontalArrangement =
                        Arrangement.SpaceEvenly,

                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    repeat(4) {

                        Box(
                            modifier = Modifier
                                .width(1.dp)
                                .height(145.dp)
                                .background(
                                    Color(0xFFD7E0E8)
                                )
                        )
                    }
                }


                // PROJECT MARKERS

                MapMarker(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(
                            start = 55.dp,
                            top = 32.dp
                        ),

                    color = Red,
                    number = "2"
                )


                MapMarker(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(start = 25.dp),

                    color = Green,
                    number = "1"
                )


                MapMarker(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(
                            end = 55.dp,
                            top = 28.dp
                        ),

                    color = Amber,
                    number = "3"
                )


                MapMarker(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(
                            end = 85.dp,
                            bottom = 25.dp
                        ),

                    color = Green,
                    number = "1"
                )


                Surface(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(9.dp),

                    shape = RoundedCornerShape(7.dp),

                    color = Color.White.copy(
                        alpha = 0.94f
                    )
                ) {

                    Row(
                        modifier = Modifier.padding(
                            horizontal = 8.dp,
                            vertical = 5.dp
                        ),

                        verticalAlignment =
                            Alignment.CenterVertically
                    ) {

                        Box(
                            modifier = Modifier
                                .size(7.dp)
                                .clip(CircleShape)
                                .background(Green)
                        )

                        Spacer(
                            modifier = Modifier.width(5.dp)
                        )

                        Text(
                            text = "LIVE MONITORING",

                            fontSize = 8.sp,

                            fontWeight =
                                FontWeight.Bold,

                            color = Navy
                        )
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),

                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = "India Monitoring Network",

                        fontSize = 15.sp,

                        fontWeight =
                            FontWeight.Bold,

                        color = Navy
                    )

                    Spacer(
                        modifier = Modifier.height(3.dp)
                    )

                    Text(
                        text =
                            "128 projects currently monitored",

                        fontSize = 11.sp,

                        color = TextGray
                    )
                }

                Icon(
                    imageVector =
                        Icons.Default.ArrowForward,

                    contentDescription =
                        "Open monitoring",

                    tint = Blue,

                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(
                modifier = Modifier.height(11.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.SpaceBetween
            ) {

                MonitoringStatus(
                    color = Green,
                    value = "113",
                    label = "Online"
                )

                MonitoringStatus(
                    color = Red,
                    value = "08",
                    label = "Critical"
                )

                MonitoringStatus(
                    color = Amber,
                    value = "05",
                    label = "Offline"
                )

                MonitoringStatus(
                    color = Blue,
                    value = "17",
                    label = "Inspections"
                )
            }
        }
    }
}


// ============================================================================
// MAP MARKER
// ============================================================================

@Composable
private fun MapMarker(
    modifier: Modifier,
    color: Color,
    number: String
) {

    Box(
        modifier = modifier
            .size(30.dp)
            .clip(CircleShape)
            .background(
                color.copy(alpha = 0.18f)
            ),

        contentAlignment = Alignment.Center
    ) {

        Box(
            modifier = Modifier
                .size(14.dp)
                .clip(CircleShape)
                .background(color),

            contentAlignment = Alignment.Center
        ) {

            Text(
                text = number,

                fontSize = 7.sp,

                fontWeight = FontWeight.Bold,

                color = Color.White
            )
        }
    }
}


// ============================================================================
// MONITORING STATUS
// ============================================================================

@Composable
private fun MonitoringStatus(
    color: Color,
    value: String,
    label: String
) {

    Column {

        Row(
            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(6.dp)
                    .clip(CircleShape)
                    .background(color)
            )

            Spacer(
                modifier = Modifier.width(4.dp)
            )

            Text(
                text = value,

                fontSize = 12.sp,

                fontWeight =
                    FontWeight.Bold,

                color = Navy
            )
        }

        Text(
            text = label,

            fontSize = 9.sp,

            color = TextGray
        )
    }
}


// ============================================================================
// ATTENDANCE CARD
// ============================================================================

@Composable
private fun AttendanceCard(
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },

        shape = RoundedCornerShape(15.dp),

        colors = CardDefaults.cardColors(
            containerColor = SurfaceColor
        ),

        border = BorderStroke(
            1.dp,
            Border
        )
    ) {

        Column(
            modifier = Modifier.padding(14.dp)
        ) {

            Row(
                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(
                            RoundedCornerShape(11.dp)
                        )
                        .background(SoftBlue),

                    contentAlignment =
                        Alignment.Center
                ) {

                    Icon(
                        imageVector =
                            Icons.Default.People,

                        contentDescription =
                            null,

                        tint = Blue,

                        modifier =
                            Modifier.size(21.dp)
                    )
                }

                Spacer(
                    modifier =
                        Modifier.width(10.dp)
                )

                Column(
                    modifier =
                        Modifier.weight(1f)
                ) {

                    Text(
                        text =
                            "Overall Attendance",

                        fontSize = 14.sp,

                        fontWeight =
                            FontWeight.Bold,

                        color = Navy
                    )

                    Text(
                        text =
                            "Across monitored projects",

                        fontSize = 10.sp,

                        color = TextGray
                    )
                }

                Text(
                    text = "89%",

                    fontSize = 21.sp,

                    fontWeight =
                        FontWeight.Bold,

                    color = Green
                )
            }

            Spacer(
                modifier =
                    Modifier.height(13.dp)
            )

            AttendanceBar(
                label =
                    "Reported attendance",

                value = 94,

                color = Blue
            )

            Spacer(
                modifier =
                    Modifier.height(9.dp)
            )

            AttendanceBar(
                label =
                    "AI detected attendance",

                value = 89,

                color = Green
            )

            Spacer(
                modifier =
                    Modifier.height(10.dp)
            )

            Surface(
                shape =
                    RoundedCornerShape(8.dp),

                color =
                    SoftRed
            ) {

                Row(
                    modifier =
                        Modifier.padding(
                            horizontal = 9.dp,
                            vertical = 7.dp
                        ),

                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector =
                            Icons.Default.Warning,

                        contentDescription =
                            null,

                        tint = Red,

                        modifier =
                            Modifier.size(15.dp)
                    )

                    Spacer(
                        modifier =
                            Modifier.width(6.dp)
                    )

                    Text(
                        text =
                            "3 projects require attendance review",

                        fontSize = 10.sp,

                        fontWeight =
                            FontWeight.SemiBold,

                        color =
                            Color(0xFF9F3030)
                    )
                }
            }
        }
    }
}


// ============================================================================
// ATTENDANCE BAR
// ============================================================================

@Composable
private fun AttendanceBar(
    label: String,
    value: Int,
    color: Color
) {

    Column {

        Row(
            modifier =
                Modifier.fillMaxWidth(),

            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Text(
                text = label,

                modifier =
                    Modifier.weight(1f),

                fontSize = 10.sp,

                color = TextGray
            )

            Text(
                text = "$value%",

                fontSize = 10.sp,

                fontWeight =
                    FontWeight.Bold,

                color = Navy
            )
        }

        Spacer(
            modifier =
                Modifier.height(5.dp)
        )

        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(7.dp)
                    .clip(
                        RoundedCornerShape(10.dp)
                    )
                    .background(
                        Color(0xFFE8EDF2)
                    )
        ) {

            Box(
                modifier =
                    Modifier
                        .fillMaxWidth(
                            value / 100f
                        )
                        .height(7.dp)
                        .clip(
                            RoundedCornerShape(10.dp)
                        )
                        .background(color)
            )
        }
    }
}


// ============================================================================
// CCTV CARD
// ============================================================================

@Composable
private fun CctvCard(
    onClick: () -> Unit
) {

    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .clickable {
                    onClick()
                },

        shape =
            RoundedCornerShape(15.dp),

        colors =
            CardDefaults.cardColors(
                containerColor =
                    SurfaceColor
            ),

        border =
            BorderStroke(
                1.dp,
                Border
            )
    ) {

        Column(
            modifier =
                Modifier.padding(14.dp)
        ) {

            Row(
                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Box(
                    modifier =
                        Modifier
                            .size(40.dp)
                            .clip(
                                RoundedCornerShape(11.dp)
                            )
                            .background(
                                SoftGreen
                            ),

                    contentAlignment =
                        Alignment.Center
                ) {

                    Icon(
                        imageVector =
                            Icons.Default.Videocam,

                        contentDescription =
                            null,

                        tint =
                            Green,

                        modifier =
                            Modifier.size(21.dp)
                    )
                }

                Spacer(
                    modifier =
                        Modifier.width(10.dp)
                )

                Column(
                    modifier =
                        Modifier.weight(1f)
                ) {

                    Text(
                        text =
                            "CCTV Network",

                        fontSize =
                            14.sp,

                        fontWeight =
                            FontWeight.Bold,

                        color =
                            Navy
                    )

                    Text(
                        text =
                            "113 of 118 cameras online",

                        fontSize =
                            10.sp,

                        color =
                            TextGray
                    )
                }

                Text(
                    text =
                        "95.8%",

                    fontSize =
                        17.sp,

                    fontWeight =
                        FontWeight.Bold,

                    color =
                        Green
                )
            }

            Spacer(
                modifier =
                    Modifier.height(12.dp)
            )

            Row(
                modifier =
                    Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.spacedBy(8.dp)
            ) {

                CctvStatus(
                    modifier =
                        Modifier.weight(1f),

                    value =
                        "113",

                    label =
                        "Online",

                    color =
                        Green
                )

                CctvStatus(
                    modifier =
                        Modifier.weight(1f),

                    value =
                        "03",

                    label =
                        "Offline",

                    color =
                        Amber
                )

                CctvStatus(
                    modifier =
                        Modifier.weight(1f),

                    value =
                        "02",

                    label =
                        "Tampering",

                    color =
                        Red
                )
            }
        }
    }
}


// ============================================================================
// CCTV STATUS
// ============================================================================

@Composable
private fun CctvStatus(
    modifier: Modifier,
    value: String,
    label: String,
    color: Color
) {

    Surface(
        modifier = modifier,

        shape =
            RoundedCornerShape(9.dp),

        color =
            color.copy(alpha = 0.07f)
    ) {

        Column(
            modifier =
                Modifier.padding(10.dp)
        ) {

            Text(
                text = value,

                fontSize = 17.sp,

                fontWeight =
                    FontWeight.Bold,

                color = color
            )

            Text(
                text = label,

                fontSize = 9.sp,

                color = TextGray
            )
        }
    }
}


// ============================================================================
// INSPECTION CARD
// ============================================================================

@Composable
private fun InspectionCard(
    onClick: () -> Unit
) {

    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .clickable {
                    onClick()
                },

        shape =
            RoundedCornerShape(15.dp),

        colors =
            CardDefaults.cardColors(
                containerColor =
                    SurfaceColor
            ),

        border =
            BorderStroke(
                1.dp,
                Border
            )
    ) {

        Column(
            modifier =
                Modifier.padding(14.dp)
        ) {

            Row(
                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Icon(
                    imageVector =
                        Icons.Default.Assignment,

                    contentDescription =
                        null,

                    tint =
                        Blue,

                    modifier =
                        Modifier.size(21.dp)
                )

                Spacer(
                    modifier =
                        Modifier.width(9.dp)
                )

                Text(
                    text =
                        "Inspection activity",

                    modifier =
                        Modifier.weight(1f),

                    fontSize = 14.sp,

                    fontWeight =
                        FontWeight.Bold,

                    color = Navy
                )

                Icon(
                    imageVector =
                        Icons.Default.ArrowForward,

                    contentDescription =
                        "Open inspections",

                    tint = Blue,

                    modifier =
                        Modifier.size(18.dp)
                )
            }

            Spacer(
                modifier =
                    Modifier.height(13.dp)
            )

            Row(
                modifier =
                    Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.spacedBy(7.dp)
            ) {

                InspectionStatus(
                    modifier =
                        Modifier.weight(1f),

                    value =
                        "17",

                    label =
                        "Pending",

                    color =
                        Amber
                )

                InspectionStatus(
                    modifier =
                        Modifier.weight(1f),

                    value =
                        "06",

                    label =
                        "Assigned",

                    color =
                        Blue
                )

                InspectionStatus(
                    modifier =
                        Modifier.weight(1f),

                    value =
                        "04",

                    label =
                        "In Progress",

                    color =
                        Green
                )

                InspectionStatus(
                    modifier =
                        Modifier.weight(1f),

                    value =
                        "31",

                    label =
                        "Completed",

                    color =
                        Navy
                )
            }
        }
    }
}


// ============================================================================
// INSPECTION STATUS
// ============================================================================

@Composable
private fun InspectionStatus(
    modifier: Modifier,
    value: String,
    label: String,
    color: Color
) {

    Surface(
        modifier = modifier,

        shape =
            RoundedCornerShape(9.dp),

        color =
            Color(0xFFF8FAFC),

        border =
            BorderStroke(
                1.dp,
                Color(0xFFE8EDF2)
            )
    ) {

        Column(
            modifier =
                Modifier.padding(8.dp)
        ) {

            Text(
                text = value,

                fontSize = 16.sp,

                fontWeight =
                    FontWeight.Bold,

                color = color
            )

            Spacer(
                modifier =
                    Modifier.height(2.dp)
            )

            Text(
                text = label,

                fontSize = 8.sp,

                color = TextGray,

                maxLines = 1
            )
        }
    }
}