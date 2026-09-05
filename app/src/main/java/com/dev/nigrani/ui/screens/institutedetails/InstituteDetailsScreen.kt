package com.dev.nigrani.ui.screens.institutedetails

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
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.outlined.ArrowForwardIos
import androidx.compose.material.icons.outlined.Business
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.PlayCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
private val Amber = Color(0xFFE1A62A)

private val Background = Color(0xFFF6F8FB)
private val SurfaceWhite = Color.White

private val SoftBlue = Color(0xFFEAF3FA)
private val SoftGreen = Color(0xFFE8F7F1)
private val SoftRed = Color(0xFFFFEEEE)
private val SoftAmber = Color(0xFFFFF6DF)

private val TextGray = Color(0xFF7C8794)
private val Border = Color(0xFFE1E6EC)


// ============================================================
// MAIN SCREEN
// ============================================================

@Composable
fun InstituteDetailsScreen(
    onBackClick: () -> Unit = {},
    onSurveillanceClick: () -> Unit = {},
    onInspectionClick: () -> Unit = {},
    onAlertsClick: () -> Unit = {}
) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Background,

        bottomBar = {
            DetailsBottomBar(
                onSurveillanceClick = onSurveillanceClick,
                onInspectionClick = onInspectionClick,
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

            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // HEADER
            item {
                DetailsHeader(
                    onBackClick = onBackClick
                )
            }

            // INSTITUTE INFORMATION
            item {
                InstituteHeader()
            }

            // OVERVIEW
            item {
                MonitoringOverview()
            }

            // CURRENT STATUS
            item {
                CurrentStatus(
                    onCctvClick = onSurveillanceClick
                )
            }

            // LIVE SURVEILLANCE
            item {
                LiveSurveillance(
                    onClick = onSurveillanceClick
                )
            }

            // RECENT INSPECTIONS
            item {
                RecentInspections(
                    onClick = onInspectionClick
                )
            }

            // RECENT ALERTS
            item {
                RecentAlerts(
                    onClick = onAlertsClick
                )
            }
        }
    }
}


// ============================================================
// TOP HEADER
// ============================================================

@Composable
private fun DetailsHeader(
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

        Text(
            text = "Institute Details",
            fontSize = 21.sp,
            fontWeight = FontWeight.Bold,
            color = Navy,
            modifier = Modifier.weight(1f)
        )

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
// INSTITUTE HEADER
// ============================================================

@Composable
private fun InstituteHeader() {

    Column {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(SoftBlue),

                contentAlignment = Alignment.Center
            ) {

                Icon(
                    imageVector = Icons.Outlined.Business,
                    contentDescription = null,
                    tint = Blue,
                    modifier = Modifier.size(29.dp)
                )
            }

            Spacer(
                modifier = Modifier.width(12.dp)
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = "Sunrise Rehabilitation Centre",
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold,
                    color = Navy
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = TextGray,
                        modifier = Modifier.size(14.dp)
                    )

                    Spacer(
                        modifier = Modifier.width(3.dp)
                    )

                    Text(
                        text = "Varanasi, Uttar Pradesh",
                        fontSize = 12.sp,
                        color = TextGray
                    )
                }
            }
        }

        Spacer(
            modifier = Modifier.height(11.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            StatusBadge(
                text = "CRITICAL",
                background = SoftRed,
                textColor = Red
            )

            Spacer(
                modifier = Modifier.width(9.dp)
            )

            Text(
                text = "Last monitored 12 min ago",
                fontSize = 11.sp,
                color = TextGray
            )
        }
    }
}


// ============================================================
// MONITORING OVERVIEW
// ============================================================

@Composable
private fun MonitoringOverview() {

    Card(
        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(18.dp),

        colors = CardDefaults.cardColors(
            containerColor = SurfaceWhite
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
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = "Monitoring Overview",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Navy
            )

            Spacer(
                modifier = Modifier.height(14.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                OverviewMetric(
                    value = "286",
                    label = "Beneficiaries"
                )

                OverviewMetric(
                    value = "71%",
                    label = "Attendance",
                    valueColor = Red
                )

                OverviewMetric(
                    value = "94%",
                    label = "Reported"
                )

                OverviewMetric(
                    value = "71%",
                    label = "Detected",
                    valueColor = Red
                )
            }
        }
    }
}


@Composable
private fun OverviewMetric(
    value: String,
    label: String,
    valueColor: Color = Navy
) {

    Column {

        Text(
            text = value,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = valueColor
        )

        Spacer(
            modifier = Modifier.height(2.dp)
        )

        Text(
            text = label,
            fontSize = 10.sp,
            color = TextGray
        )
    }
}


// ============================================================
// CURRENT STATUS
// ============================================================

@Composable
private fun CurrentStatus(
    onCctvClick: () -> Unit
) {

    Column {

        Text(
            text = "Current Status",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Navy
        )

        Spacer(
            modifier = Modifier.height(9.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            StatusCard(
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        onCctvClick()
                    },

                icon = Icons.Default.Videocam,
                title = "CCTV",
                value = "ONLINE",
                iconColor = Green,
                iconBackground = SoftGreen
            )

            StatusCard(
                modifier = Modifier.weight(1f),

                icon = Icons.Default.Error,
                title = "Attendance",
                value = "ANOMALY",
                iconColor = Red,
                iconBackground = SoftRed
            )
        }
    }
}


@Composable
private fun StatusCard(
    modifier: Modifier,
    icon: ImageVector,
    title: String,
    value: String,
    iconColor: Color,
    iconBackground: Color
) {

    Card(
        modifier = modifier,

        shape = RoundedCornerShape(15.dp),

        colors = CardDefaults.cardColors(
            containerColor = SurfaceWhite
        ),

        border = BorderStroke(
            1.dp,
            Border
        ),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        )
    ) {

        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(iconBackground),

                contentAlignment = Alignment.Center
            ) {

                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(
                modifier = Modifier.width(9.dp)
            )

            Column {

                Text(
                    text = title,
                    fontSize = 11.sp,
                    color = TextGray
                )

                Text(
                    text = value,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = iconColor
                )
            }
        }
    }
}


// ============================================================
// LIVE SURVEILLANCE
// ============================================================

@Composable
private fun LiveSurveillance(
    onClick: () -> Unit
) {

    Column {

        SectionHeader(
            title = "Live Surveillance",
            action = "View all"
        )

        Spacer(
            modifier = Modifier.height(9.dp)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onClick()
                },

            shape = RoundedCornerShape(18.dp),

            colors = CardDefaults.cardColors(
                containerColor = SurfaceWhite
            ),

            border = BorderStroke(
                1.dp,
                Border
            ),

            elevation = CardDefaults.cardElevation(
                defaultElevation = 1.dp
            )
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(145.dp)
                    .background(Color(0xFF172A3A)),

                contentAlignment = Alignment.Center
            ) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Icon(
                        imageVector = Icons.Outlined.PlayCircle,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(43.dp)
                    )

                    Spacer(
                        modifier = Modifier.height(7.dp)
                    )

                    Text(
                        text = "CCTV Camera 01",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )

                    Spacer(
                        modifier = Modifier.height(4.dp)
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Box(
                            modifier = Modifier
                                .size(7.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF48D597))
                        )

                        Spacer(
                            modifier = Modifier.width(5.dp)
                        )

                        Text(
                            text = "LIVE",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }

                // LIVE INDICATOR
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(10.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color(0xCC000000))
                        .padding(
                            horizontal = 7.dp,
                            vertical = 4.dp
                        )
                ) {

                    Text(
                        text = "LIVE",
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),

                verticalAlignment = Alignment.CenterVertically
            ) {

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = "1 of 4 cameras online",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = Navy
                    )

                    Spacer(
                        modifier = Modifier.height(2.dp)
                    )

                    Text(
                        text = "Live surveillance feed",
                        fontSize = 10.sp,
                        color = TextGray
                    )
                }

                Icon(
                    imageVector = Icons.Outlined.ArrowForwardIos,
                    contentDescription = null,
                    tint = Blue,
                    modifier = Modifier.size(15.dp)
                )
            }
        }
    }
}


// ============================================================
// RECENT INSPECTIONS
// ============================================================

@Composable
private fun RecentInspections(
    onClick: () -> Unit
) {

    Column {

        SectionHeader(
            title = "Recent Inspections",
            action = "View all"
        )

        Spacer(
            modifier = Modifier.height(9.dp)
        )

        InspectionRow(
            title = "Surprise Inspection",
            date = "28 Aug 2026",
            status = "PASSED",
            statusColor = Green,
            background = SoftGreen,
            onClick = onClick
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        InspectionRow(
            title = "Routine Inspection",
            date = "14 Aug 2026",
            status = "REVIEW",
            statusColor = Amber,
            background = SoftAmber,
            onClick = onClick
        )
    }
}


@Composable
private fun InspectionRow(
    title: String,
    date: String,
    status: String,
    statusColor: Color,
    background: Color,
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
            containerColor = SurfaceWhite
        ),

        border = BorderStroke(
            1.dp,
            Border
        ),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        )
    ) {

        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(SoftBlue),

                contentAlignment = Alignment.Center
            ) {

                Icon(
                    imageVector = Icons.Default.Assignment,
                    contentDescription = null,
                    tint = Blue,
                    modifier = Modifier.size(20.dp)
                )
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

                Spacer(
                    modifier = Modifier.height(2.dp)
                )

                Text(
                    text = date,
                    fontSize = 10.sp,
                    color = TextGray
                )
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(background)
                    .padding(
                        horizontal = 8.dp,
                        vertical = 5.dp
                    )
            ) {

                Text(
                    text = status,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    color = statusColor
                )
            }
        }
    }
}


// ============================================================
// RECENT ALERTS
// ============================================================

@Composable
private fun RecentAlerts(
    onClick: () -> Unit
) {

    Column {

        SectionHeader(
            title = "Recent Alerts",
            action = "View all"
        )

        Spacer(
            modifier = Modifier.height(9.dp)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onClick()
                },

            shape = RoundedCornerShape(15.dp),

            colors = CardDefaults.cardColors(
                containerColor = SurfaceWhite
            ),

            border = BorderStroke(
                1.dp,
                Border
            ),

            elevation = CardDefaults.cardElevation(
                defaultElevation = 0.dp
            )
        ) {

            Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(11.dp))
                        .background(SoftRed),

                    contentAlignment = Alignment.Center
                ) {

                    Icon(
                        imageVector = Icons.Default.Error,
                        contentDescription = null,
                        tint = Red,
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
                        text = "Attendance anomaly detected",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Navy
                    )

                    Spacer(
                        modifier = Modifier.height(3.dp)
                    )

                    Text(
                        text = "Detected today · 12 min ago",
                        fontSize = 10.sp,
                        color = TextGray
                    )
                }

                Column(
                    horizontalAlignment = Alignment.End
                ) {

                    Text(
                        text = "CRITICAL",
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = Red
                    )

                    Spacer(
                        modifier = Modifier.height(5.dp)
                    )

                    Icon(
                        imageVector = Icons.Outlined.ArrowForwardIos,
                        contentDescription = null,
                        tint = Red,
                        modifier = Modifier.size(13.dp)
                    )
                }
            }
        }
    }
}


// ============================================================
// SECTION HEADER
// ============================================================

@Composable
private fun SectionHeader(
    title: String,
    action: String
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Navy,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = action,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = Blue
        )
    }
}


// ============================================================
// STATUS BADGE
// ============================================================

@Composable
private fun StatusBadge(
    text: String,
    background: Color,
    textColor: Color
) {

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(7.dp))
            .background(background)
            .padding(
                horizontal = 9.dp,
                vertical = 5.dp
            )
    ) {

        Text(
            text = text,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            color = textColor
        )
    }
}


// ============================================================
// BOTTOM NAVIGATION
// CUSTOM CLICKABLE NAVIGATION
// ============================================================

@Composable
private fun DetailsBottomBar(
    onSurveillanceClick: () -> Unit,
    onInspectionClick: () -> Unit,
    onAlertsClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(SurfaceWhite)
            .navigationBarsPadding()
    ) {

        // TOP BORDER
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Border)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(68.dp)
                .background(SurfaceWhite),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // ====================================================
            // OVERVIEW
            // ====================================================

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .clickable {
                        // Already on Overview
                    }
                    .padding(vertical = 8.dp),

                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Icon(
                    imageVector = Icons.Outlined.Business,
                    contentDescription = "Overview",
                    tint = Blue,
                    modifier = Modifier.size(24.dp)
                )

                Spacer(
                    modifier = Modifier.height(3.dp)
                )

                Text(
                    text = "Overview",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Blue
                )
            }


            // ====================================================
            // CCTV
            // ====================================================

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .clickable {
                        onSurveillanceClick()
                    }
                    .padding(vertical = 8.dp),

                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Icon(
                    imageVector = Icons.Default.Videocam,
                    contentDescription = "CCTV",
                    tint = TextGray,
                    modifier = Modifier.size(24.dp)
                )

                Spacer(
                    modifier = Modifier.height(3.dp)
                )

                Text(
                    text = "CCTV",
                    fontSize = 11.sp,
                    color = TextGray
                )
            }


            // ====================================================
            // INSPECTIONS
            // ====================================================

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .clickable {
                        onInspectionClick()
                    }
                    .padding(vertical = 8.dp),

                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Icon(
                    imageVector = Icons.Default.Assignment,
                    contentDescription = "Inspections",
                    tint = TextGray,
                    modifier = Modifier.size(24.dp)
                )

                Spacer(
                    modifier = Modifier.height(3.dp)
                )

                Text(
                    text = "Inspections",
                    fontSize = 11.sp,
                    color = TextGray
                )
            }


            // ====================================================
            // ALERTS
            // ====================================================

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .clickable {
                        onAlertsClick()
                    }
                    .padding(vertical = 8.dp),

                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = "Alerts",
                    tint = TextGray,
                    modifier = Modifier.size(24.dp)
                )

                Spacer(
                    modifier = Modifier.height(3.dp)
                )

                Text(
                    text = "Alerts",
                    fontSize = 11.sp,
                    color = TextGray
                )
            }
        }
    }
}