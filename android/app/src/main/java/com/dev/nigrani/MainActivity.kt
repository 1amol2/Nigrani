package com.dev.nigrani

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import com.dev.nigrani.ui.screens.LoginScreen

import com.dev.nigrani.ui.screens.alerts.AlertsScreen
import com.dev.nigrani.ui.screens.dashboard.DashboardScreen
import com.dev.nigrani.ui.screens.institutes.InstitutesScreen
import com.dev.nigrani.ui.screens.institutedetails.InstituteDetailsScreen

import com.dev.nigrani.ui.screens.inspections.EvidenceCaptureScreen
import com.dev.nigrani.ui.screens.inspections.InspectionAssignmentScreen
import com.dev.nigrani.ui.screens.inspections.InspectionChecklistScreen
import com.dev.nigrani.ui.screens.inspections.InspectionItem
import com.dev.nigrani.ui.screens.inspections.InspectionReportScreen
import com.dev.nigrani.ui.screens.inspections.InspectionSubmittedScreen
import com.dev.nigrani.ui.screens.inspections.InspectionsScreen
import com.dev.nigrani.ui.screens.inspections.LocationVerificationScreen

import com.dev.nigrani.ui.screens.reports.ReportDetailsScreen
import com.dev.nigrani.ui.screens.reports.ReportItem
import com.dev.nigrani.ui.screens.reports.ReportsScreen

import com.dev.nigrani.ui.screens.surveillance.LiveSurveillanceScreen
import com.dev.nigrani.ui.theme.NigraniTheme


enum class AppScreen {
    LOGIN,
    DASHBOARD,

    INSTITUTES,
    INSTITUTE_DETAILS,

    INSPECTIONS,
    INSPECTION_ASSIGNMENT,
    LOCATION_VERIFICATION,
    INSPECTION_CHECKLIST,
    EVIDENCE_CAPTURE,
    INSPECTION_REPORT,
    INSPECTION_SUBMITTED,

    SURVEILLANCE,
    ALERTS,

    REPORTS,
    REPORT_DETAILS
}


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {

            NigraniTheme(
                darkTheme = false,
                dynamicColor = false
            ) {

                // ============================================================
                // CURRENT SCREEN
                // ============================================================

                var currentScreen by remember {
                    mutableStateOf(AppScreen.LOGIN)
                }


                // ============================================================
                // SELECTED REPORT
                // ============================================================

                var selectedReport by remember {
                    mutableStateOf<ReportItem?>(null)
                }


                // ============================================================
                // SELECTED INSPECTION
                // ============================================================

                var selectedInspection by remember {
                    mutableStateOf<InspectionItem?>(null)
                }


                // ============================================================
                // ACTIVE INSPECTION SESSION
                // ============================================================

                var inspectionSession by remember {
                    mutableStateOf<InspectionSession?>(null)
                }


                // ============================================================
                // RETURN LOCATIONS
                // ============================================================

                var alertsReturnScreen by remember {
                    mutableStateOf(AppScreen.DASHBOARD)
                }


                var surveillanceReturnScreen by remember {
                    mutableStateOf(AppScreen.INSTITUTE_DETAILS)
                }


                var inspectionReturnScreen by remember {
                    mutableStateOf(AppScreen.DASHBOARD)
                }


                // ============================================================
                // SYSTEM BACK BUTTON
                // ============================================================

                BackHandler(
                    enabled = currentScreen != AppScreen.DASHBOARD
                ) {

                    currentScreen = when (currentScreen) {

                        AppScreen.LOGIN ->
                            AppScreen.LOGIN

                        AppScreen.DASHBOARD ->
                            AppScreen.DASHBOARD

                        AppScreen.INSTITUTES ->
                            AppScreen.DASHBOARD

                        AppScreen.INSTITUTE_DETAILS ->
                            AppScreen.INSTITUTES

                        AppScreen.INSPECTIONS ->
                            AppScreen.DASHBOARD

                        AppScreen.INSPECTION_ASSIGNMENT ->
                            inspectionReturnScreen

                        AppScreen.LOCATION_VERIFICATION ->
                            AppScreen.INSPECTION_ASSIGNMENT

                        AppScreen.INSPECTION_CHECKLIST ->
                            AppScreen.LOCATION_VERIFICATION

                        AppScreen.EVIDENCE_CAPTURE ->
                            AppScreen.INSPECTION_CHECKLIST

                        AppScreen.INSPECTION_REPORT ->
                            AppScreen.EVIDENCE_CAPTURE

                        AppScreen.INSPECTION_SUBMITTED ->
                            AppScreen.DASHBOARD

                        AppScreen.SURVEILLANCE ->
                            surveillanceReturnScreen

                        AppScreen.ALERTS ->
                            alertsReturnScreen

                        AppScreen.REPORTS ->
                            AppScreen.DASHBOARD

                        AppScreen.REPORT_DETAILS ->
                            AppScreen.REPORTS
                    }
                }


                // ============================================================
                // SCREEN ROUTER
                // ============================================================

                when (currentScreen) {


                    // ========================================================
                    // LOGIN
                    // ========================================================

                    AppScreen.LOGIN -> {

                        LoginScreen(
                            onLoginClick = {

                                currentScreen =
                                    AppScreen.DASHBOARD
                            }
                        )
                    }


                    // ========================================================
                    // DASHBOARD
                    // ========================================================

                    AppScreen.DASHBOARD -> {

                        DashboardScreen(

                            onInstitutesClick = {

                                currentScreen =
                                    AppScreen.INSTITUTES
                            },


                            onInspectionClick = {

                                currentScreen =
                                    AppScreen.INSPECTIONS
                            },


                            onAlertsClick = {

                                alertsReturnScreen =
                                    AppScreen.DASHBOARD

                                currentScreen =
                                    AppScreen.ALERTS
                            },


                            onReportsClick = {

                                currentScreen =
                                    AppScreen.REPORTS
                            },


                            onSurveillanceClick = {

                                surveillanceReturnScreen =
                                    AppScreen.DASHBOARD

                                currentScreen =
                                    AppScreen.SURVEILLANCE
                            }
                        )
                    }


                    // ========================================================
                    // INSTITUTES
                    // ========================================================

                    AppScreen.INSTITUTES -> {

                        InstitutesScreen(

                            onInstituteClick = {

                                currentScreen =
                                    AppScreen.INSTITUTE_DETAILS
                            },


                            onDashboardClick = {

                                currentScreen =
                                    AppScreen.DASHBOARD
                            },


                            onInspectionClick = {

                                inspectionReturnScreen =
                                    AppScreen.INSTITUTES

                                currentScreen =
                                    AppScreen.INSPECTIONS
                            },


                            onAlertsClick = {

                                alertsReturnScreen =
                                    AppScreen.INSTITUTES

                                currentScreen =
                                    AppScreen.ALERTS
                            }
                        )
                    }


                    // ========================================================
                    // INSTITUTE DETAILS
                    // ========================================================

                    AppScreen.INSTITUTE_DETAILS -> {

                        InstituteDetailsScreen(

                            onBackClick = {

                                currentScreen =
                                    AppScreen.INSTITUTES
                            },


                            onSurveillanceClick = {

                                surveillanceReturnScreen =
                                    AppScreen.INSTITUTE_DETAILS

                                currentScreen =
                                    AppScreen.SURVEILLANCE
                            },


                            onInspectionClick = {

                                val instituteInspection =
                                    InspectionItem(

                                        id = "INS-2026-0905-014",

                                        institute =
                                            "Sunrise Rehabilitation Centre",

                                        location =
                                            "Varanasi, Uttar Pradesh",

                                        type =
                                            "Surprise Inspection",

                                        inspector =
                                            "Rajesh Kumar",

                                        date =
                                            "05 Sep 2026 • 18:00",

                                        priority =
                                            "Critical",

                                        status =
                                            "Assigned",

                                        reason =
                                            "Attendance anomaly detected",

                                        latitude =
                                            25.3176,

                                        longitude =
                                            82.9739,

                                        allowedRadiusMeters =
                                            100f
                                    )


                                selectedInspection =
                                    instituteInspection


                                inspectionSession =
                                    InspectionSession(
                                        inspection =
                                            instituteInspection
                                    )


                                inspectionReturnScreen =
                                    AppScreen.INSTITUTE_DETAILS


                                currentScreen =
                                    AppScreen.INSPECTION_ASSIGNMENT
                            },


                            onAlertsClick = {

                                alertsReturnScreen =
                                    AppScreen.INSTITUTE_DETAILS

                                currentScreen =
                                    AppScreen.ALERTS
                            }
                        )
                    }


                    // ========================================================
                    // INSPECTIONS HOME
                    // ========================================================

                    AppScreen.INSPECTIONS -> {

                        InspectionsScreen(

                            onBackClick = {

                                currentScreen =
                                    inspectionReturnScreen
                            },


                            onInspectionClick = { inspection ->

                                selectedInspection =
                                    inspection


                                inspectionSession =
                                    InspectionSession(
                                        inspection =
                                            inspection
                                    )


                                inspectionReturnScreen =
                                    AppScreen.INSPECTIONS


                                currentScreen =
                                    AppScreen.INSPECTION_ASSIGNMENT
                            }
                        )
                    }


                    // ========================================================
                    // INSPECTION ASSIGNMENT
                    // ========================================================

                    AppScreen.INSPECTION_ASSIGNMENT -> {

                        InspectionAssignmentScreen(

                            inspection =
                                inspectionSession?.inspection,


                            onBackClick = {

                                currentScreen =
                                    inspectionReturnScreen
                            },


                            onStartInspectionClick = {

                                currentScreen =
                                    AppScreen.LOCATION_VERIFICATION
                            }
                        )
                    }


                    // ========================================================
                    // LOCATION VERIFICATION
                    // ========================================================

                    AppScreen.LOCATION_VERIFICATION -> {

                        LocationVerificationScreen(

                            inspection =
                                inspectionSession?.inspection,


                            onBackClick = {

                                currentScreen =
                                    AppScreen.INSPECTION_ASSIGNMENT
                            },


                            onContinueClick = {

                                currentScreen =
                                    AppScreen.INSPECTION_CHECKLIST
                            }
                        )
                    }


                    // ========================================================
                    // INSPECTION CHECKLIST
                    // ========================================================

                    AppScreen.INSPECTION_CHECKLIST -> {

                        InspectionChecklistScreen(

                            onBackClick = {

                                currentScreen =
                                    AppScreen.LOCATION_VERIFICATION
                            },


                            onContinueClick = {

                                currentScreen =
                                    AppScreen.EVIDENCE_CAPTURE
                            }
                        )
                    }


                    // ========================================================
                    // EVIDENCE CAPTURE
                    // ========================================================

                    AppScreen.EVIDENCE_CAPTURE -> {

                        EvidenceCaptureScreen(

                            onBackClick = {

                                currentScreen =
                                    AppScreen.INSPECTION_CHECKLIST
                            },


                            onSubmitClick = { evidenceUri ->

                                // Real camera URI received here.
                                // Backend upload will be connected later.

                                currentScreen =
                                    AppScreen.INSPECTION_REPORT
                            }
                        )
                    }


                    // ========================================================
                    // INSPECTION REPORT
                    // ========================================================

                    AppScreen.INSPECTION_REPORT -> {

                        InspectionReportScreen(

                            onBackClick = {

                                currentScreen =
                                    AppScreen.EVIDENCE_CAPTURE
                            },


                            onSubmitClick = {

                                currentScreen =
                                    AppScreen.INSPECTION_SUBMITTED
                            }
                        )
                    }


                    // ========================================================
                    // INSPECTION SUBMITTED
                    // ========================================================

                    AppScreen.INSPECTION_SUBMITTED -> {

                        InspectionSubmittedScreen(

                            onDoneClick = {

                                // Clear current inspection session
                                inspectionSession = null
                                selectedInspection = null

                                currentScreen =
                                    AppScreen.DASHBOARD
                            }
                        )
                    }


                    // ========================================================
                    // LIVE SURVEILLANCE
                    // ========================================================

                    AppScreen.SURVEILLANCE -> {

                        LiveSurveillanceScreen(

                            onBackClick = {

                                currentScreen =
                                    surveillanceReturnScreen
                            },


                            onOverviewClick = {

                                currentScreen =
                                    AppScreen.INSTITUTE_DETAILS
                            },


                            onAlertsClick = {

                                alertsReturnScreen =
                                    AppScreen.SURVEILLANCE

                                currentScreen =
                                    AppScreen.ALERTS
                            }
                        )
                    }


                    // ========================================================
                    // ALERTS
                    // ========================================================

                    AppScreen.ALERTS -> {

                        AlertsScreen(

                            onBackClick = {

                                currentScreen =
                                    alertsReturnScreen
                            },


                            onAlertClick = {

                                // Alert Details will be connected later.
                            },


                            onInstitutesClick = {

                                currentScreen =
                                    AppScreen.INSTITUTES
                            },


                            onSurveillanceClick = {

                                surveillanceReturnScreen =
                                    AppScreen.ALERTS

                                currentScreen =
                                    AppScreen.SURVEILLANCE
                            }
                        )
                    }


                    // ========================================================
                    // REPORTS
                    // ========================================================

                    AppScreen.REPORTS -> {

                        ReportsScreen(

                            onBackClick = {

                                currentScreen =
                                    AppScreen.DASHBOARD
                            },


                            onReportClick = { report ->

                                selectedReport =
                                    report

                                currentScreen =
                                    AppScreen.REPORT_DETAILS
                            }
                        )
                    }


                    // ========================================================
                    // REPORT DETAILS
                    // ========================================================

                    AppScreen.REPORT_DETAILS -> {

                        selectedReport?.let { report ->

                            ReportDetailsScreen(

                                report =
                                    report,


                                onBackClick = {

                                    currentScreen =
                                        AppScreen.REPORTS
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}