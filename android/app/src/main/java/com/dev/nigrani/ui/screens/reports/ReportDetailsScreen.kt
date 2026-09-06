package com.dev.nigrani.ui.screens.reports

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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ReportDetailsScreen(
    report: ReportItem,
    onBackClick: () -> Unit = {}
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F9FC))
    ) {

        item {

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
                        contentDescription = "Back"
                    )
                }

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = "Report Details",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF17324D)
                    )

                    Text(
                        text = report.id,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }

                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = statusColor(report.status).copy(alpha = 0.12f)
                ) {

                    Text(
                        text = report.status.uppercase(),
                        modifier = Modifier.padding(
                            horizontal = 12.dp,
                            vertical = 7.dp
                        ),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = statusColor(report.status)
                    )
                }
            }
        }

        item {

            ReportOverviewCard(report)

            Spacer(modifier = Modifier.height(12.dp))
        }

        item {

            SectionTitle(
                icon = Icons.Default.Description,
                title = "Inspection Information"
            )

            Spacer(modifier = Modifier.height(8.dp))
        }

        item {

            InspectionInformationCard(report)

            Spacer(modifier = Modifier.height(12.dp))
        }

        item {

            SectionTitle(
                icon = Icons.Default.LocationOn,
                title = "Location Verification"
            )

            Spacer(modifier = Modifier.height(8.dp))
        }

        item {

            LocationVerificationCard()

            Spacer(modifier = Modifier.height(12.dp))
        }

        item {

            SectionTitle(
                icon = Icons.Default.CheckCircle,
                title = "Checklist Summary"
            )

            Spacer(modifier = Modifier.height(8.dp))
        }

        item {

            ChecklistSummaryCard(report)

            Spacer(modifier = Modifier.height(12.dp))
        }

        item {

            SectionTitle(
                icon = Icons.Default.Description,
                title = "Key Findings"
            )

            Spacer(modifier = Modifier.height(8.dp))
        }

        item {

            FindingsCard(report)

            Spacer(modifier = Modifier.height(12.dp))
        }

        item {

            SectionTitle(
                icon = Icons.Default.PhotoLibrary,
                title = "Evidence"
            )

            Spacer(modifier = Modifier.height(8.dp))
        }

        item {

            EvidenceCard()

            Spacer(modifier = Modifier.height(12.dp))
        }

        item {

            SectionTitle(
                icon = Icons.Default.Verified,
                title = "Compliance Assessment"
            )

            Spacer(modifier = Modifier.height(8.dp))
        }

        item {

            ComplianceCard(report)

            Spacer(modifier = Modifier.height(28.dp))
        }
    }
}


@Composable
private fun ReportOverviewCard(
    report: ReportItem
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = report.institute,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF17324D)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = report.location,
                fontSize = 13.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                InfoValue(
                    label = "Compliance",
                    value = report.compliance
                )

                InfoValue(
                    label = "Inspection",
                    value = report.inspectionType
                )

                InfoValue(
                    label = "Date",
                    value = report.date
                )
            }
        }
    }
}


@Composable
private fun InspectionInformationCard(
    report: ReportItem
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            DetailRow(
                label = "Report ID",
                value = report.id
            )

            DetailRow(
                label = "Inspection Type",
                value = report.inspectionType
            )

            DetailRow(
                label = "Inspector",
                value = report.inspector
            )

            DetailRow(
                label = "Inspection Date",
                value = report.date
            )

            DetailRow(
                label = "Institute",
                value = report.institute
            )
        }
    }
}


@Composable
private fun LocationVerificationCard() {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = Icons.Default.Verified,
                    contentDescription = null,
                    tint = Color(0xFF2E7D32)
                )

                Spacer(modifier = Modifier.width(10.dp))

                Column {

                    Text(
                        text = "Location Verified",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2E7D32)
                    )

                    Text(
                        text = "Inspector verified presence at institute",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            DetailRow(
                label = "Distance from institute",
                value = "18 metres"
            )

            DetailRow(
                label = "GPS Accuracy",
                value = "±8 metres"
            )

            DetailRow(
                label = "Verification",
                value = "Successful"
            )
        }
    }
}


@Composable
private fun ChecklistSummaryCard(
    report: ReportItem
) {

    val compliant =
        report.compliance == "94%" ||
                report.status.equals("Compliant", ignoreCase = true)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {

        ChecklistItem(
            title = "Beneficiary Attendance",
            status = if (compliant) "Compliant" else "Needs Attention"
        )

        ChecklistItem(
            title = "Staff Availability",
            status = "Compliant"
        )

        ChecklistItem(
            title = "Infrastructure Condition",
            status = "Compliant"
        )

        ChecklistItem(
            title = "Safety & Accessibility",
            status = "Compliant"
        )

        ChecklistItem(
            title = "Records & Registers",
            status = "Compliant"
        )

        ChecklistItem(
            title = "CCTV Surveillance",
            status = if (report.status.equals("Critical", ignoreCase = true))
                "Needs Attention"
            else
                "Compliant"
        )
    }
}


@Composable
private fun FindingsCard(
    report: ReportItem
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text =
                    if (report.status.equals("Critical", ignoreCase = true))
                        "Attendance anomaly requires further review. CCTV and attendance records should be cross-verified."
                    else
                        "Inspection completed successfully. No major compliance concerns were identified during the inspection.",
                fontSize = 14.sp,
                lineHeight = 21.sp,
                color = Color(0xFF344054)
            )
        }
    }
}


@Composable
private fun EvidenceCard() {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(
                        Color(0xFFE8EDF3),
                        RoundedCornerShape(10.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Icon(
                        imageVector = Icons.Default.PhotoLibrary,
                        contentDescription = null,
                        tint = Color(0xFF52667A)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Evidence Photo",
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF52667A)
                    )

                    Text(
                        text = "GPS tagged • Authentic capture",
                        fontSize = 11.sp,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "1 evidence item captured",
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}


@Composable
private fun ComplianceCard(
    report: ReportItem
) {

    val progress =
        report.compliance
            .replace("%", "")
            .toFloatOrNull()
            ?.div(100f)
            ?: 0.85f

    val status =
        when {
            progress >= 0.90f -> "COMPLIANT"
            progress >= 0.75f -> "NEEDS ATTENTION"
            else -> "CRITICAL"
        }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column {

                    Text(
                        text = "Overall Compliance",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF17324D)
                    )

                    Text(
                        text = status,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = statusColor(status)
                    )
                }

                Text(
                    text = report.compliance,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = statusColor(status)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            LinearProgressIndicator(
                progress = { progress.coerceIn(0f, 1f) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(7.dp),
            )
        }
    }
}


@Composable
private fun ChecklistItem(
    title: String,
    status: String
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(13.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = null,
                tint = if (status == "Compliant")
                    Color(0xFF2E7D32)
                else
                    Color(0xFFF59E0B)
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = title,
                modifier = Modifier.weight(1f),
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium
            )

            Text(
                text = status,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = if (status == "Compliant")
                    Color(0xFF2E7D32)
                else
                    Color(0xFFF59E0B)
            )
        }
    }
}


@Composable
private fun SectionTitle(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFF174A7E)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = title,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF17324D)
        )
    }
}


@Composable
private fun DetailRow(
    label: String,
    value: String
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 7.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = label,
            fontSize = 12.sp,
            color = Color.Gray
        )

        Text(
            text = value,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF26384A)
        )
    }
}


@Composable
private fun InfoValue(
    label: String,
    value: String
) {

    Column {

        Text(
            text = label,
            fontSize = 10.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(3.dp))

        Text(
            text = value,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF17324D)
        )
    }
}


private fun statusColor(status: String): Color {

    return when {

        status.equals("Compliant", ignoreCase = true) ->
            Color(0xFF2E7D32)

        status.equals("Critical", ignoreCase = true) ->
            Color(0xFFD32F2F)

        else ->
            Color(0xFFF59E0B)
    }
}