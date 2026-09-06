package com.dev.nigrani.ui.screens.inspections

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts

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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Refresh

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.core.content.ContextCompat

import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource


@Composable
fun LocationVerificationScreen(
    inspection: InspectionItem? = null,
    onBackClick: () -> Unit = {},
    onContinueClick: () -> Unit = {}
) {

    // ========================================================================
    // CURRENT INSPECTION
    // ========================================================================

    val currentInspection = inspection ?: InspectionItem(
        id = "INS-2026-0905-014",
        institute = "Sunrise Rehabilitation Centre",
        location = "Varanasi, Uttar Pradesh",
        type = "Surprise Inspection",
        inspector = "Rajesh Kumar",
        date = "05 Sep 2026 • 18:00",
        priority = "Critical",
        status = "Assigned",
        reason = "Attendance anomaly detected",

        // Temporary demo coordinates
        latitude = 25.3176,
        longitude = 82.9739,
        allowedRadiusMeters = 100f
    )


    // ========================================================================
    // CONTEXT
    // ========================================================================

    val context = androidx.compose.ui.platform.LocalContext.current


    // ========================================================================
    // STATE
    // ========================================================================

    var isVerifying by remember {
        mutableStateOf(false)
    }

    var locationVerified by remember {
        mutableStateOf(false)
    }

    var errorMessage by remember {
        mutableStateOf<String?>(null)
    }

    var currentLatitude by remember {
        mutableStateOf<Double?>(null)
    }

    var currentLongitude by remember {
        mutableStateOf<Double?>(null)
    }

    var gpsAccuracy by remember {
        mutableStateOf<Float?>(null)
    }

    var distanceFromInstitute by remember {
        mutableStateOf<Float?>(null)
    }


    // ========================================================================
    // PERMISSION REQUEST
    // ========================================================================

    val locationPermissionLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->

            val fineLocationGranted =
                permissions[
                    Manifest.permission.ACCESS_FINE_LOCATION
                ] == true

            if (fineLocationGranted) {

                startLocationVerification(
                    context = context,
                    targetLatitude = currentInspection.latitude,
                    targetLongitude = currentInspection.longitude,
                    allowedRadiusMeters =
                        currentInspection.allowedRadiusMeters,

                    onStarted = {
                        isVerifying = true
                        errorMessage = null
                        locationVerified = false
                    },

                    onLocationReceived = { location, distance ->

                        isVerifying = false

                        currentLatitude =
                            location.latitude

                        currentLongitude =
                            location.longitude

                        gpsAccuracy =
                            location.accuracy

                        distanceFromInstitute =
                            distance

                        val accurateEnough =
                            location.hasAccuracy() &&
                                    location.accuracy <= 50f

                        val insideRadius =
                            distance <=
                                    currentInspection.allowedRadiusMeters

                        when {

                            !accurateEnough -> {

                                locationVerified = false

                                errorMessage =
                                    "GPS accuracy is too low. " +
                                            "Move to an open area and try again."
                            }

                            !insideRadius -> {

                                locationVerified = false

                                errorMessage =
                                    "You are ${formatDistance(distance)} " +
                                            "away from the institute. " +
                                            "The permitted radius is " +
                                            "${currentInspection.allowedRadiusMeters.toInt()} metres."
                            }

                            else -> {

                                locationVerified = true
                                errorMessage = null
                            }
                        }
                    },

                    onError = { message ->

                        isVerifying = false
                        locationVerified = false
                        errorMessage = message
                    }
                )

            } else {

                isVerifying = false
                locationVerified = false

                errorMessage =
                    "Precise location permission is required. " +
                            "Please allow precise location for Nigrani."
            }
        }


    // ========================================================================
    // SCROLL
    // ========================================================================

    val scrollState = rememberScrollState()


    // ========================================================================
    // SCREEN
    // ========================================================================

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.background
            )
    ) {

        // ====================================================================
        // TOP BAR
        // ====================================================================

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(
                    horizontal = 8.dp,
                    vertical = 8.dp
                ),

            verticalAlignment =
                Alignment.CenterVertically
        ) {

            IconButton(
                onClick = onBackClick
            ) {

                Icon(
                    imageVector =
                        Icons.Default.ArrowBack,

                    contentDescription =
                        "Back",

                    tint =
                        MaterialTheme.colorScheme.onBackground
                )
            }


            Text(
                text =
                    "Location Verification",

                modifier =
                    Modifier.weight(1f),

                fontSize =
                    20.sp,

                fontWeight =
                    FontWeight.Bold,

                color =
                    MaterialTheme.colorScheme.onBackground
            )


            IconButton(
                onClick = {}
            ) {

                Icon(
                    imageVector =
                        Icons.Default.Notifications,

                    contentDescription =
                        "Notifications",

                    tint =
                        MaterialTheme.colorScheme.onBackground
                )
            }
        }


        // ====================================================================
        // CONTENT
        // ====================================================================

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp),

            verticalArrangement =
                Arrangement.spacedBy(14.dp)
        ) {

            Spacer(
                modifier =
                    Modifier.height(2.dp)
            )


            // =================================================================
            // INTRO CARD
            // =================================================================

            Card(
                modifier =
                    Modifier.fillMaxWidth(),

                shape =
                    RoundedCornerShape(16.dp),

                colors =
                    CardDefaults.cardColors(
                        containerColor =
                            Color(0xFFEFF6FF)
                    ),

                border =
                    BorderStroke(
                        1.dp,
                        Color(0xFFBFDBFE)
                    )
            ) {

                Row(
                    modifier =
                        Modifier.padding(16.dp),

                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    Box(
                        modifier =
                            Modifier
                                .size(44.dp)
                                .background(
                                    Color(0xFFDCEEFF),
                                    RoundedCornerShape(12.dp)
                                ),

                        contentAlignment =
                            Alignment.Center
                    ) {

                        Icon(
                            imageVector =
                                Icons.Default.LocationOn,

                            contentDescription =
                                null,

                            tint =
                                Color(0xFF2563A6),

                            modifier =
                                Modifier.size(25.dp)
                        )
                    }


                    Spacer(
                        modifier =
                            Modifier.size(12.dp)
                    )


                    Column {

                        Text(
                            text =
                                "VERIFY INSPECTION LOCATION",

                            fontSize =
                                12.sp,

                            fontWeight =
                                FontWeight.Bold,

                            color =
                                Color(0xFF1E5A92)
                        )


                        Spacer(
                            modifier =
                                Modifier.height(4.dp)
                        )


                        Text(
                            text =
                                "Your device GPS will be used to confirm " +
                                        "that you are physically present at the institute.",

                            fontSize =
                                12.sp,

                            color =
                                Color(0xFF52677D),

                            lineHeight =
                                17.sp
                        )
                    }
                }
            }


            // =================================================================
            // INSPECTION LOCATION
            // =================================================================

            SectionTitle(
                text =
                    "INSPECTION LOCATION"
            )


            Card(
                modifier =
                    Modifier.fillMaxWidth(),

                shape =
                    RoundedCornerShape(14.dp),

                colors =
                    CardDefaults.cardColors(
                        containerColor =
                            MaterialTheme.colorScheme.surface
                    ),

                border =
                    BorderStroke(
                        1.dp,
                        Color(0xFFE2E8F0)
                    )
            ) {

                Column(
                    modifier =
                        Modifier.padding(16.dp)
                ) {

                    Text(
                        text =
                            currentInspection.institute,

                        fontSize =
                            17.sp,

                        fontWeight =
                            FontWeight.Bold,

                        color =
                            MaterialTheme.colorScheme.onSurface
                    )


                    Spacer(
                        modifier =
                            Modifier.height(6.dp)
                    )


                    Row(
                        verticalAlignment =
                            Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector =
                                Icons.Default.LocationOn,

                            contentDescription =
                                null,

                            modifier =
                                Modifier.size(17.dp),

                            tint =
                                Color(0xFF2563A6)
                        )


                        Spacer(
                            modifier =
                                Modifier.size(5.dp)
                        )


                        Text(
                            text =
                                currentInspection.location,

                            fontSize =
                                13.sp,

                            color =
                                Color(0xFF64748B)
                        )
                    }


                    Spacer(
                        modifier =
                            Modifier.height(10.dp)
                    )


                    InfoRow(
                        label =
                            "Inspection ID",

                        value =
                            currentInspection.id
                    )


                    Spacer(
                        modifier =
                            Modifier.height(8.dp)
                    )


                    InfoRow(
                        label =
                            "Permitted radius",

                        value =
                            "${currentInspection.allowedRadiusMeters.toInt()} metres"
                    )
                }
            }


            // =================================================================
            // LIVE GPS
            // =================================================================

            SectionTitle(
                text =
                    "LIVE GPS LOCATION"
            )


            Card(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(190.dp),

                shape =
                    RoundedCornerShape(16.dp),

                colors =
                    CardDefaults.cardColors(
                        containerColor =
                            Color(0xFFE8EEF3)
                    )
            ) {

                Box(
                    modifier =
                        Modifier.fillMaxSize(),

                    contentAlignment =
                        Alignment.Center
                ) {

                    Column(
                        horizontalAlignment =
                            Alignment.CenterHorizontally
                    ) {

                        Box(
                            modifier =
                                Modifier
                                    .size(58.dp)
                                    .background(
                                        Color.White,
                                        RoundedCornerShape(50.dp)
                                    ),

                            contentAlignment =
                                Alignment.Center
                        ) {

                            Icon(
                                imageVector =
                                    Icons.Default.MyLocation,

                                contentDescription =
                                    null,

                                modifier =
                                    Modifier.size(30.dp),

                                tint =
                                    when {

                                        locationVerified ->
                                            Color(0xFF16A34A)

                                        isVerifying ->
                                            Color(0xFF2563A6)

                                        else ->
                                            Color(0xFF174A7E)
                                    }
                            )
                        }


                        Spacer(
                            modifier =
                                Modifier.height(10.dp)
                        )


                        Text(
                            text =
                                when {

                                    isVerifying ->
                                        "Acquiring GPS location..."

                                    locationVerified ->
                                        "Location verified"

                                    errorMessage != null ->
                                        "GPS verification failed"

                                    else ->
                                        "Current location"
                                },

                            fontSize =
                                13.sp,

                            fontWeight =
                                FontWeight.SemiBold,

                            color =
                                Color(0xFF334155)
                        )


                        Spacer(
                            modifier =
                                Modifier.height(3.dp)
                        )


                        Text(
                            text =
                                when {

                                    isVerifying ->
                                        "Please keep the phone still"

                                    locationVerified ->
                                        "You are inside the permitted area"

                                    errorMessage != null ->
                                        "Check the error below"

                                    else ->
                                        "Press Verify Location to begin"
                                },

                            fontSize =
                                11.sp,

                            color =
                                Color(0xFF64748B)
                        )
                    }
                }
            }


            // =================================================================
            // GPS DETAILS
            // =================================================================

            SectionTitle(
                text =
                    "GPS DETAILS"
            )


            Card(
                modifier =
                    Modifier.fillMaxWidth(),

                shape =
                    RoundedCornerShape(14.dp),

                colors =
                    CardDefaults.cardColors(
                        containerColor =
                            MaterialTheme.colorScheme.surface
                    ),

                border =
                    BorderStroke(
                        1.dp,
                        Color(0xFFE2E8F0)
                    )
            ) {

                Column(
                    modifier =
                        Modifier.padding(16.dp),

                    verticalArrangement =
                        Arrangement.spacedBy(14.dp)
                ) {

                    InfoRow(
                        label =
                            "Distance from institute",

                        value =
                            distanceFromInstitute?.let {
                                formatDistance(it)
                            } ?: "Not captured",

                        valueColor =
                            if (locationVerified)
                                Color(0xFF16A34A)
                            else
                                Color(0xFF1E293B)
                    )


                    InfoRow(
                        label =
                            "GPS accuracy",

                        value =
                            gpsAccuracy?.let {
                                "${it.toInt()} metres"
                            } ?: "Not captured"
                    )


                    InfoRow(
                        label =
                            "Latitude",

                        value =
                            currentLatitude?.let {
                                String.format(
                                    "%.6f",
                                    it
                                )
                            } ?: "Not captured"
                    )


                    InfoRow(
                        label =
                            "Longitude",

                        value =
                            currentLongitude?.let {
                                String.format(
                                    "%.6f",
                                    it
                                )
                            } ?: "Not captured"
                    )


                    InfoRow(
                        label =
                            "Location status",

                        value =
                            when {

                                locationVerified ->
                                    "VERIFIED"

                                isVerifying ->
                                    "VERIFYING"

                                else ->
                                    "NOT VERIFIED"
                            },

                        valueColor =
                            when {

                                locationVerified ->
                                    Color(0xFF16A34A)

                                isVerifying ->
                                    Color(0xFF2563A6)

                                else ->
                                    Color(0xFFD97706)
                            }
                    )
                }
            }


            // =================================================================
            // ERROR
            // =================================================================

            if (errorMessage != null) {

                Card(
                    modifier =
                        Modifier.fillMaxWidth(),

                    shape =
                        RoundedCornerShape(14.dp),

                    colors =
                        CardDefaults.cardColors(
                            containerColor =
                                Color(0xFFFFF7ED)
                        ),

                    border =
                        BorderStroke(
                            1.dp,
                            Color(0xFFFED7AA)
                        )
                ) {

                    Row(
                        modifier =
                            Modifier.padding(14.dp),

                        verticalAlignment =
                            Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector =
                                Icons.Default.Error,

                            contentDescription =
                                null,

                            modifier =
                                Modifier.size(24.dp),

                            tint =
                                Color(0xFFD97706)
                        )


                        Spacer(
                            modifier =
                                Modifier.size(10.dp)
                        )


                        Column {

                            Text(
                                text =
                                    "VERIFICATION FAILED",

                                fontSize =
                                    11.sp,

                                fontWeight =
                                    FontWeight.Bold,

                                color =
                                    Color(0xFF9A3412)
                            )


                            Spacer(
                                modifier =
                                    Modifier.height(3.dp)
                            )


                            Text(
                                text =
                                    errorMessage!!,

                                fontSize =
                                    12.sp,

                                color =
                                    Color(0xFF7C4A1D),

                                lineHeight =
                                    17.sp
                            )
                        }
                    }
                }
            }


            // =================================================================
            // SUCCESS
            // =================================================================

            if (locationVerified) {

                Card(
                    modifier =
                        Modifier.fillMaxWidth(),

                    shape =
                        RoundedCornerShape(14.dp),

                    colors =
                        CardDefaults.cardColors(
                            containerColor =
                                Color(0xFFF0FDF4)
                        ),

                    border =
                        BorderStroke(
                            1.dp,
                            Color(0xFFBBF7D0)
                        )
                ) {

                    Row(
                        modifier =
                            Modifier.padding(16.dp),

                        verticalAlignment =
                            Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector =
                                Icons.Default.CheckCircle,

                            contentDescription =
                                null,

                            modifier =
                                Modifier.size(25.dp),

                            tint =
                                Color(0xFF16A34A)
                        )


                        Spacer(
                            modifier =
                                Modifier.size(10.dp)
                        )


                        Column {

                            Text(
                                text =
                                    "LOCATION VERIFIED",

                                fontSize =
                                    13.sp,

                                fontWeight =
                                    FontWeight.Bold,

                                color =
                                    Color(0xFF166534)
                            )


                            Spacer(
                                modifier =
                                    Modifier.height(3.dp)
                            )


                            Text(
                                text =
                                    "Your device is within the permitted " +
                                            "inspection radius.",

                                fontSize =
                                    12.sp,

                                color =
                                    Color(0xFF4D7C5B)
                            )
                        }
                    }
                }
            }


            // =================================================================
            // BUTTONS
            // =================================================================

            if (!locationVerified) {

                Button(
                    onClick = {

                        // -----------------------------------------------------
                        // CHECK WHETHER PRECISE LOCATION IS ALREADY GRANTED
                        // -----------------------------------------------------

                        val fineGranted =
                            ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.ACCESS_FINE_LOCATION
                            ) == PackageManager.PERMISSION_GRANTED


                        if (fineGranted) {

                            // Permission already granted.
                            // Directly start GPS verification.

                            startLocationVerification(
                                context = context,

                                targetLatitude =
                                    currentInspection.latitude,

                                targetLongitude =
                                    currentInspection.longitude,

                                allowedRadiusMeters =
                                    currentInspection
                                        .allowedRadiusMeters,

                                onStarted = {

                                    isVerifying = true
                                    errorMessage = null
                                    locationVerified = false
                                },

                                onLocationReceived = {
                                        location,
                                        distance ->

                                    isVerifying = false

                                    currentLatitude =
                                        location.latitude

                                    currentLongitude =
                                        location.longitude

                                    gpsAccuracy =
                                        location.accuracy

                                    distanceFromInstitute =
                                        distance

                                    val accurateEnough =
                                        location.hasAccuracy() &&
                                                location.accuracy <= 50f

                                    val insideRadius =
                                        distance <=
                                                currentInspection
                                                    .allowedRadiusMeters

                                    when {

                                        !accurateEnough -> {

                                            locationVerified = false

                                            errorMessage =
                                                "GPS accuracy is too low. " +
                                                        "Move to an open area " +
                                                        "and try again."
                                        }

                                        !insideRadius -> {

                                            locationVerified = false

                                            errorMessage =
                                                "You are ${formatDistance(distance)} " +
                                                        "away from the institute. " +
                                                        "The permitted radius is " +
                                                        "${currentInspection.allowedRadiusMeters.toInt()} metres."
                                        }

                                        else -> {

                                            locationVerified = true
                                            errorMessage = null
                                        }
                                    }
                                },

                                onError = { message ->

                                    isVerifying = false
                                    locationVerified = false
                                    errorMessage = message
                                }
                            )

                        } else {

                            // -------------------------------------------------
                            // REQUEST REAL ANDROID LOCATION PERMISSION
                            // -------------------------------------------------

                            locationPermissionLauncher.launch(
                                arrayOf(
                                    Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.ACCESS_COARSE_LOCATION
                                )
                            )
                        }
                    },

                    enabled =
                        !isVerifying,

                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(54.dp),

                    shape =
                        RoundedCornerShape(12.dp),

                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor =
                                Color(0xFF174A7E)
                        )
                ) {

                    Icon(
                        imageVector =
                            Icons.Default.MyLocation,

                        contentDescription =
                            null,

                        modifier =
                            Modifier.size(19.dp)
                    )


                    Spacer(
                        modifier =
                            Modifier.size(8.dp)
                    )


                    Text(
                        text =
                            if (isVerifying)
                                "VERIFYING LOCATION..."
                            else
                                "VERIFY LOCATION",

                        fontSize =
                            14.sp,

                        fontWeight =
                            FontWeight.Bold
                    )
                }

            } else {

                // =============================================================
                // CONTINUE
                // =============================================================

                Button(
                    onClick =
                        onContinueClick,

                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(54.dp),

                    shape =
                        RoundedCornerShape(12.dp),

                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor =
                                Color(0xFF174A7E)
                        )
                ) {

                    Text(
                        text =
                            "CONTINUE TO INSPECTION",

                        fontSize =
                            14.sp,

                        fontWeight =
                            FontWeight.Bold
                    )
                }


                // =============================================================
                // VERIFY AGAIN
                // =============================================================

                Button(
                    onClick = {

                        locationVerified = false
                        errorMessage = null
                        currentLatitude = null
                        currentLongitude = null
                        gpsAccuracy = null
                        distanceFromInstitute = null
                    },

                    modifier =
                        Modifier.fillMaxWidth(),

                    shape =
                        RoundedCornerShape(12.dp),

                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor =
                                Color.Transparent,

                            contentColor =
                                Color(0xFF174A7E)
                        )
                ) {

                    Icon(
                        imageVector =
                            Icons.Default.Refresh,

                        contentDescription =
                            null,

                        modifier =
                            Modifier.size(18.dp)
                    )


                    Spacer(
                        modifier =
                            Modifier.size(7.dp)
                    )


                    Text(
                        text =
                            "VERIFY AGAIN",

                        fontSize =
                            13.sp,

                        fontWeight =
                            FontWeight.SemiBold
                    )
                }
            }


            Spacer(
                modifier =
                    Modifier.height(16.dp)
            )
        }
    }
}


// ============================================================================
// START GPS VERIFICATION
// ============================================================================

private fun startLocationVerification(
    context: Context,

    targetLatitude: Double,

    targetLongitude: Double,

    allowedRadiusMeters: Float,

    onStarted: () -> Unit,

    onLocationReceived: (
        location: Location,
        distanceMeters: Float
    ) -> Unit,

    onError: (String) -> Unit
) {

    // ========================================================================
    // PERMISSION CHECK
    // ========================================================================

    val fineGranted =
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED


    if (!fineGranted) {

        onError(
            "Precise location permission is required."
        )

        return
    }


    // ========================================================================
    // LOCATION SERVICES CHECK
    // ========================================================================

    val locationManager =
        context.getSystemService(
            Context.LOCATION_SERVICE
        ) as LocationManager

    val locationEnabled =
        androidx.core.location.LocationManagerCompat
            .isLocationEnabled(locationManager)

    if (!locationEnabled) {

        onError(
            "Location services are turned off. " +
                    "Please turn on GPS/Location on your phone."
        )

        return
    }

    // ========================================================================
    // START
    // ========================================================================

    onStarted()


    // ========================================================================
    // FUSED LOCATION PROVIDER
    // ========================================================================

    val fusedLocationClient =
        LocationServices
            .getFusedLocationProviderClient(context)


    // ========================================================================
    // HIGH ACCURACY REQUEST
    // ========================================================================

    val request =
        CurrentLocationRequest.Builder()
            .setPriority(
                Priority.PRIORITY_HIGH_ACCURACY
            )
            .setMaxUpdateAgeMillis(
                5_000
            )
            .setDurationMillis(
                15_000
            )
            .build()


    // ========================================================================
    // REQUEST CURRENT LOCATION
    // ========================================================================

    try {

        val cancellationTokenSource =
            CancellationTokenSource()


        fusedLocationClient
            .getCurrentLocation(
                request,
                cancellationTokenSource.token
            )

            .addOnSuccessListener { location ->

                if (location == null) {

                    onError(
                        "Unable to obtain your current GPS location. " +
                                "Move to an open area and try again."
                    )

                    return@addOnSuccessListener
                }


                // ============================================================
                // CALCULATE DISTANCE
                // ============================================================

                val distanceResult =
                    FloatArray(1)


                Location.distanceBetween(
                    location.latitude,
                    location.longitude,

                    targetLatitude,
                    targetLongitude,

                    distanceResult
                )


                val distance =
                    distanceResult[0]


                // ============================================================
                // RETURN RESULT
                // ============================================================

                onLocationReceived(
                    location,
                    distance
                )
            }

            .addOnFailureListener { exception ->

                onError(
                    exception.message
                        ?: "Unable to obtain current GPS location."
                )
            }

    } catch (
        exception: SecurityException
    ) {

        onError(
            "Location permission was not granted. " +
                    "Please allow precise location access."
        )

    } catch (
        exception: Exception
    ) {

        onError(
            exception.message
                ?: "Unable to access device GPS."
        )
    }
}


// ============================================================================
// SECTION TITLE
// ============================================================================

@Composable
private fun SectionTitle(
    text: String
) {

    Text(
        text =
            text,

        fontSize =
            11.sp,

        fontWeight =
            FontWeight.Bold,

        color =
            Color(0xFF64748B),

        letterSpacing =
            0.5.sp
    )
}


// ============================================================================
// INFO ROW
// ============================================================================

@Composable
private fun InfoRow(
    label: String,
    value: String,
    valueColor: Color = Color(0xFF1E293B)
) {

    Row(
        modifier =
            Modifier.fillMaxWidth(),

        verticalAlignment =
            Alignment.CenterVertically
    ) {

        Text(
            text =
                label,

            fontSize =
                11.sp,

            color =
                Color(0xFF64748B),

            modifier =
                Modifier.weight(1f)
        )


        Text(
            text =
                value,

            fontSize =
                12.sp,

            fontWeight =
                FontWeight.SemiBold,

            color =
                valueColor
        )
    }
}


// ============================================================================
// DISTANCE FORMATTER
// ============================================================================

private fun formatDistance(
    distanceMeters: Float
): String {

    return if (distanceMeters < 1000f) {

        "${distanceMeters.toInt()} metres"

    } else {

        String.format(
            "%.2f km",
            distanceMeters / 1000f
        )
    }
}