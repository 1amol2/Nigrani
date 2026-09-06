package com.dev.nigrani.ui.screens.institutes

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
import androidx.compose.material.icons.outlined.Assignment
import androidx.compose.material.icons.outlined.Business
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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


// ----------------------------------------------------
// COLORS
// ----------------------------------------------------

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


// ----------------------------------------------------
// DATA MODEL
// ----------------------------------------------------

data class Institute(
    val name: String,
    val location: String,
    val type: String,
    val beneficiaries: String,
    val attendance: String,
    val cctvOnline: Boolean,
    val status: String,
    val lastChecked: String
)


// ----------------------------------------------------
// MOCK DATA
// ----------------------------------------------------

private val instituteList = listOf(

    Institute(
        name = "Sunrise Rehabilitation Centre",
        location = "Varanasi, Uttar Pradesh",
        type = "Rehabilitation Centre",
        beneficiaries = "286",
        attendance = "71%",
        cctvOnline = true,
        status = "Critical",
        lastChecked = "12 min ago"
    ),

    Institute(
        name = "Hope Care Institute",
        location = "Lucknow, Uttar Pradesh",
        type = "Care Institution",
        beneficiaries = "194",
        attendance = "96%",
        cctvOnline = true,
        status = "Healthy",
        lastChecked = "8 min ago"
    ),

    Institute(
        name = "Sahyog Welfare Centre",
        location = "Jaipur, Rajasthan",
        type = "Welfare Centre",
        beneficiaries = "158",
        attendance = "91%",
        cctvOnline = true,
        status = "Healthy",
        lastChecked = "18 min ago"
    ),

    Institute(
        name = "Jeevan Jyoti Foundation",
        location = "Bhopal, Madhya Pradesh",
        type = "Rehabilitation Centre",
        beneficiaries = "221",
        attendance = "87%",
        cctvOnline = false,
        status = "Offline",
        lastChecked = "42 min ago"
    ),

    Institute(
        name = "Navjeevan Support Centre",
        location = "Patna, Bihar",
        type = "Support Centre",
        beneficiaries = "176",
        attendance = "93%",
        cctvOnline = true,
        status = "Healthy",
        lastChecked = "21 min ago"
    )
)


// ----------------------------------------------------
// MAIN SCREEN
// ----------------------------------------------------

@Composable
fun InstitutesScreen(
    onInstituteClick: (Institute) -> Unit = {},
    onDashboardClick: () -> Unit = {},
    onInspectionClick: () -> Unit = {},
    onAlertsClick : () -> Unit = {}
) {

    var searchQuery by remember {
        mutableStateOf("")
    }

    var selectedFilter by remember {
        mutableStateOf("All")
    }


    val filteredInstitutes = instituteList.filter { institute ->

        val searchMatches =
            institute.name.contains(
                searchQuery,
                ignoreCase = true
            ) ||
                    institute.location.contains(
                        searchQuery,
                        ignoreCase = true
                    )


        val filterMatches = when (selectedFilter) {

            "Healthy" -> {
                institute.status == "Healthy"
            }

            "Critical" -> {
                institute.status == "Critical"
            }

            "Offline" -> {
                institute.status == "Offline"
            }

            else -> {
                true
            }
        }


        searchMatches && filterMatches
    }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Background,

        bottomBar = {
            InstitutesBottomBar(
                onDashboardClick = onDashboardClick,
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
                top = 14.dp,
                bottom = 20.dp
            ),

            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {


            // HEADER

            item {
                InstitutesHeader()
            }


            // SEARCH

            item {
                SearchBar(
                    value = searchQuery,
                    onValueChange = {
                        searchQuery = it
                    }
                )
            }


            // FILTERS

            item {
                FilterRow(
                    selectedFilter = selectedFilter,
                    onFilterSelected = {
                        selectedFilter = it
                    }
                )
            }


            // SUMMARY

            item {
                InstituteSummary(
                    visibleCount = filteredInstitutes.size
                )
            }


            // INSTITUTE LIST

            items(
                items = filteredInstitutes,
                key = {
                    it.name
                }
            ) { institute ->

                InstituteCard(
                    institute = institute,
                    onClick = {
                        onInstituteClick(institute)
                    }
                )
            }


            // EMPTY STATE

            if (filteredInstitutes.isEmpty()) {

                item {
                    EmptyState()
                }
            }
        }
    }
}


// ----------------------------------------------------
// HEADER
// ----------------------------------------------------

@Composable
private fun InstitutesHeader() {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(
            modifier = Modifier.weight(1f)
        ) {

            Text(
                text = "Institutes",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = Navy
            )

            Spacer(
                modifier = Modifier.height(3.dp)
            )

            Text(
                text = "Monitor registered institutions",
                fontSize = 14.sp,
                color = TextGray
            )
        }


        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(
                    RoundedCornerShape(13.dp)
                )
                .background(SurfaceWhite),

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
                    .clip(CircleShape)
                    .background(Red)
                    .align(Alignment.TopEnd)
            )
        }
    }
}


// ----------------------------------------------------
// SEARCH BAR
// ----------------------------------------------------

@Composable
private fun SearchBar(
    value: String,
    onValueChange: (String) -> Unit
) {

    OutlinedTextField(
        value = value,

        onValueChange = onValueChange,

        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp),

        singleLine = true,

        placeholder = {
            Text(
                text = "Search institute or location",
                fontSize = 14.sp,
                color = TextGray
            )
        },

        leadingIcon = {

            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = "Search",
                tint = TextGray
            )
        },

        shape = RoundedCornerShape(15.dp),

        colors = OutlinedTextFieldDefaults.colors(

            focusedContainerColor = SurfaceWhite,

            unfocusedContainerColor = SurfaceWhite,

            focusedBorderColor = Blue,

            unfocusedBorderColor = Border,

            cursorColor = Blue
        )
    )
}


// ----------------------------------------------------
// FILTER ROW
// ----------------------------------------------------

@Composable
private fun FilterRow(
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
            text = "Healthy",
            selected = selectedFilter == "Healthy",
            onClick = {
                onFilterSelected("Healthy")
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
            text = "Offline",
            selected = selectedFilter == "Offline",
            onClick = {
                onFilterSelected("Offline")
            }
        )
    }
}


// ----------------------------------------------------
// FILTER CHIP
// ----------------------------------------------------

@Composable
private fun FilterChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {

    Surface(
        modifier = Modifier
            .clip(
                RoundedCornerShape(20.dp)
            )
            .clickable {
                onClick()
            },

        color = if (selected) {
            Navy
        } else {
            SurfaceWhite
        },

        border = if (selected) {
            null
        } else {
            BorderStroke(
                1.dp,
                Border
            )
        },

        shape = RoundedCornerShape(20.dp)
    ) {

        Text(
            text = text,

            modifier = Modifier.padding(
                horizontal = 13.dp,
                vertical = 8.dp
            ),

            fontSize = 12.sp,

            fontWeight = if (selected) {
                FontWeight.SemiBold
            } else {
                FontWeight.Normal
            },

            color = if (selected) {
                Color.White
            } else {
                TextGray
            }
        )
    }
}


// ----------------------------------------------------
// SUMMARY
// ----------------------------------------------------

@Composable
private fun InstituteSummary(
    visibleCount: Int
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(
            modifier = Modifier.weight(1f)
        ) {

            Text(
                text = "$visibleCount Institutes",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Navy
            )

            Text(
                text = "Current monitoring status",
                fontSize = 12.sp,
                color = TextGray
            )
        }


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
                text = "Live",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = Green
            )
        }
    }
}


// ----------------------------------------------------
// INSTITUTE CARD
// ----------------------------------------------------

@Composable
private fun InstituteCard(
    institute: Institute,
    onClick: () -> Unit
) {

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
            modifier = Modifier.padding(15.dp)
        ) {


            Row(
                verticalAlignment = Alignment.Top
            ) {


                // INSTITUTE ICON

                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .clip(
                            RoundedCornerShape(13.dp)
                        )
                        .background(SoftBlue),

                    contentAlignment = Alignment.Center
                ) {

                    Icon(
                        imageVector = Icons.Outlined.Business,
                        contentDescription = null,
                        tint = Blue,
                        modifier = Modifier.size(24.dp)
                    )
                }


                Spacer(
                    modifier = Modifier.width(11.dp)
                )


                // NAME + LOCATION

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = institute.name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Navy
                    )

                    Spacer(
                        modifier = Modifier.height(3.dp)
                    )

                    Text(
                        text = institute.location,
                        fontSize = 12.sp,
                        color = TextGray
                    )

                    Text(
                        text = institute.type,
                        fontSize = 11.sp,
                        color = Color(0xFF9AA3AE)
                    )
                }


                // STATUS

                StatusBadge(
                    status = institute.status
                )
            }


            Spacer(
                modifier = Modifier.height(13.dp)
            )


            // DIVIDER

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Border)
            )


            Spacer(
                modifier = Modifier.height(11.dp)
            )


            // METRICS

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                InstituteMetric(
                    title = "Beneficiaries",
                    value = institute.beneficiaries
                )


                InstituteMetric(
                    title = "Attendance",
                    value = institute.attendance,

                    valueColor =
                        if (
                            (institute.attendance
                                .removeSuffix("%")
                                .toIntOrNull() ?: 0) < 80
                        ) {
                            Red
                        } else {
                            Navy
                        }
                )


                CctvStatus(
                    online = institute.cctvOnline
                )
            }


            Spacer(
                modifier = Modifier.height(9.dp)
            )


            Text(
                text = "Last checked ${institute.lastChecked}",
                fontSize = 10.sp,
                color = Color(0xFF9AA3AE)
            )
        }
    }
}


// ----------------------------------------------------
// STATUS BADGE
// ----------------------------------------------------

@Composable
private fun StatusBadge(
    status: String
) {

    val backgroundColor = when (status) {

        "Critical" -> SoftRed

        "Offline" -> SoftAmber

        else -> SoftGreen
    }


    val textColor = when (status) {

        "Critical" -> Red

        "Offline" -> Amber

        else -> Green
    }


    Box(
        modifier = Modifier
            .clip(
                RoundedCornerShape(7.dp)
            )
            .background(backgroundColor)
            .padding(
                horizontal = 8.dp,
                vertical = 5.dp
            )
    ) {

        Text(
            text = status,
            fontSize = 10.sp,
            fontWeight = FontWeight.SemiBold,
            color = textColor
        )
    }
}


// ----------------------------------------------------
// INSTITUTE METRIC
// ----------------------------------------------------

@Composable
private fun InstituteMetric(
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


// ----------------------------------------------------
// CCTV STATUS
// ----------------------------------------------------

@Composable
private fun CctvStatus(
    online: Boolean
) {

    Column {

        Text(
            text = "CCTV",
            fontSize = 10.sp,
            color = TextGray
        )

        Spacer(
            modifier = Modifier.height(2.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(7.dp)
                    .clip(CircleShape)
                    .background(
                        if (online) {
                            Green
                        } else {
                            Red
                        }
                    )
            )

            Spacer(
                modifier = Modifier.width(5.dp)
            )

            Text(
                text = if (online) {
                    "Online"
                } else {
                    "Offline"
                },

                fontSize = 12.sp,

                fontWeight = FontWeight.SemiBold,

                color = if (online) {
                    Green
                } else {
                    Red
                }
            )
        }
    }
}


// ----------------------------------------------------
// EMPTY STATE
// ----------------------------------------------------

@Composable
private fun EmptyState() {

    Card(
        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(18.dp),

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
                .padding(30.dp),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = null,
                tint = TextGray,
                modifier = Modifier.size(32.dp)
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            Text(
                text = "No institutes found",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Navy
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Text(
                text = "Try another search or filter",
                fontSize = 12.sp,
                color = TextGray
            )
        }
    }
}


// ----------------------------------------------------
// BOTTOM NAVIGATION
// ----------------------------------------------------

@Composable
private fun InstitutesBottomBar(
    onDashboardClick: () -> Unit,
    onInspectionClick: () -> Unit,
    onAlertsClick: () -> Unit
)
{

    NavigationBar(
        modifier = Modifier.navigationBarsPadding(),
        containerColor = SurfaceWhite,
        tonalElevation = 0.dp
    ) {


        NavigationBarItem(
            selected = false,
            onClick = onDashboardClick,
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


        NavigationBarItem(
            selected = true,
            onClick = {},
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
            onClick = onInspectionClick,
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Assignment,
                    contentDescription = "Inspections"
                )
            },
            label = {
                Text("Inspections")
            }
        )


        NavigationBarItem(
            selected = false,
            onClick = onAlertsClick,
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


        NavigationBarItem(
            selected = false,
            onClick = {},
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