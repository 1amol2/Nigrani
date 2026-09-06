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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Verified
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
import androidx.compose.foundation.layout.statusBarsPadding

@Composable
fun InspectionReportScreen(
    onBackClick: () -> Unit = {},
    onSubmitClick: () -> Unit = {}
) {

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        // ─────────────────────────────────────
        // TOP BAR
        // ─────────────────────────────────────

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

            Text(
                text = "Inspection Report",
                modifier = Modifier.weight(1f),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            IconButton(
                onClick = {}
            ) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notifications"
                )
            }
        }

        // ─────────────────────────────────────
        // CONTENT
        // ─────────────────────────────────────

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            // REPORT STATUS

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
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

                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .background(
                                Color(0xFFDCEEFF),
                                RoundedCornerShape(12.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {

                        Icon(
                            imageVector = Icons.Default.Description,
                            contentDescription = null,
                            modifier = Modifier.size(25.dp),
                            tint = Color(0xFF2563A6)
                        )
                    }

                    Spacer(modifier = Modifier.size(12.dp))

                    Column {

                        Text(
                            text = "INSPECTION REPORT",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1E5A92)
                        )

                        Spacer(modifier = Modifier.height(3.dp))

                        Text(
                            text = "Ready for submission",
                            fontSize = 12.sp,
                            color = Color(0xFF52677D)
                        )
                    }
                }
            }

            // INSTITUTE

            SectionTitle("INSTITUTE")

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
                        text = "Sunrise Rehabilitation Centre",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
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
                            text = "Varanasi, Uttar Pradesh",
                            fontSize = 12.sp,
                            color = Color(0xFF64748B)
                        )
                    }
                }
            }

            // INSPECTOR

            SectionTitle("INSPECTOR DETAILS")

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
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    ReportRow(
                        label = "Inspector",
                        value = "Rajesh Kumar"
                    )

                    ReportRow(
                        label = "Inspection type",
                        value = "Surprise Inspection"
                    )

                    ReportRow(
                        label = "Inspection date",
                        value = "05 Sep 2026"
                    )

                    ReportRow(
                        label = "Inspection time",
                        value = "14:32"
                    )
                }
            }

            // LOCATION

            SectionTitle("LOCATION VERIFICATION")

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

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector = Icons.Default.Verified,
                            contentDescription = null,
                            modifier = Modifier.size(22.dp),
                            tint = Color(0xFF16A34A)
                        )

                        Spacer(modifier = Modifier.size(9.dp))

                        Text(
                            text = "LOCATION VERIFIED",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF166534)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    ReportRow(
                        label = "Distance",
                        value = "18 metres"
                    )

                    Spacer(modifier = Modifier.height(9.dp))

                    ReportRow(
                        label = "GPS accuracy",
                        value = "± 8 metres"
                    )
                }
            }

            // CHECKLIST SUMMARY

            SectionTitle("CHECKLIST SUMMARY")

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
                    verticalArrangement = Arrangement.spacedBy(11.dp)
                ) {

                    SummaryRow(
                        label = "Beneficiary attendance",
                        status = "Compliant"
                    )

                    SummaryRow(
                        label = "Staff availability",
                        status = "Compliant"
                    )

                    SummaryRow(
                        label = "Infrastructure condition",
                        status = "Needs Attention"
                    )

                    SummaryRow(
                        label = "Safety and accessibility",
                        status = "Compliant"
                    )

                    SummaryRow(
                        label = "Records and registers",
                        status = "Compliant"
                    )

                    SummaryRow(
                        label = "CCTV surveillance",
                        status = "Compliant"
                    )
                }
            }

            // FINDINGS

            SectionTitle("KEY FINDINGS")

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFFFFBEB)
                ),
                border = BorderStroke(
                    1.dp,
                    Color(0xFFFDE68A)
                )
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(
                        text = "Infrastructure requires attention",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF92400E)
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Minor infrastructure deficiencies were observed during the inspection and should be addressed by the institute.",
                        fontSize = 12.sp,
                        color = Color(0xFF78350F),
                        lineHeight = 18.sp
                    )
                }
            }

            // EVIDENCE

            SectionTitle("EVIDENCE")

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

                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .background(
                                Color(0xFFEFF6FF),
                                RoundedCornerShape(11.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {

                        Icon(
                            imageVector = Icons.Default.PhotoCamera,
                            contentDescription = null,
                            modifier = Modifier.size(22.dp),
                            tint = Color(0xFF2563A6)
                        )
                    }

                    Spacer(modifier = Modifier.size(11.dp))

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {

                        Text(
                            text = "1 evidence item",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(3.dp))

                        Text(
                            text = "GPS verified • Captured during inspection",
                            fontSize = 11.sp,
                            color = Color(0xFF64748B)
                        )
                    }

                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        modifier = Modifier.size(21.dp),
                        tint = Color(0xFF16A34A)
                    )
                }
            }

            // OVERALL STATUS

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFFFF7ED)
                ),
                border = BorderStroke(
                    1.dp,
                    Color(0xFFFCD34D)
                )
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(
                        text = "OVERALL INSPECTION STATUS",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF92400E),
                        letterSpacing = 0.5.sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Needs Attention",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFB45309)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "One checklist item requires corrective action.",
                        fontSize = 12.sp,
                        color = Color(0xFF78350F)
                    )
                }
            }

            Spacer(modifier = Modifier.height(2.dp))

            // SUBMIT BUTTON

            Button(
                onClick = onSubmitClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF174A7E)
                )
            ) {

                Text(
                    text = "SUBMIT INSPECTION REPORT",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

// ─────────────────────────────────────────────
// SECTION TITLE
// ─────────────────────────────────────────────

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

// ─────────────────────────────────────────────
// REPORT ROW
// ─────────────────────────────────────────────

@Composable
private fun ReportRow(
    label: String,
    value: String
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = label,
            fontSize = 12.sp,
            color = Color(0xFF64748B)
        )

        Text(
            text = value,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF1E293B)
        )
    }
}

// ─────────────────────────────────────────────
// SUMMARY ROW
// ─────────────────────────────────────────────

@Composable
private fun SummaryRow(
    label: String,
    status: String
) {

    val isCompliant = status == "Compliant"

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = if (isCompliant) {
                Icons.Default.CheckCircle
            } else {
                Icons.Default.Description
            },
            contentDescription = null,
            modifier = Modifier.size(18.dp),
            tint = if (isCompliant) {
                Color(0xFF16A34A)
            } else {
                Color(0xFFD97706)
            }
        )

        Spacer(modifier = Modifier.size(9.dp))

        Text(
            text = label,
            modifier = Modifier.weight(1f),
            fontSize = 12.sp,
            color = Color(0xFF334155)
        )

        Text(
            text = status,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = if (isCompliant) {
                Color(0xFF16A34A)
            } else {
                Color(0xFFD97706)
            }
        )
    }
}