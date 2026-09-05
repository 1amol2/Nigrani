package com.dev.nigrani.ui.screens.alerts

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.outlined.Business
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Visibility
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


// ====================================================
// COLORS
// ====================================================

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


// ====================================================
// ALERT MODEL
// ====================================================

data class AlertItem(
    val title: String,
    val institute: String,
    val location: String,
    val time: String,
    val severity: String,
    val description: String,
    val confidence: String,
    val icon: ImageVector
)


// ====================================================
// SAMPLE ALERT DATA
// ====================================================

private val alerts = listOf(

    AlertItem(
        title = "Attendance anomaly",
        institute = "Sunrise Rehabilitation Centre",
        location = "Varanasi, Uttar Pradesh",
        time = "12 min ago",
        severity = "CRITICAL",
        description = "AI detected attendance significantly below the reported value.",
        confidence = "96%",
        icon = Icons.Default.Error
    ),

    AlertItem(
        title = "CCTV tampering detected",
        institute = "Sahyog Welfare Centre",
        location = "Jaipur, Rajasthan",
        time = "34 min ago",
        severity = "CRITICAL",
        description = "Camera obstruction or intentional feed interruption detected.",
        confidence = "91%",
        icon = Icons.Default.Videocam
    ),

    AlertItem(
        title = "Unusual inactivity",
        institute = "Hope Care Institute",
        location = "Lucknow, Uttar Pradesh",
        time = "1 hr ago",
        severity = "WARNING",
        description = "AI detected unusually low activity during operating hours.",
        confidence = "84%",
        icon = Icons.Default.Error
    ),

    AlertItem(
        title = "Restricted area movement",
        institute = "Seva Support Centre",
        location = "Bhopal, Madhya Pradesh",
        time = "2 hrs ago",
        severity = "WARNING",
        description = "Movement detected in a restricted monitoring zone.",
        confidence = "79%",
        icon = Icons.Default.LocationOn
    )
)


// ====================================================
// MAIN SCREEN
// ====================================================

@Composable
fun AlertsScreen(
    onBackClick: () -> Unit = {},
    onAlertClick: (AlertItem) -> Unit = {},
    onInstitutesClick: () -> Unit = {},
    onSurveillanceClick: () -> Unit = {}

) {

    var selectedFilter by remember {
        mutableStateOf("All")
    }


    val filteredAlerts = when (selectedFilter) {

        "Critical" ->
            alerts.filter {
                it.severity == "CRITICAL"
            }

        "Warning" ->
            alerts.filter {
                it.severity == "WARNING"
            }

        else ->
            alerts
    }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Background,

        bottomBar = {
            AlertsBottomBar(
                onInstitutesClick = onInstitutesClick,
                onSurveillanceClick = onSurveillanceClick

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

            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            item {

                AlertsHeader(
                    onBackClick = onBackClick
                )
            }


            item {

                AlertSummary()
            }


            item {

                FilterBar(
                    selectedFilter = selectedFilter,
                    onFilterSelected = {
                        selectedFilter = it
                    }
                )
            }


            item {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {

                        Text(
                            text = "AI Detected Alerts",
                            fontSize = 19.sp,
                            fontWeight = FontWeight.Bold,
                            color = Navy
                        )

                        Text(
                            text = "${filteredAlerts.size} alerts require monitoring",
                            fontSize = 11.sp,
                            color = TextGray
                        )
                    }


                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(SoftBlue)
                            .padding(
                                horizontal = 9.dp,
                                vertical = 6.dp
                            )
                    ) {

                        Text(
                            text = "AI ACTIVE",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = Blue
                        )
                    }
                }
            }


            items(
                items = filteredAlerts
            ) { alert ->

                AlertCard(
                    alert = alert,
                    onClick = {
                        onAlertClick(alert)
                    }
                )
            }
        }
    }
}


// ====================================================
// HEADER
// ====================================================

@Composable
private fun AlertsHeader(
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
                text = "AI Alerts",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Navy
            )

            Text(
                text = "AI-powered monitoring anomalies",
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


// ====================================================
// SUMMARY
// ====================================================

@Composable
private fun AlertSummary() {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(9.dp)
    ) {

        AlertSummaryCard(
            modifier = Modifier.weight(1f),
            value = "08",
            label = "Total",
            color = Navy,
            background = SoftBlue
        )

        AlertSummaryCard(
            modifier = Modifier.weight(1f),
            value = "02",
            label = "Critical",
            color = Red,
            background = SoftRed
        )

        AlertSummaryCard(
            modifier = Modifier.weight(1f),
            value = "02",
            label = "Warning",
            color = Amber,
            background = SoftAmber
        )
    }
}


@Composable
private fun AlertSummaryCard(
    modifier: Modifier,
    value: String,
    label: String,
    color: Color,
    background: Color
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
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(11.dp)
        ) {

            Box(
                modifier = Modifier
                    .size(27.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(background),

                contentAlignment = Alignment.Center
            ) {

                Box(
                    modifier = Modifier
                        .size(7.dp)
                        .clip(CircleShape)
                        .background(color)
                )
            }

            Spacer(
                modifier = Modifier.height(7.dp)
            )

            Text(
                text = value,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
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


// ====================================================
// FILTER BAR
// ====================================================

@Composable
private fun FilterBar(
    selectedFilter: String,
    onFilterSelected: (String) -> Unit
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        FilterChip(
            text = "All",
            selected = selectedFilter == "All",
            onClick = {
                onFilterSelected("All")
            }
        )

        FilterChip(
            text = "Critical",
            selected = selectedFilter == "Critical",
            onClick = {
                onFilterSelected("Critical")
            }
        )

        FilterChip(
            text = "Warning",
            selected = selectedFilter == "Warning",
            onClick = {
                onFilterSelected("Warning")
            }
        )
    }
}


@Composable
private fun FilterChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {

    val background = if (selected) {
        Navy
    } else {
        SurfaceWhite
    }

    val textColor = if (selected) {
        Color.White
    } else {
        TextGray
    }


    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(background)
            .clickable {
                onClick()
            }
            .padding(
                horizontal = 14.dp,
                vertical = 8.dp
            )
    ) {

        Text(
            text = text,
            fontSize = 11.sp,
            fontWeight = if (selected) {
                FontWeight.Bold
            } else {
                FontWeight.Medium
            },
            color = textColor
        )
    }
}


// ====================================================
// ALERT CARD
// ====================================================

@Composable
private fun AlertCard(
    alert: AlertItem,
    onClick: () -> Unit
) {

    val critical = alert.severity == "CRITICAL"

    val severityColor = if (critical) {
        Red
    } else {
        Amber
    }

    val severityBackground = if (critical) {
        SoftRed
    } else {
        SoftAmber
    }


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

        Column(
            modifier = Modifier.padding(14.dp)
        ) {

            Row(
                verticalAlignment = Alignment.Top
            ) {

                Box(
                    modifier = Modifier
                        .size(45.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(severityBackground),

                    contentAlignment = Alignment.Center
                ) {

                    Icon(
                        imageVector = alert.icon,
                        contentDescription = null,
                        tint = severityColor,
                        modifier = Modifier.size(22.dp)
                    )
                }


                Spacer(
                    modifier = Modifier.width(11.dp)
                )


                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = alert.title,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Navy,
                            modifier = Modifier.weight(1f)
                        )


                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(severityBackground)
                                .padding(
                                    horizontal = 7.dp,
                                    vertical = 5.dp
                                )
                        ) {

                            Text(
                                text = alert.severity,
                                fontSize = 8.sp,
                                fontWeight = FontWeight.Bold,
                                color = severityColor
                            )
                        }
                    }


                    Spacer(
                        modifier = Modifier.height(4.dp)
                    )


                    Text(
                        text = alert.institute,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = Navy
                    )


                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = TextGray,
                            modifier = Modifier.size(12.dp)
                        )

                        Spacer(
                            modifier = Modifier.width(2.dp)
                        )

                        Text(
                            text = alert.location,
                            fontSize = 10.sp,
                            color = TextGray
                        )
                    }
                }
            }


            Spacer(
                modifier = Modifier.height(12.dp)
            )


            Text(
                text = alert.description,
                fontSize = 11.sp,
                color = TextGray,
                lineHeight = 16.sp
            )


            Spacer(
                modifier = Modifier.height(12.dp)
            )


            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = "AI confidence",
                        fontSize = 9.sp,
                        color = TextGray
                    )

                    Text(
                        text = alert.confidence,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Blue
                    )
                }


                Column(
                    horizontalAlignment = Alignment.End
                ) {

                    Text(
                        text = "Detected",
                        fontSize = 9.sp,
                        color = TextGray
                    )

                    Text(
                        text = alert.time,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Navy
                    )
                }
            }


            Spacer(
                modifier = Modifier.height(11.dp)
            )


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                ActionButton(
                    modifier = Modifier.weight(1f),
                    text = "View Evidence",
                    icon = Icons.Outlined.Visibility,
                    onClick = onClick
                )

                ActionButton(
                    modifier = Modifier.weight(1f),
                    text = "Assign Inspection",
                    icon = Icons.Outlined.Business,
                    onClick = onClick
                )
            }
        }
    }
}


// ====================================================
// ACTION BUTTON
// ====================================================

@Composable
private fun ActionButton(
    modifier: Modifier,
    text: String,
    icon: ImageVector,
    onClick: () -> Unit
) {

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(9.dp))
            .background(SoftBlue)
            .clickable {
                onClick()
            }
            .padding(
                horizontal = 8.dp,
                vertical = 8.dp
            ),

        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Blue,
            modifier = Modifier.size(15.dp)
        )

        Spacer(
            modifier = Modifier.width(5.dp)
        )

        Text(
            text = text,
            fontSize = 9.sp,
            fontWeight = FontWeight.SemiBold,
            color = Blue
        )
    }
}


// ====================================================
// BOTTOM NAVIGATION
// ====================================================

@Composable
private fun AlertsBottomBar(
    onInstitutesClick: () -> Unit,
    onSurveillanceClick: () -> Unit
) {

    NavigationBar(
        modifier = Modifier.navigationBarsPadding(),
        containerColor = SurfaceWhite,
        tonalElevation = 0.dp
    ) {

        NavigationBarItem(
            selected = false,
            onClick = {
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


        NavigationBarItem(
            selected = false,
            onClick = onSurveillanceClick,
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


        NavigationBarItem(
            selected = true,
            onClick = {},
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
    }
}