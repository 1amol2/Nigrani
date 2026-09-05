package com.dev.nigrani.ui.screens.inspections

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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun InspectionAssignmentScreen(
    inspection: InspectionItem? = null,
    onBackClick: () -> Unit = {},
    onStartInspectionClick: () -> Unit = {}
) {

    /*
     * Fallback data is kept so this screen never crashes if it is opened
     * without a selected inspection.
     */
    val currentInspection = inspection ?: InspectionItem(
        id = "INS-2026-0905-014",
        institute = "Sunrise Rehabilitation Centre",
        location = "Varanasi, Uttar Pradesh",
        type = "Surprise Inspection",
        inspector = "Rajesh Kumar",
        date = "05 Sep 2026 • 18:00",
        priority = "Critical",
        status = "Assigned",
        reason = "Attendance anomaly detected"
    )

    val scrollState = rememberScrollState()

    val isCritical =
        currentInspection.priority.equals("Critical", ignoreCase = true)

    val priorityBackground =
        if (isCritical) Color(0xFFFFF7ED)
        else Color(0xFFEFF6FF)

    val priorityBorder =
        if (isCritical) Color(0xFFF59E0B)
        else Color(0xFFBFDBFE)

    val priorityIconBackground =
        if (isCritical) Color(0xFFFFE8CC)
        else Color(0xFFDCEEFF)

    val priorityIconTint =
        if (isCritical) Color(0xFFD97706)
        else Color(0xFF2563A6)

    val priorityTextColor =
        if (isCritical) Color(0xFFB45309)
        else Color(0xFF1E5A92)

    val prioritySubTextColor =
        if (isCritical) Color(0xFF92400E)
        else Color(0xFF52677D)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        // ============================================================
        // TOP BAR
        // ============================================================

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(
                onClick = onBackClick
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }

            Text(
                text = "Inspection Assignment",
                modifier = Modifier.weight(1f),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            IconButton(
                onClick = {}
            ) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notifications",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }

        // ============================================================
        // SCROLLABLE CONTENT
        // ============================================================

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            Spacer(modifier = Modifier.height(2.dp))

            // ========================================================
            // INSPECTION TYPE / PRIORITY
            // ========================================================

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = priorityBackground
                ),
                border = BorderStroke(
                    1.dp,
                    priorityBorder
                )
            ) {

                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .background(
                                priorityIconBackground,
                                RoundedCornerShape(12.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {

                        Icon(
                            imageVector = Icons.Default.Assignment,
                            contentDescription = null,
                            modifier = Modifier.size(23.dp),
                            tint = priorityIconTint
                        )
                    }

                    Spacer(modifier = Modifier.size(12.dp))

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {

                        Text(
                            text = currentInspection.type.uppercase(),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = priorityTextColor
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "${currentInspection.priority} Priority",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = prioritySubTextColor
                        )
                    }
                }
            }

            // ========================================================
            // INSTITUTE
            // ========================================================

            SectionTitle(
                title = "INSTITUTE"
            )

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
                        text = currentInspection.institute,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            modifier = Modifier.size(17.dp),
                            tint = Color(0xFF2563A6)
                        )

                        Spacer(modifier = Modifier.size(5.dp))

                        Text(
                            text = currentInspection.location,
                            fontSize = 13.sp,
                            color = Color(0xFF64748B)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Color(0xFFF8FAFC),
                                RoundedCornerShape(10.dp)
                            )
                            .padding(
                                horizontal = 12.dp,
                                vertical = 10.dp
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = "Inspection ID",
                            fontSize = 11.sp,
                            color = Color(0xFF64748B),
                            modifier = Modifier.weight(1f)
                        )

                        Text(
                            text = currentInspection.id,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF334155)
                        )
                    }
                }
            }

            // ========================================================
            // INSPECTION REASON
            // ========================================================

            SectionTitle(
                title = "INSPECTION REASON"
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
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(
                        text = currentInspection.reason,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Color(0xFFFFF1F2),
                                RoundedCornerShape(9.dp)
                            )
                            .padding(
                                horizontal = 11.dp,
                                vertical = 9.dp
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = "AI-triggered monitoring event",
                            fontSize = 11.sp,
                            color = Color(0xFF9F1239),
                            modifier = Modifier.weight(1f)
                        )

                        Text(
                            text = "96%",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFDC2626)
                        )
                    }
                }
            }

            // ========================================================
            // ASSIGNMENT DETAILS
            // ========================================================

            SectionTitle(
                title = "ASSIGNMENT DETAILS"
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
                    verticalArrangement = Arrangement.spacedBy(15.dp)
                ) {

                    AssignmentRow(
                        icon = Icons.Default.Person,
                        label = "Inspector",
                        value = currentInspection.inspector
                    )

                    AssignmentRow(
                        icon = Icons.Default.CalendarToday,
                        label = "Inspection date",
                        value = currentInspection.date
                    )

                    AssignmentRow(
                        icon = Icons.Default.Schedule,
                        label = "Status",
                        value = currentInspection.status
                    )
                }
            }

            // ========================================================
            // STATUS
            // ========================================================

            SectionTitle(
                title = "INSPECTION STATUS"
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFF0FDF4)
                ),
                border = BorderStroke(
                    1.dp,
                    Color(0xFFBBF7D0)
                )
            ) {

                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        modifier = Modifier.size(22.dp),
                        tint = Color(0xFF16A34A)
                    )

                    Spacer(modifier = Modifier.size(10.dp))

                    Column {

                        Text(
                            text = currentInspection.status,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF166534)
                        )

                        Spacer(modifier = Modifier.height(3.dp))

                        Text(
                            text = statusDescription(currentInspection.status),
                            fontSize = 12.sp,
                            color = Color(0xFF4D7C5B)
                        )
                    }
                }
            }

            // ========================================================
            // LOCATION VERIFICATION
            // ========================================================

            SectionTitle(
                title = "LOCATION"
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFEFF6FF)
                ),
                border = BorderStroke(
                    1.dp,
                    Color(0xFFBFDBFE)
                )
            ) {

                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = Color(0xFF2563A6)
                    )

                    Spacer(modifier = Modifier.size(10.dp))

                    Column {

                        Text(
                            text = "Location verification required",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1E3A5F)
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "The inspector must verify their location before the inspection can begin.",
                            fontSize = 12.sp,
                            color = Color(0xFF52677D),
                            lineHeight = 17.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(3.dp))

            // ========================================================
            // START INSPECTION
            // ========================================================

            Button(
                onClick = onStartInspectionClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF174A7E)
                )
            ) {

                Text(
                    text = "START INSPECTION",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

// ================================================================
// SECTION TITLE
// ================================================================

@Composable
private fun SectionTitle(
    title: String
) {

    Text(
        text = title,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF64748B),
        letterSpacing = 0.5.sp
    )
}

// ================================================================
// ASSIGNMENT ROW
// ================================================================

@Composable
private fun AssignmentRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(36.dp)
                .background(
                    Color(0xFFEFF6FF),
                    RoundedCornerShape(10.dp)
                ),
            contentAlignment = Alignment.Center
        ) {

            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(18.dp),
                tint = Color(0xFF2563A6)
            )
        }

        Spacer(modifier = Modifier.size(11.dp))

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

// ================================================================
// STATUS DESCRIPTION
// ================================================================

private fun statusDescription(
    status: String
): String {

    return when (status.lowercase()) {

        "assigned" ->
            "Waiting for inspector to begin"

        "pending" ->
            "Inspection is awaiting assignment"

        "in progress" ->
            "Inspection is currently underway"

        "completed" ->
            "Inspection has been completed"

        else ->
            "Inspection status is currently $status"
    }
}