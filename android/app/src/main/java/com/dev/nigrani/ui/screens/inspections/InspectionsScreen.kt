package com.dev.nigrani.ui.screens.inspections

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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class InspectionItem(
    val id: String,
    val institute: String,
    val location: String,
    val type: String,
    val inspector: String,
    val date: String,
    val priority: String,
    val status: String,
    val reason: String,

    // GPS coordinates of the institute
    val latitude: Double,
    val longitude: Double,

    // Maximum distance allowed for location verification
    val allowedRadiusMeters: Float = 100f
)

@Composable
fun InspectionsScreen(
    onBackClick: () -> Unit = {},
    onInspectionClick: (InspectionItem) -> Unit = {}
) {

    /*
     * -------------------------------------------------------------------------
     * TEMPORARY GPS DATA
     * -------------------------------------------------------------------------
     *
     * These coordinates are only for frontend GPS testing.
     *
     * Later these values will come from the backend/database for each institute.
     *
     * DO NOT treat these as the real coordinates of the institutes.
     */

    val inspections = remember {

        listOf(

            // -----------------------------------------------------------------
            // 1. SUNRISE REHABILITATION CENTRE
            // -----------------------------------------------------------------

            InspectionItem(
                id = "INS-2026-0905-014",
                institute = "Sunrise Rehabilitation Centre",
                location = "Varanasi, Uttar Pradesh",
                type = "Surprise Inspection",
                inspector = "Rajesh Kumar",
                date = "05 Sep 2026 • 18:00",
                priority = "Critical",
                status = "Assigned",
                reason = "Attendance anomaly detected",

                latitude = 25.3176,
                longitude = 82.9739,
                allowedRadiusMeters = 100f
            ),

            // -----------------------------------------------------------------
            // 2. HOPE CARE INSTITUTE
            // -----------------------------------------------------------------

            InspectionItem(
                id = "INS-2026-0905-011",
                institute = "Hope Care Institute",
                location = "Lucknow, Uttar Pradesh",
                type = "Routine Inspection",
                inspector = "Amit Sharma",
                date = "05 Sep 2026 • 16:30",
                priority = "Normal",
                status = "Pending",
                reason = "Scheduled inspection",

                latitude = 22.4000,
                longitude = 82.9739,
                allowedRadiusMeters = 100f
            ),

            // -----------------------------------------------------------------
            // 3. SAHYOG WELFARE CENTRE
            // -----------------------------------------------------------------

            InspectionItem(
                id = "INS-2026-0904-027",
                institute = "Sahyog Welfare Centre",
                location = "Jaipur, Rajasthan",
                type = "Surprise Inspection",
                inspector = "Priya Singh",
                date = "04 Sep 2026 • 17:00",
                priority = "Critical",
                status = "In Progress",
                reason = "CCTV tampering detected",

                latitude = 29.8555,
                longitude = 82.7600,
                allowedRadiusMeters = 100f
            ),

            // -----------------------------------------------------------------
            // 4. JEEVAN JYOTI FOUNDATION
            // -----------------------------------------------------------------

            InspectionItem(
                id = "INS-2026-0904-018",
                institute = "Jeevan Jyoti Foundation",
                location = "Bhopal, Madhya Pradesh",
                type = "Verification Inspection",
                inspector = "Vikas Verma",
                date = "04 Sep 2026 • Completed",
                priority = "Normal",
                status = "Completed",
                reason = "CCTV offline verification",

                latitude = 23.2599,
                longitude = 77.4126,
                allowedRadiusMeters = 100f
            ),

            // -----------------------------------------------------------------
            // 5. NAVJEEVAN SUPPORT CENTRE
            // -----------------------------------------------------------------

            InspectionItem(
                id = "INS-2026-0903-009",
                institute = "Navjeevan Support Centre",
                location = "Patna, Bihar",
                type = "Routine Inspection",
                inspector = "Neha Gupta",
                date = "03 Sep 2026 • Completed",
                priority = "Normal",
                status = "Completed",
                reason = "Scheduled inspection",

                latitude = 25.5941,
                longitude = 85.1376,
                allowedRadiusMeters = 100f
            )
        )
    }

    var searchText by remember {
        mutableStateOf("")
    }

    var selectedFilter by remember {
        mutableStateOf("All")
    }

    val filters = listOf(
        "All",
        "Pending",
        "Assigned",
        "In Progress",
        "Completed"
    )

    val filteredInspections = inspections.filter { inspection ->

        val matchesSearch =
            searchText.isBlank() ||
                    inspection.institute.contains(
                        searchText,
                        ignoreCase = true
                    ) ||
                    inspection.location.contains(
                        searchText,
                        ignoreCase = true
                    ) ||
                    inspection.id.contains(
                        searchText,
                        ignoreCase = true
                    )

        val matchesFilter =
            selectedFilter == "All" ||
                    inspection.status == selectedFilter

        matchesSearch && matchesFilter
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F9FC))
    ) {

        // ================================================================
        // HEADER
        // ================================================================

        item {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(
                        horizontal = 8.dp,
                        vertical = 8.dp
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(
                    onClick = onBackClick
                ) {

                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color(0xFF17324D)
                    )
                }

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = "Inspections",
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF17324D)
                    )

                    Text(
                        text = "Inspection command center",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }

                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = Color(0xFFE8F5E9)
                ) {

                    Row(
                        modifier = Modifier.padding(
                            horizontal = 10.dp,
                            vertical = 7.dp
                        ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Box(
                            modifier = Modifier
                                .width(7.dp)
                                .height(7.dp)
                                .background(
                                    Color(0xFF2E7D32),
                                    RoundedCornerShape(50)
                                )
                        )

                        Spacer(
                            modifier = Modifier.width(6.dp)
                        )

                        Text(
                            text = "LIVE",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2E7D32)
                        )
                    }
                }
            }
        }

        // ================================================================
        // SUMMARY
        // ================================================================

        item {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                InspectionStat(
                    modifier = Modifier.weight(1f),
                    value = inspections.size.toString(),
                    label = "Total",
                    icon = Icons.Default.Assignment
                )

                InspectionStat(
                    modifier = Modifier.weight(1f),
                    value = inspections.count {
                        it.status == "Pending" ||
                                it.status == "Assigned"
                    }.toString(),
                    label = "Pending",
                    icon = Icons.Default.CalendarToday
                )

                InspectionStat(
                    modifier = Modifier.weight(1f),
                    value = inspections.count {
                        it.priority == "Critical"
                    }.toString(),
                    label = "Critical",
                    icon = Icons.Default.Warning
                )
            }

            Spacer(
                modifier = Modifier.height(14.dp)
            )
        }

        // ================================================================
        // SEARCH
        // ================================================================

        item {

            OutlinedTextField(
                value = searchText,
                onValueChange = {
                    searchText = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                singleLine = true,
                leadingIcon = {

                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )
                },
                placeholder = {

                    Text(
                        text = "Search inspections"
                    )
                },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF174A7E),
                    unfocusedBorderColor = Color(0xFFD0D7DE),
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )
        }

        // ================================================================
        // FILTERS
        // ================================================================

        item {

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp),
                verticalArrangement = Arrangement.Center
            ) {

                item {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {

                        filters.forEach { filter ->

                            val selected =
                                selectedFilter == filter

                            Surface(
                                modifier = Modifier.clickable {
                                    selectedFilter = filter
                                },
                                shape = RoundedCornerShape(20.dp),
                                color =
                                    if (selected)
                                        Color(0xFF174A7E)
                                    else
                                        Color.White,
                                border =
                                    if (!selected)
                                        androidx.compose.foundation.BorderStroke(
                                            1.dp,
                                            Color(0xFFD0D7DE)
                                        )
                                    else
                                        null
                            ) {

                                Text(
                                    text = filter,
                                    modifier = Modifier.padding(
                                        horizontal = 13.dp,
                                        vertical = 7.dp
                                    ),
                                    fontSize = 11.sp,
                                    fontWeight =
                                        if (selected)
                                            FontWeight.Bold
                                        else
                                            FontWeight.Normal,
                                    color =
                                        if (selected)
                                            Color.White
                                        else
                                            Color(0xFF52667A)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )
        }

        // ================================================================
        // SECTION TITLE
        // ================================================================

        item {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "Inspection Queue",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF17324D)
                )

                Text(
                    text = "${filteredInspections.size} inspections",
                    fontSize = 11.sp,
                    color = Color.Gray
                )
            }

            Spacer(
                modifier = Modifier.height(8.dp)
            )
        }

        // ================================================================
        // INSPECTION CARDS
        // ================================================================

        items(
            filteredInspections,
            key = {
                it.id
            }
        ) { inspection ->

            InspectionCard(
                inspection = inspection,
                onClick = {
                    onInspectionClick(inspection)
                }
            )
        }

        // ================================================================
        // EMPTY STATE
        // ================================================================

        if (filteredInspections.isEmpty()) {

            item {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(40.dp),
                    contentAlignment = Alignment.Center
                ) {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Icon(
                            imageVector = Icons.Default.Assignment,
                            contentDescription = null,
                            tint = Color(0xFF9AA7B5)
                        )

                        Spacer(
                            modifier = Modifier.height(10.dp)
                        )

                        Text(
                            text = "No inspections found",
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF52667A)
                        )

                        Text(
                            text = "Try another search or filter",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                }
            }
        }

        item {

            Spacer(
                modifier = Modifier.height(24.dp)
            )
        }
    }
}


// ============================================================================
// INSPECTION STAT
// ============================================================================

@Composable
private fun InspectionStat(
    modifier: Modifier,
    value: String,
    label: String,
    icon: ImageVector
) {

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {

        Column(
            modifier = Modifier.padding(12.dp)
        ) {

            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier
                    .width(18.dp)
                    .height(18.dp),
                tint = Color(0xFF174A7E)
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            Text(
                text = value,
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF17324D)
            )

            Text(
                text = label,
                fontSize = 10.sp,
                color = Color.Gray
            )
        }
    }
}


// ============================================================================
// INSPECTION CARD
// ============================================================================

@Composable
private fun InspectionCard(
    inspection: InspectionItem,
    onClick: () -> Unit
) {

    val priorityColor =
        if (inspection.priority == "Critical")
            Color(0xFFD32F2F)
        else
            Color(0xFF52667A)

    val statusColor =
        when (inspection.status) {

            "Completed" ->
                Color(0xFF2E7D32)

            "In Progress" ->
                Color(0xFF2563A6)

            "Assigned" ->
                Color(0xFFD97706)

            else ->
                Color(0xFF64748B)
        }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 5.dp
            )
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {

        Column(
            modifier = Modifier.padding(14.dp)
        ) {

            // ------------------------------------------------------------
            // TOP ROW
            // ------------------------------------------------------------

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = inspection.institute,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF17324D)
                    )

                    Spacer(
                        modifier = Modifier.height(3.dp)
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            modifier = Modifier
                                .width(14.dp)
                                .height(14.dp),
                            tint = Color.Gray
                        )

                        Spacer(
                            modifier = Modifier.width(3.dp)
                        )

                        Text(
                            text = inspection.location,
                            fontSize = 11.sp,
                            color = Color.Gray
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = priorityColor.copy(alpha = 0.10f)
                ) {

                    Text(
                        text = inspection.priority.uppercase(),
                        modifier = Modifier.padding(
                            horizontal = 8.dp,
                            vertical = 5.dp
                        ),
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = priorityColor
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            // ------------------------------------------------------------
            // TYPE + STATUS
            // ------------------------------------------------------------

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = inspection.type,
                    fontSize = 11.sp,
                    color = Color(0xFF52667A),
                    modifier = Modifier.weight(1f)
                )

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = statusColor.copy(alpha = 0.10f)
                ) {

                    Text(
                        text = inspection.status,
                        modifier = Modifier.padding(
                            horizontal = 9.dp,
                            vertical = 5.dp
                        ),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = statusColor
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            // ------------------------------------------------------------
            // REASON
            // ------------------------------------------------------------

            Surface(
                shape = RoundedCornerShape(8.dp),
                color = Color(0xFFF4F6F8)
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(9.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = null,
                        modifier = Modifier
                            .width(15.dp)
                            .height(15.dp),
                        tint =
                            if (inspection.priority == "Critical")
                                Color(0xFFD32F2F)
                            else
                                Color(0xFF64748B)
                    )

                    Spacer(
                        modifier = Modifier.width(7.dp)
                    )

                    Text(
                        text = inspection.reason,
                        fontSize = 11.sp,
                        color = Color(0xFF52667A),
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            // ------------------------------------------------------------
            // INSPECTOR + DATE
            // ------------------------------------------------------------

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier
                        .width(15.dp)
                        .height(15.dp),
                    tint = Color.Gray
                )

                Spacer(
                    modifier = Modifier.width(5.dp)
                )

                Text(
                    text = inspection.inspector,
                    fontSize = 11.sp,
                    color = Color.Gray
                )

                Spacer(
                    modifier = Modifier.weight(1f)
                )

                Icon(
                    imageVector = Icons.Default.CalendarToday,
                    contentDescription = null,
                    modifier = Modifier
                        .width(14.dp)
                        .height(14.dp),
                    tint = Color.Gray
                )

                Spacer(
                    modifier = Modifier.width(4.dp)
                )

                Text(
                    text = inspection.date,
                    fontSize = 10.sp,
                    color = Color.Gray
                )

                Spacer(
                    modifier = Modifier.width(5.dp)
                )

                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = null,
                    tint = Color(0xFF174A7E)
                )
            }

            Spacer(
                modifier = Modifier.height(5.dp)
            )

            Text(
                text = inspection.id,
                fontSize = 9.sp,
                color = Color(0xFF9AA7B5)
            )
        }
    }
}