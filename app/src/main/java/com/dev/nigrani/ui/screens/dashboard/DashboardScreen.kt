package com.dev.nigrani.ui.screens.dashboard

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apartment
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.outlined.Assignment
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
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

private val Navy = Color(0xFF123B63)
private val Blue = Color(0xFF21639D)
private val Green = Color(0xFF159A72)
private val Red = Color(0xFFD23838)
private val Amber = Color(0xFFE1A62A)

private val Background = Color(0xFFF6F8FB)
private val Surface = Color.White
private val SoftBlue = Color(0xFFEAF3FA)
private val SoftRed = Color(0xFFFFEEEE)
private val TextGray = Color(0xFF7C8794)
private val Border = Color(0xFFE2E7ED)


@Composable
fun DashboardScreen(
    onInstitutesClick: () -> Unit = {},
    onInspectionClick: () -> Unit = {},
    onAlertsClick: () -> Unit = {},
    onReportsClick: () -> Unit = {}
) {

    var selectedItem by remember {
        mutableIntStateOf(0)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Background,
        contentWindowInsets = WindowInsets.statusBars,

        bottomBar = {

            NavigationBar(
                modifier = Modifier.navigationBarsPadding(),
                containerColor = Surface,
                tonalElevation = 0.dp
            ) {

                // ============================================================
                // DASHBOARD
                // ============================================================

                NavigationBarItem(
                    selected = selectedItem == 0,

                    onClick = {
                        selectedItem = 0
                    },

                    icon = {
                        Icon(
                            Icons.Outlined.Home,
                            contentDescription = "Dashboard"
                        )
                    },

                    label = {
                        Text("Dashboard")
                    }
                )


                // ============================================================
                // INSTITUTES
                // ============================================================

                NavigationBarItem(
                    selected = selectedItem == 1,

                    onClick = {
                        selectedItem = 1
                        onInstitutesClick()
                    },

                    icon = {
                        Icon(
                            Icons.Outlined.Business,
                            contentDescription = "Institutes"
                        )
                    },

                    label = {
                        Text("Institutes")
                    }
                )


                // ============================================================
                // INSPECTIONS
                // ============================================================

                NavigationBarItem(
                    selected = selectedItem == 2,

                    onClick = {
                        selectedItem = 2
                        onInspectionClick()
                    },

                    icon = {
                        Icon(
                            Icons.Outlined.Assignment,
                            contentDescription = "Inspections"
                        )
                    },

                    label = {
                        Text("Inspections")
                    }
                )


                // ============================================================
                // ALERTS
                // ============================================================

                NavigationBarItem(
                    selected = selectedItem == 3,

                    onClick = {
                        selectedItem = 3
                        onAlertsClick()
                    },

                    icon = {
                        Icon(
                            Icons.Outlined.Notifications,
                            contentDescription = "Alerts"
                        )
                    },

                    label = {
                        Text("Alerts")
                    }
                )


                // ============================================================
                // REPORTS
                // ============================================================

                NavigationBarItem(
                    selected = selectedItem == 4,

                    onClick = {
                        selectedItem = 4
                        onReportsClick()
                    },

                    icon = {
                        Icon(
                            Icons.Outlined.Description,
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
                .windowInsetsPadding(WindowInsets.statusBars)
                .padding(
                    start = 18.dp,
                    end = 18.dp,
                    top = 10.dp,
                    bottom = 10.dp
                )
        ) {

            DashboardHeader()

            Spacer(modifier = Modifier.height(13.dp))

            DashboardStats()

            Spacer(modifier = Modifier.height(14.dp))

            AttentionSection()

            Spacer(modifier = Modifier.height(14.dp))

            LiveMonitoringSection()
        }
    }
}


// ============================================================================
// DASHBOARD HEADER
// ============================================================================

@Composable
private fun DashboardHeader() {

    val hour = LocalTime.now().hour

    val greeting = when {
        hour < 12 -> "Good morning"
        hour < 17 -> "Good afternoon"
        else -> "Good evening"
    }

    val date = LocalDate.now().format(
        DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy")
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
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold,
                color = Navy
            )

            Spacer(modifier = Modifier.height(3.dp))

            Text(
                text = "Monitoring Command Center",
                fontSize = 15.sp,
                color = TextGray
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = date,
                fontSize = 12.sp,
                color = Color(0xFF9AA3AE)
            )
        }

        Box(
            modifier = Modifier
                .size(43.dp)
                .clip(
                    androidx.compose.foundation.shape.RoundedCornerShape(13.dp)
                )
                .background(Surface),
            contentAlignment = Alignment.Center
        ) {

            Icon(
                imageVector = Icons.Outlined.Notifications,
                contentDescription = "Notifications",
                tint = Navy,
                modifier = Modifier.size(22.dp)
            )

            Box(
                modifier = Modifier
                    .padding(5.dp)
                    .size(7.dp)
                    .clip(
                        androidx.compose.foundation.shape.CircleShape
                    )
                    .background(Red)
                    .align(Alignment.TopEnd)
            )
        }
    }
}


// ============================================================================
// DASHBOARD STATS
// ============================================================================

@Composable
private fun DashboardStats() {

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
                value = "128",
                label = "Active Projects"
            )

            StatCard(
                modifier = Modifier.weight(1f),
                icon = Icons.Default.Videocam,
                iconTint = Green,
                value = "113",
                label = "CCTV Online"
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
                value = "17",
                label = "Inspections Pending"
            )

            StatCard(
                modifier = Modifier.weight(1f),
                icon = Icons.Default.Error,
                iconTint = Red,
                value = "08",
                label = "Critical Alerts"
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
    value: String,
    label: String
) {

    Card(
        modifier = modifier.height(105.dp),

        shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),

        colors = CardDefaults.cardColors(
            containerColor = Surface
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
                .padding(12.dp),

            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(
                        androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
                    )
                    .background(SoftBlue),

                contentAlignment = Alignment.Center
            ) {

                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(23.dp)
                )
            }

            Spacer(modifier = Modifier.width(11.dp))

            Column {

                Text(
                    text = value,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = Navy
                )

                Text(
                    text = label,
                    fontSize = 11.sp,
                    color = TextGray,
                    maxLines = 2
                )
            }
        }
    }
}


// ============================================================================
// ATTENTION SECTION
// ============================================================================

@Composable
private fun AttentionSection() {

    Column {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Attention Required",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Navy,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "View all",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = Blue
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        AttentionCard()
    }
}


// ============================================================================
// ATTENTION CARD
// ============================================================================

@Composable
private fun AttentionCard() {

    Card(
        modifier = Modifier.fillMaxWidth(),

        shape = androidx.compose.foundation.shape.RoundedCornerShape(17.dp),

        colors = CardDefaults.cardColors(
            containerColor = Surface
        ),

        border = BorderStroke(
            1.dp,
            Border
        ),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
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
                        .size(44.dp)
                        .clip(
                            androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
                        )
                        .background(SoftRed),

                    contentAlignment = Alignment.Center
                ) {

                    Icon(
                        imageVector = Icons.Default.Error,
                        contentDescription = null,
                        tint = Red,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(11.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Box(
                            modifier = Modifier
                                .clip(
                                    androidx.compose.foundation.shape.RoundedCornerShape(5.dp)
                                )
                                .background(Red)
                                .padding(
                                    horizontal = 8.dp,
                                    vertical = 4.dp
                                )
                        ) {

                            Text(
                                text = "CRITICAL",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Attendance anomaly",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = Navy
                    )
                }
            }

            Spacer(modifier = Modifier.height(9.dp))

            Text(
                text = "Sunrise Rehabilitation Centre",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF566474)
            )

            Text(
                text = "Varanasi, Uttar Pradesh",
                fontSize = 12.sp,
                color = TextGray
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
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
                    title = "Updated",
                    value = "12 min ago"
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
            fontSize = 10.sp,
            color = TextGray
        )

        Spacer(modifier = Modifier.height(1.dp))

        Text(
            text = value,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = valueColor
        )
    }
}


// ============================================================================
// LIVE MONITORING
// ============================================================================

@Composable
private fun LiveMonitoringSection() {

    Column {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Live Monitoring",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Navy,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "View map",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = Blue
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),

            shape = androidx.compose.foundation.shape.RoundedCornerShape(17.dp),

            colors = CardDefaults.cardColors(
                containerColor = Surface
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
                    .fillMaxWidth()
                    .padding(13.dp),

                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .size(70.dp)
                        .clip(
                            androidx.compose.foundation.shape.RoundedCornerShape(13.dp)
                        )
                        .background(SoftBlue),

                    contentAlignment = Alignment.Center
                ) {

                    Icon(
                        imageVector = Icons.Default.Map,
                        contentDescription = null,
                        tint = Blue,
                        modifier = Modifier.size(32.dp)
                    )
                }

                Spacer(modifier = Modifier.width(13.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = "India Monitoring Map",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Navy
                    )

                    Spacer(modifier = Modifier.height(3.dp))

                    Text(
                        text = "128 projects across India",
                        fontSize = 12.sp,
                        color = TextGray
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {

                        StatusItem(
                            color = Green,
                            value = "113",
                            label = "Online"
                        )

                        StatusItem(
                            color = Red,
                            value = "15",
                            label = "Alerts"
                        )

                        StatusItem(
                            color = Blue,
                            value = "5",
                            label = "Offline"
                        )
                    }
                }
            }
        }
    }
}


// ============================================================================
// STATUS ITEM
// ============================================================================

@Composable
private fun StatusItem(
    color: Color,
    value: String,
    label: String
) {

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(7.dp)
                .clip(
                    androidx.compose.foundation.shape.CircleShape
                )
                .background(color)
        )

        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = value,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = Navy
        )

        Spacer(modifier = Modifier.width(2.dp))

        Text(
            text = label,
            fontSize = 10.sp,
            color = TextGray
        )
    }
}