package com.dev.nigrani

import androidx.compose.runtime.Immutable

@Immutable
data class InspectionSession(
    val inspection: com.dev.nigrani.ui.screens.inspections.InspectionItem,

    val locationVerified: Boolean = false,

    val checklistResults: Map<String, String> = emptyMap()
)