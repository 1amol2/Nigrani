package com.dev.nigrani.ui.screens.inspections

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.statusBarsPadding

private data class ChecklistItem(
    val title: String,
    val description: String
)

@Composable
fun InspectionChecklistScreen(
    onBackClick: () -> Unit = {},
    onContinueClick: () -> Unit = {}
) {

    val checklistItems = remember {
        listOf(
            ChecklistItem(
                "Beneficiary attendance",
                "Verify physical presence against reported attendance."
            ),
            ChecklistItem(
                "Staff availability",
                "Verify required staff are present during inspection."
            ),
            ChecklistItem(
                "Infrastructure condition",
                "Check classrooms, facilities and general infrastructure."
            ),
            ChecklistItem(
                "Safety and accessibility",
                "Verify safety arrangements and accessibility provisions."
            ),
            ChecklistItem(
                "Records and registers",
                "Check beneficiary and attendance records."
            ),
            ChecklistItem(
                "CCTV surveillance",
                "Verify CCTV cameras are operational and covering required areas."
            )
        )
    }

    val selections = remember {
        mutableStateListOf<String?>().apply {
            repeat(checklistItems.size) {
                add(null)
            }
        }
    }

    val completedCount = selections.count { it != null }

    val progress =
        if (checklistItems.isEmpty()) {
            0f
        } else {
            completedCount.toFloat() / checklistItems.size.toFloat()
        }

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
                text = "Inspection Checklist",
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

            // INSTITUTE INFO

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
                        text = "SURPRISE INSPECTION",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFB45309),
                        letterSpacing = 0.5.sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Sunrise Rehabilitation Centre",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
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

            // PROGRESS

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

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = "INSPECTION PROGRESS",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF52677D)
                        )

                        Text(
                            text = "$completedCount / ${checklistItems.size}",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF174A7E)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    LinearProgressIndicator(
                        progress = { progress },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(7.dp),
                        color = Color(0xFF2563A6),
                        trackColor = Color(0xFFD9E8F7)
                    )

                    Spacer(modifier = Modifier.height(7.dp))

                    Text(
                        text = if (completedCount == checklistItems.size) {
                            "All checklist items completed"
                        } else {
                            "${checklistItems.size - completedCount} items remaining"
                        },
                        fontSize = 11.sp,
                        color = Color(0xFF64748B)
                    )
                }
            }

            // SECTION TITLE

            Text(
                text = "INSPECTION CHECKLIST",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF64748B),
                letterSpacing = 0.5.sp
            )

            // CHECKLIST ITEMS

            checklistItems.forEachIndexed { index, item ->

                ChecklistCard(
                    item = item,
                    selectedStatus = selections[index],
                    onStatusSelected = { status ->
                        selections[index] = status
                    }
                )
            }

            Spacer(modifier = Modifier.height(2.dp))

            // CONTINUE BUTTON

            Button(
                onClick = onContinueClick,
                enabled = completedCount == checklistItems.size,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF174A7E),
                    disabledContainerColor = Color(0xFFCBD5E1),
                    disabledContentColor = Color(0xFF64748B)
                )
            ) {

                Text(
                    text = "CONTINUE TO EVIDENCE",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun ChecklistCard(
    item: ChecklistItem,
    selectedStatus: String?,
    onStatusSelected: (String) -> Unit
) {

    val borderColor = when (selectedStatus) {
        "Compliant" -> Color(0xFFBBF7D0)
        "Needs Attention" -> Color(0xFFFDE68A)
        "Non-Compliant" -> Color(0xFFFECACA)
        else -> Color(0xFFE2E8F0)
    }

    val backgroundColor = when (selectedStatus) {
        "Compliant" -> Color(0xFFF0FDF4)
        "Needs Attention" -> Color(0xFFFFFBEB)
        "Non-Compliant" -> Color(0xFFFEF2F2)
        else -> MaterialTheme.colorScheme.surface
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        border = BorderStroke(
            1.dp,
            borderColor
        )
    ) {

        Column(
            modifier = Modifier.padding(15.dp)
        ) {

            Row(
                verticalAlignment = Alignment.Top
            ) {

                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .background(
                            Color(0xFFEFF6FF),
                            RoundedCornerShape(10.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = "✓",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2563A6)
                    )
                }

                Spacer(modifier = Modifier.size(10.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = item.title,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = item.description,
                        fontSize = 12.sp,
                        color = Color(0xFF64748B),
                        lineHeight = 17.sp
                    )
                }

                Icon(
                    imageVector = Icons.Default.ExpandMore,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = Color(0xFF94A3B8)
                )
            }

            Spacer(modifier = Modifier.height(13.dp))

            StatusButton(
                text = "Compliant",
                selected = selectedStatus == "Compliant",
                icon = Icons.Default.CheckCircle,
                iconColor = Color(0xFF16A34A),
                onClick = {
                    onStatusSelected("Compliant")
                }
            )

            Spacer(modifier = Modifier.height(7.dp))

            StatusButton(
                text = "Needs Attention",
                selected = selectedStatus == "Needs Attention",
                icon = Icons.Default.Warning,
                iconColor = Color(0xFFD97706),
                onClick = {
                    onStatusSelected("Needs Attention")
                }
            )

            Spacer(modifier = Modifier.height(7.dp))

            StatusButton(
                text = "Non-Compliant",
                selected = selectedStatus == "Non-Compliant",
                icon = Icons.Default.Warning,
                iconColor = Color(0xFFDC2626),
                onClick = {
                    onStatusSelected("Non-Compliant")
                }
            )
        }
    }
}

@Composable
private fun StatusButton(
    text: String,
    selected: Boolean,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconColor: Color,
    onClick: () -> Unit
) {

    val backgroundColor =
        if (selected) {
            when (text) {
                "Compliant" -> Color(0xFFDCFCE7)
                "Needs Attention" -> Color(0xFFFEF3C7)
                else -> Color(0xFFFEE2E2)
            }
        } else {
            Color.Transparent
        }

    val borderColor =
        if (selected) {
            iconColor
        } else {
            Color(0xFFE2E8F0)
        }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                backgroundColor,
                RoundedCornerShape(9.dp)
            )
            .clickable {
                onClick()
            }
            .padding(
                horizontal = 12.dp,
                vertical = 10.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(18.dp),
            tint = iconColor
        )

        Spacer(modifier = Modifier.size(9.dp))

        Text(
            text = text,
            modifier = Modifier.weight(1f),
            fontSize = 12.sp,
            fontWeight = if (selected) {
                FontWeight.Bold
            } else {
                FontWeight.Medium
            },
            color = Color(0xFF334155)
        )

        if (selected) {

            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Selected",
                modifier = Modifier.size(18.dp),
                tint = iconColor
            )
        }
    }
}