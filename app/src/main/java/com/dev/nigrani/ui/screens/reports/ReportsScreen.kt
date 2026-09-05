package com.dev.nigrani.ui.screens.reports

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.FilterList
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.Surface
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
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
import androidx.compose.foundation.layout.statusBarsPadding

private val Navy = Color(0xFF123B63)
private val Blue = Color(0xFF21639D)
private val Green = Color(0xFF159A72)
private val Red = Color(0xFFD23838)
private val Amber = Color(0xFFE1A62A)

private val Background = Color(0xFFF6F8FB)
private val Surface = Color.White
private val SoftBlue = Color(0xFFEAF3FA)
private val SoftGreen = Color(0xFFEAF8F3)
private val SoftRed = Color(0xFFFFEEEE)
private val SoftAmber = Color(0xFFFFF7E5)
private val TextGray = Color(0xFF7C8794)
private val Border = Color(0xFFE2E7ED)


data class ReportItem(
    val id: String,
    val institute: String,
    val location: String,
    val inspectionType: String,
    val inspector: String,
    val date: String,
    val status: String,
    val compliance: String
)


@Composable
fun ReportsScreen(
    onBackClick: () -> Unit = {},
    onReportClick: (ReportItem) -> Unit = {}
) {

    var searchQuery by remember {
        mutableStateOf("")
    }

    var selectedFilter by remember {
        mutableStateOf("All")
    }

    val reports = remember {
        listOf(

            ReportItem(
                id = "INS-2026-0905-014",
                institute = "Sunrise Rehabilitation Centre",
                location = "Varanasi, Uttar Pradesh",
                inspectionType = "Surprise Inspection",
                inspector = "Rajesh Kumar",
                date = "05 Sep 2026 • 14:45",
                status = "Critical",
                compliance = "71%"
            ),

            ReportItem(
                id = "INS-2026-0904-008",
                institute = "Hope Care Institute",
                location = "Lucknow, Uttar Pradesh",
                inspectionType = "Routine Inspection",
                inspector = "Amit Sharma",
                date = "04 Sep 2026 • 16:20",
                status = "Compliant",
                compliance = "96%"
            ),

            ReportItem(
                id = "INS-2026-0903-021",
                institute = "Sahyog Welfare Centre",
                location = "Jaipur, Rajasthan",
                inspectionType = "Surprise Inspection",
                inspector = "Priya Singh",
                date = "03 Sep 2026 • 12:10",
                status = "Needs Attention",
                compliance = "84%"
            ),

            ReportItem(
                id = "INS-2026-0902-006",
                institute = "Jeevan Jyoti Foundation",
                location = "Bhopal, Madhya Pradesh",
                inspectionType = "Routine Inspection",
                inspector = "Vikram Patel",
                date = "02 Sep 2026 • 11:35",
                status = "Compliant",
                compliance = "91%"
            ),

            ReportItem(
                id = "INS-2026-0901-017",
                institute = "Navjeevan Support Centre",
                location = "Patna, Bihar",
                inspectionType = "Surprise Inspection",
                inspector = "Neha Verma",
                date = "01 Sep 2026 • 15:05",
                status = "Needs Attention",
                compliance = "79%"
            )
        )
    }

    val filteredReports = reports.filter { report ->

        val matchesSearch =
            report.institute.contains(
                searchQuery,
                ignoreCase = true
            ) ||
                    report.id.contains(
                        searchQuery,
                        ignoreCase = true
                    ) ||
                    report.inspector.contains(
                        searchQuery,
                        ignoreCase = true
                    )

        val matchesFilter =
            selectedFilter == "All" ||
                    report.status == selectedFilter

        matchesSearch && matchesFilter
    }


    Scaffold(
        containerColor = Background,

        topBar = {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .background(Surface)
                    .padding(
                        horizontal = 12.dp,
                        vertical = 10.dp
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(
                    onClick = onBackClick
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ArrowBack,
                        contentDescription = "Back",
                        tint = Navy
                    )
                }

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = "Reports",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Navy
                    )

                    Text(
                        text = "Inspection reports and compliance",
                        fontSize = 12.sp,
                        color = TextGray
                    )
                }

                Icon(
                    imageVector = Icons.Outlined.Description,
                    contentDescription = null,
                    tint = Blue,
                    modifier = Modifier.size(25.dp)
                )
            }
        }

    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),

            contentPadding = androidx.compose.foundation.layout.PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = 14.dp,
                bottom = 24.dp
            ),

            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // ================================================================
            // SUMMARY
            // ================================================================

            item {

                Text(
                    text = "Report Overview",
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold,
                    color = Navy
                )

                Spacer(
                    modifier = Modifier.height(9.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(9.dp)
                ) {

                    ReportStat(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.Assignment,
                        value = "42",
                        label = "Total",
                        tint = Blue,
                        background = SoftBlue
                    )

                    ReportStat(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.CheckCircle,
                        value = "31",
                        label = "Compliant",
                        tint = Green,
                        background = SoftGreen
                    )

                    ReportStat(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.Error,
                        value = "04",
                        label = "Critical",
                        tint = Red,
                        background = SoftRed
                    )
                }
            }


            // ================================================================
            // SEARCH
            // ================================================================

            item {

                OutlinedTextField(
                    value = searchQuery,

                    onValueChange = {
                        searchQuery = it
                    },

                    modifier = Modifier.fillMaxWidth(),

                    singleLine = true,

                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search"
                        )
                    },

                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.FilterList,
                            contentDescription = "Filter"
                        )
                    },

                    placeholder = {
                        Text(
                            text = "Search reports..."
                        )
                    },

                    shape = RoundedCornerShape(13.dp)
                )
            }


            // ================================================================
            // FILTERS
            // ================================================================

            item {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(7.dp)
                ) {

                    FilterChip(
                        selected = selectedFilter == "All",

                        onClick = {
                            selectedFilter = "All"
                        },

                        label = {
                            Text("All")
                        },

                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = SoftBlue,
                            selectedLabelColor = Navy
                        )
                    )

                    FilterChip(
                        selected = selectedFilter == "Compliant",

                        onClick = {
                            selectedFilter = "Compliant"
                        },

                        label = {
                            Text("Compliant")
                        },

                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = SoftGreen,
                            selectedLabelColor = Green
                        )
                    )

                    FilterChip(
                        selected = selectedFilter == "Needs Attention",

                        onClick = {
                            selectedFilter = "Needs Attention"
                        },

                        label = {
                            Text("Attention")
                        },

                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = SoftAmber,
                            selectedLabelColor = Amber
                        )
                    )

                    FilterChip(
                        selected = selectedFilter == "Critical",

                        onClick = {
                            selectedFilter = "Critical"
                        },

                        label = {
                            Text("Critical")
                        },

                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = SoftRed,
                            selectedLabelColor = Red
                        )
                    )
                }
            }


            // ================================================================
            // RESULT COUNT
            // ================================================================

            item {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "${filteredReports.size} reports",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Navy,
                        modifier = Modifier.weight(1f)
                    )

                    Text(
                        text = "Latest first",
                        fontSize = 11.sp,
                        color = TextGray
                    )
                }
            }


            // ================================================================
            // REPORT CARDS
            // ================================================================

            items(
                items = filteredReports,
                key = {
                    it.id
                }
            ) { report ->

                ReportCard(
                    report = report,
                    onClick = {
                        onReportClick(report)
                    }
                )
            }


            // ================================================================
            // EMPTY STATE
            // ================================================================

            if (filteredReports.isEmpty()) {

                item {

                    EmptyReportsState()
                }
            }
        }
    }
}


// ============================================================================
// REPORT STAT
// ============================================================================

@Composable
private fun ReportStat(
    modifier: Modifier,
    icon: ImageVector,
    value: String,
    label: String,
    tint: Color,
    background: Color
) {

    Card(
        modifier = modifier.height(91.dp),

        shape = RoundedCornerShape(15.dp),

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
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),

            verticalArrangement = Arrangement.Center
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .size(31.dp)
                        .clip(
                            RoundedCornerShape(9.dp)
                        )
                        .background(background),

                    contentAlignment = Alignment.Center
                ) {

                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = tint,
                        modifier = Modifier.size(17.dp)
                    )
                }

                Spacer(
                    modifier = Modifier.width(7.dp)
                )

                Text(
                    text = value,
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold,
                    color = Navy
                )
            }

            Spacer(
                modifier = Modifier.height(3.dp)
            )

            Text(
                text = label,
                fontSize = 10.sp,
                color = TextGray
            )
        }
    }
}


// ============================================================================
// REPORT CARD
// ============================================================================

@Composable
private fun ReportCard(
    report: ReportItem,
    onClick: () -> Unit
) {

    val statusColor = when (report.status) {
        "Critical" -> Red
        "Needs Attention" -> Amber
        else -> Green
    }

    val statusBackground = when (report.status) {
        "Critical" -> SoftRed
        "Needs Attention" -> SoftAmber
        else -> SoftGreen
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = onClick
            ),

        shape = RoundedCornerShape(16.dp),

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
                        .size(40.dp)
                        .clip(
                            RoundedCornerShape(11.dp)
                        )
                        .background(SoftBlue),

                    contentAlignment = Alignment.Center
                ) {

                    Icon(
                        imageVector = Icons.Outlined.Description,
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
                        text = report.institute,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Navy
                    )

                    Spacer(
                        modifier = Modifier.height(2.dp)
                    )

                    Text(
                        text = report.location,
                        fontSize = 11.sp,
                        color = TextGray
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(
                            RoundedCornerShape(6.dp)
                        )
                        .background(statusBackground)
                        .padding(
                            horizontal = 8.dp,
                            vertical = 5.dp
                        )
                ) {

                    Text(
                        text = report.status,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = statusColor
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(11.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                ReportInfo(
                    title = "Report ID",
                    value = report.id
                )

                ReportInfo(
                    title = "Compliance",
                    value = report.compliance,
                    valueColor = statusColor
                )
            }

            Spacer(
                modifier = Modifier.height(9.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                ReportInfo(
                    title = "Inspection",
                    value = report.inspectionType
                )

                ReportInfo(
                    title = "Inspector",
                    value = report.inspector
                )
            }

            Spacer(
                modifier = Modifier.height(9.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = report.date,
                    fontSize = 10.sp,
                    color = TextGray,
                    modifier = Modifier.weight(1f)
                )

                TextButton(
                    onClick = onClick
                ) {

                    Text(
                        text = "View Report",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Blue
                    )
                }
            }
        }
    }
}


// ============================================================================
// REPORT INFO
// ============================================================================

@Composable
private fun ReportInfo(
    title: String,
    value: String,
    valueColor: Color = Navy
) {

    Column(
        modifier = Modifier.width(150.dp)
    ) {

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
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            color = valueColor,
            maxLines = 1
        )
    }
}


// ============================================================================
// EMPTY STATE
// ============================================================================

@Composable
private fun EmptyReportsState() {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),

        shape = RoundedCornerShape(16.dp),

        colors = CardDefaults.cardColors(
            containerColor = Surface
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
                imageVector = Icons.Default.Warning,
                contentDescription = null,
                tint = Amber,
                modifier = Modifier.size(34.dp)
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            Text(
                text = "No reports found",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Navy
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Text(
                text = "Try changing your search or filter.",
                fontSize = 12.sp,
                color = TextGray
            )
        }
    }
}