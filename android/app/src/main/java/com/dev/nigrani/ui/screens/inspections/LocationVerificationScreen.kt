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
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.filled.Notifications

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.core.app.ActivityCompat
import androidx.core.location.LocationManagerCompat

import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority


@Composable
fun LocationVerificationScreen(
    inspection: InspectionItem? = null,
    onBackClick: () -> Unit = {},
    onContinueClick: () -> Unit = {}
) {

    val context = LocalContext.current

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
        latitude = 25.3176,
        longitude = 82.9739,
        allowedRadiusMeters = 100f
    )

    val fusedLocationClient: FusedLocationProviderClient =
        remember {
            LocationServices.getFusedLocationProviderClient(context)
        }

    var locationVerified by remember {
        mutableStateOf(false)
    }

    var isLoading by remember {
        mutableStateOf(false)
    }

    var errorMessage by remember {
        mutableStateOf<String?>(null)
    }

    var distanceMeters by remember {
        mutableStateOf<Float?>(null)
    }

    var accuracyMeters by remember {
        mutableStateOf<Float?>(null)
    }

    var currentLatitude by remember {
        mutableStateOf<Double?>(null)
    }

    var currentLongitude by remember {
        mutableStateOf<Double?>(null)
    }

    var isTestLocation by remember {
        mutableStateOf(false)
    }


    // ================================================================
    // REAL GPS VERIFICATION
    // ================================================================

    fun verifyCurrentLocation() {

        errorMessage = null
        isLoading = true
        locationVerified = false
        isTestLocation = false

        val locationManager =
            context.getSystemService(Context.LOCATION_SERVICE)
                    as LocationManager

        if (!LocationManagerCompat.isLocationEnabled(locationManager)) {

            isLoading = false

            errorMessage =
                "Location services are turned off. Please enable GPS and try again."

            return
        }

        val hasFineLocation =
            ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

        val hasCoarseLocation =
            ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

        if (!hasFineLocation && !hasCoarseLocation) {

            isLoading = false

            errorMessage =
                "Location permission is required to verify the inspection location."

            return
        }

        val request =
            CurrentLocationRequest.Builder()
                .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
                .setMaxUpdateAgeMillis(5000)
                .setDurationMillis(15000)
                .build()

        try {

            fusedLocationClient
                .getCurrentLocation(
                    request,
                    null
                )
                .addOnSuccessListener { location ->

                    isLoading = false

                    if (location == null) {

                        errorMessage =
                            "Unable to get your current location. Please try again."

                        return@addOnSuccessListener
                    }

                    val accuracy =
                        location.accuracy

                    currentLatitude =
                        location.latitude

                    currentLongitude =
                        location.longitude

                    accuracyMeters =
                        accuracy

                    if (accuracy <= 0f || accuracy > 50f) {

                        errorMessage =
                            "GPS accuracy is too low. Current accuracy: ${
                                if (accuracy > 0) {
                                    "${accuracy.toInt()} m"
                                } else {
                                    "Unavailable"
                                }
                            }"

                        return@addOnSuccessListener
                    }

                    val result =
                        FloatArray(1)

                    Location.distanceBetween(
                        location.latitude,
                        location.longitude,
                        currentInspection.latitude,
                        currentInspection.longitude,
                        result
                    )

                    val distance =
                        result[0]

                    distanceMeters =
                        distance

                    if (
                        distance <=
                        currentInspection.allowedRadiusMeters
                    ) {

                        locationVerified =
                            true

                        errorMessage =
                            null

                    } else {

                        locationVerified =
                            false

                        errorMessage =
                            "You are outside the permitted inspection radius."
                    }
                }
                .addOnFailureListener { exception ->

                    isLoading = false

                    errorMessage =
                        exception.message
                            ?: "Unable to obtain your current location."
                }

        } catch (e: SecurityException) {

            isLoading = false

            errorMessage =
                "Location permission is required."
        }
    }


    // ================================================================
    // TEST LOCATION
    // ================================================================

    fun useTestLocation() {

        isLoading = false
        errorMessage = null

        isTestLocation = true
        locationVerified = true

        // Simulate being exactly at the institute.
        currentLatitude =
            currentInspection.latitude

        currentLongitude =
            currentInspection.longitude

        distanceMeters =
            0f

        accuracyMeters =
            5f
    }


    // ================================================================
    // PERMISSION REQUEST
    // ================================================================

    val permissionLauncher =
        rememberLauncherForActivityResult(
            contract =
                ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->

            val fineGranted =
                permissions[
                    Manifest.permission.ACCESS_FINE_LOCATION
                ] == true

            val coarseGranted =
                permissions[
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ] == true

            if (fineGranted || coarseGranted) {

                verifyCurrentLocation()

            } else {

                errorMessage =
                    "Location permission was denied. Please allow location access."
            }
        }


    fun startVerification() {

        val fineGranted =
            ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

        val coarseGranted =
            ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

        if (fineGranted || coarseGranted) {

            verifyCurrentLocation()

        } else {

            permissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }


    val scrollState =
        rememberScrollState()


    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(
                    MaterialTheme.colorScheme.background
                )
    ) {

        // ============================================================
        // TOP BAR
        // ============================================================

        Row(
            modifier =
                Modifier
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
                        "Back"
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
                    FontWeight.Bold
            )


            IconButton(
                onClick = {}
            ) {

                Icon(
                    imageVector =
                        Icons.Default.Notifications,

                    contentDescription =
                        "Notifications"
                )
            }
        }


        // ============================================================
        // CONTENT
        // ============================================================

        Column(
            modifier =
                Modifier
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


            // ========================================================
            // HEADER
            // ========================================================

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
                                "Confirm that you are physically present at the institute.",

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


            // ========================================================
            // INSTITUTE
            // ========================================================

            Text(
                text =
                    "INSPECTION LOCATION",

                fontSize =
                    11.sp,

                fontWeight =
                    FontWeight.Bold,

                color =
                    Color(0xFF64748B),

                letterSpacing =
                    0.5.sp
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
                            FontWeight.Bold
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
                }
            }


            // ========================================================
            // CURRENT LOCATION
            // ========================================================

            Text(
                text =
                    "CURRENT LOCATION",

                fontSize =
                    11.sp,

                fontWeight =
                    FontWeight.Bold,

                color =
                    Color(0xFF64748B),

                letterSpacing =
                    0.5.sp
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
                                    Color(0xFF174A7E)
                            )
                        }


                        Spacer(
                            modifier =
                                Modifier.height(10.dp)
                        )


                        Text(
                            text =
                                when {

                                    isTestLocation ->
                                        "Test location active"

                                    currentLatitude != null ->
                                        "Current location captured"

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


                        Text(
                            text =
                                when {

                                    isLoading ->
                                        "Getting GPS location..."

                                    isTestLocation ->
                                        "Simulated at institute"

                                    currentLatitude != null ->
                                        "GPS location received"

                                    else ->
                                        "Tap VERIFY LOCATION below"
                                },

                            fontSize =
                                11.sp,

                            color =
                                Color(0xFF64748B)
                        )
                    }
                }
            }


            // ========================================================
            // LOCATION DETAILS
            // ========================================================

            Text(
                text =
                    "LOCATION DETAILS",

                fontSize =
                    11.sp,

                fontWeight =
                    FontWeight.Bold,

                color =
                    Color(0xFF64748B),

                letterSpacing =
                    0.5.sp
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
                        Arrangement.spacedBy(15.dp)
                ) {

                    LocationRow(
                        icon =
                            Icons.Default.Navigation,

                        label =
                            "Distance from institute",

                        value =
                            distanceMeters?.let {
                                "${it.toInt()} metres"
                            } ?: "Not captured"
                    )


                    LocationRow(
                        icon =
                            Icons.Default.MyLocation,

                        label =
                            "GPS accuracy",

                        value =
                            accuracyMeters?.let {
                                "± ${it.toInt()} metres"
                            } ?: "Not captured"
                    )


                    LocationRow(
                        icon =
                            Icons.Default.LocationOn,

                        label =
                            "Location status",

                        value =
                            when {

                                locationVerified ->
                                    "Verified"

                                distanceMeters != null ->
                                    "Outside permitted radius"

                                else ->
                                    "Not verified"
                            },

                        valueColor =
                            when {

                                locationVerified ->
                                    Color(0xFF16A34A)

                                distanceMeters != null ->
                                    Color(0xFFDC2626)

                                else ->
                                    Color(0xFFD97706)
                            }
                    )


                    if (
                        currentLatitude != null &&
                        currentLongitude != null
                    ) {

                        LocationRow(
                            icon =
                                Icons.Default.MyLocation,

                            label =
                                if (isTestLocation)
                                    "Test coordinates"
                                else
                                    "Current coordinates",

                            value =
                                String.format(
                                    "%.6f, %.6f",
                                    currentLatitude,
                                    currentLongitude
                                )
                        )
                    }
                }
            }


            // ========================================================
            // ERROR
            // ========================================================

            errorMessage?.let { message ->

                Card(
                    modifier =
                        Modifier.fillMaxWidth(),

                    shape =
                        RoundedCornerShape(14.dp),

                    colors =
                        CardDefaults.cardColors(
                            containerColor =
                                Color(0xFFFEF2F2)
                        ),

                    border =
                        BorderStroke(
                            1.dp,
                            Color(0xFFFECACA)
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
                                Icons.Default.ErrorOutline,

                            contentDescription =
                                null,

                            tint =
                                Color(0xFFDC2626),

                            modifier =
                                Modifier.size(24.dp)
                        )


                        Spacer(
                            modifier =
                                Modifier.size(10.dp)
                        )


                        Text(
                            text =
                                message,

                            fontSize =
                                12.sp,

                            color =
                                Color(0xFF991B1B)
                        )
                    }
                }
            }


            // ========================================================
            // SUCCESS
            // ========================================================

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
                                Modifier.size(24.dp),

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
                                    if (isTestLocation)
                                        "TEST LOCATION VERIFIED"
                                    else
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
                                    if (isTestLocation)
                                        "Testing mode: simulated presence at the institute."
                                    else
                                        "You are within the permitted inspection radius.",

                                fontSize =
                                    12.sp,

                                color =
                                    Color(0xFF4D7C5B)
                            )
                        }
                    }
                }
            }


            Spacer(
                modifier =
                    Modifier.height(2.dp)
            )


            // ========================================================
            // REAL GPS BUTTON
            // ========================================================

            if (!locationVerified) {

                Button(
                    onClick =
                        {
                            startVerification()
                        },

                    enabled =
                        !isLoading,

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

                    if (isLoading) {

                        CircularProgressIndicator(
                            modifier =
                                Modifier.size(20.dp),

                            color =
                                Color.White,

                            strokeWidth =
                                2.dp
                        )

                    } else {

                        Icon(
                            imageVector =
                                Icons.Default.MyLocation,

                            contentDescription =
                                null,

                            modifier =
                                Modifier.size(19.dp)
                        )
                    }


                    Spacer(
                        modifier =
                            Modifier.size(8.dp)
                    )


                    Text(
                        text =
                            if (isLoading)
                                "VERIFYING LOCATION..."
                            else
                                "VERIFY LOCATION",

                        fontSize =
                            14.sp,

                        fontWeight =
                            FontWeight.Bold
                    )
                }
            }


            // ========================================================
            // TEST MODE BUTTON
            // ========================================================

            if (!locationVerified) {

                Button(
                    onClick =
                        {
                            useTestLocation()
                        },

                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(48.dp),

                    shape =
                        RoundedCornerShape(12.dp),

                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor =
                                Color(0xFFE2E8F0),
                            contentColor =
                                Color(0xFF334155)
                        )
                ) {

                    Icon(
                        imageVector =
                            Icons.Default.Navigation,

                        contentDescription =
                            null,

                        modifier =
                            Modifier.size(18.dp)
                    )


                    Spacer(
                        modifier =
                            Modifier.size(8.dp)
                    )


                    Text(
                        text =
                            "USE TEST LOCATION",

                        fontSize =
                            13.sp,

                        fontWeight =
                            FontWeight.Bold
                    )
                }


                Text(
                    text =
                        "Development testing only",

                    modifier =
                        Modifier.fillMaxWidth(),

                    fontSize =
                        10.sp,

                    color =
                        Color(0xFF94A3B8),

                    textAlign =
                        androidx.compose.ui.text.style.TextAlign.Center
                )
            }


            // ========================================================
            // CONTINUE
            // ========================================================

            if (locationVerified) {

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
            }


            Spacer(
                modifier =
                    Modifier.height(16.dp)
            )
        }
    }
}


// ================================================================
// LOCATION ROW
// ================================================================

@Composable
private fun LocationRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String,
    valueColor: Color =
        Color(0xFF1E293B)
) {

    Row(
        modifier =
            Modifier.fillMaxWidth(),

        verticalAlignment =
            Alignment.CenterVertically
    ) {

        Icon(
            imageVector =
                icon,

            contentDescription =
                null,

            modifier =
                Modifier.size(20.dp),

            tint =
                Color(0xFF2563A6)
        )


        Spacer(
            modifier =
                Modifier.size(12.dp)
        )


        Column(
            modifier =
                Modifier.weight(1f)
        ) {

            Text(
                text =
                    label,

                fontSize =
                    11.sp,

                color =
                    Color(0xFF64748B)
            )


            Spacer(
                modifier =
                    Modifier.height(2.dp)
            )


            Text(
                text =
                    value,

                fontSize =
                    14.sp,

                fontWeight =
                    FontWeight.SemiBold,

                color =
                    valueColor
            )
        }
    }
}