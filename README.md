# Nigrani

### Social Justice Monitoring & Inspection Platform

Nigrani is a Smart India Hackathon project for centralized monitoring of institutes and projects under the Ministry/Department of Social Justice & Empowerment.

The platform is designed to connect real-time monitoring, CCTV surveillance, anomaly detection, surprise inspections, GPS-based field verification, evidence capture, and inspection reporting in one workflow.

---

## Problem

Conventional periodic monitoring and institute-level reporting can make it difficult to identify irregularities quickly, verify field conditions, and maintain a reliable inspection trail.

Nigrani aims to provide a centralized monitoring system that helps authorized officials:

- Monitor institutes and projects in real time
- Identify attendance and operational anomalies
- Review CCTV and surveillance events
- Assign targeted or surprise inspections
- Verify an inspector's physical presence using GPS
- Capture geo-tagged field evidence
- Complete standardized inspection checklists
- Submit structured inspection reports
- Maintain a centralized compliance view

---

## Core Workflow

```text
Monitoring Dashboard
        ↓
Institute / AI Alert
        ↓
Inspection Assignment
        ↓
Inspector
        ↓
GPS Location Verification
        ↓
Inspection Checklist
        ↓
Camera Evidence Capture
        ↓
Inspection Report
        ↓
Final Submission
        ↓
Central Dashboard
```

---

## Key Features

### Central Monitoring Dashboard

- Active institute/project overview
- CCTV monitoring status
- Pending inspection count
- Critical alerts
- Attendance anomaly indicators
- Inspection status
- Quick navigation to monitoring modules

### Institute Monitoring

- Institute listing
- Search
- Healthy / Critical / Offline filtering
- Beneficiary information
- Attendance information
- CCTV status
- Last monitoring/check time
- Institute details

### Live Surveillance

- Camera selector
- Camera online/offline status
- Live CCTV monitoring interface
- AI monitoring indicators
- Attendance anomaly detection indicator
- Crowd density indicator
- Restricted-area indicator
- CCTV tampering indicator

The current frontend contains the surveillance interface; actual CCTV streaming will be connected through the backend.

### AI / Monitoring Alerts

The platform is designed to surface events such as:

- Attendance anomalies
- CCTV tampering
- Unusual inactivity
- Restricted-area movement
- Other operational irregularities

Alerts contain information such as severity, confidence, institute, location, timestamp, and description.

### Inspection Management

Supports:

- Routine inspections
- Surprise inspections
- Verification inspections
- Inspector assignment
- Inspection priority
- Inspection status tracking

Inspection states include:

```text
Pending
Assigned
In Progress
Completed
```

### GPS Location Verification

Before starting an inspection, the inspector verifies their physical presence at the assigned institute.

The Android application:

1. Requests location permission.
2. Checks whether location services are enabled.
3. Obtains the current GPS location.
4. Calculates distance from the institute.
5. Checks GPS accuracy.
6. Compares the distance with the configured inspection radius.
7. Allows the inspection to continue only after successful verification.

A development-only test-location option is used during development so the complete inspection flow can be tested without physically travelling to the configured mock institute.

> The development test-location option must be removed before the final production build.

### Inspection Checklist

The inspection checklist covers:

- Beneficiary attendance
- Staff availability
- Infrastructure condition
- Safety and accessibility
- Records and registers
- CCTV surveillance

Each item can be marked:

```text
Compliant
Needs Attention
Non-Compliant
```

The workflow requires all checklist items to be evaluated before continuing.

### Evidence Capture

Inspectors can capture photographic evidence directly using the Android device camera.

The intended evidence flow is:

```text
Camera
   ↓
Image File
   ↓
URI
   ↓
Evidence Preview
   ↓
Retake / Remove
   ↓
Submit Evidence
   ↓
Backend Multipart Upload
   ↓
Object/File Storage
   ↓
Permanent Image Reference
   ↓
Inspection Record
```

The Android `content://` URI is only a device-side reference. It is not the permanent backend image URL.

The backend is responsible for storing the uploaded file and returning a permanent image URL, object key, or evidence record reference.

### Inspection Reports

Reports contain:

- Inspection information
- Institute information
- Inspector information
- Location verification
- Checklist results
- Findings
- Evidence
- Overall compliance status
- Submission status

### Reports & Compliance

The reports module provides:

- Report search
- Compliance filtering
- Report listing
- Report details
- Findings review
- Evidence review
- Compliance assessment

---

## Repository Structure

The repository contains the Android application and backend components.

```text
Nigrani
│
├── android
│   ├── app
│   ├── gradle
│   ├── build.gradle.kts
│   ├── gradle.properties
│   ├── gradlew
│   ├── gradlew.bat
│   └── settings.gradle.kts
│
└── backend
    ├── src
    ├── gradle
    └── backend project files
```

### Android Package Structure

```text
com.dev.nigrani
│
├── data
│   ├── dto
│   ├── model
│   ├── remote
│   └── repository
│
├── navigation
│
├── ui
│   ├── components
│   ├── screens
│   │   ├── auth
│   │   ├── dashboard
│   │   ├── institutes
│   │   ├── surveillance
│   │   ├── alerts
│   │   ├── inspections
│   │   └── reports
│   ├── theme
│   └── viewmodel
│
├── utils
├── AppContainer.kt
└── MainActivity.kt
```

---

## Technology Stack

### Android

- Kotlin
- Jetpack Compose
- Material 3
- Android Activity Result APIs
- Google Fused Location Provider
- Android Camera APIs
- FileProvider

### Architecture

```text
Compose UI
    ↓
ViewModel / State
    ↓
Repository
    ↓
Remote API
    ↓
Backend
    ↓
Database / Object Storage
```

### Important Architecture Decision

Nigrani does **not** use Room or an Android local database for its core application data.

Temporary Compose/ViewModel state may be used during an active inspection, while persistent application data belongs to the backend.

---

## Backend Integration

The frontend is being developed independently from the backend so that the backend can later be integrated without rebuilding the UI.

Expected backend integrations include:

- Authentication
- Institutes
- Institute details
- Dashboard summaries
- Inspection assignments
- GPS verification records
- Checklist data
- Evidence upload
- Inspection submission
- Alerts
- Reports
- CCTV stream endpoints

The exact API endpoints, request fields, authentication mechanism, response format, and storage provider are defined by the backend implementation and should not be assumed by the frontend.

---

## Design System

Nigrani follows a professional government monitoring / command-center visual language.

### UI Principles

- Clear information hierarchy
- Compact information-dense cards
- Light and readable interface
- Restrained visual design
- Rounded corners
- Subtle borders
- Clear section labels
- Professional icons
- Strong status visibility
- Minimal unnecessary animation

### Color Language

```text
Primary      → Deep Navy / Government Blue
Secondary    → Blue
Healthy      → Green
Warning      → Amber
Critical     → Red
Background   → Off-white
Surface      → White
```

The current Android theme uses:

```kotlin
primary = Color(0xFF174A7E)
secondary = Color(0xFF2563A6)
background = Color(0xFFF7F9FC)
surface = Color.White
```

---

## Application Screens

### Authentication

- Login

### Monitoring

- Dashboard
- Institutes
- Institute Details
- Live Surveillance
- AI Alerts

### Inspections

- Inspections
- Inspection Assignment
- Location Verification
- Inspection Checklist
- Evidence Capture
- Inspection Report
- Inspection Submitted

### Reports

- Reports
- Report Details

---

## Current Development Status

### Frontend

| Module | Status |
|---|---|
| Login UI | Completed |
| Dashboard | Completed |
| Institutes | Completed |
| Institute Details | Completed |
| Live Surveillance UI | Completed |
| Alerts UI | Completed |
| Inspections | Completed |
| Inspection Assignment | Completed |
| Real GPS Verification | Completed |
| Inspection Checklist | Completed |
| Real Camera Capture | Completed |
| Inspection Report UI | Completed |
| Inspection Submitted | Completed |
| Reports | Completed |
| Report Details | Completed |
| Backend API Integration | In Progress |
| Evidence Backend Upload | Pending Backend Contract |
| Real CCTV Streaming | Backend Dependent |
| Dynamic Dashboard Data | Backend Dependent |
| Dynamic Alerts | Backend Dependent |
| Dynamic Inspection Data | Backend Dependent |

---

## MVP Demonstration

The strongest end-to-end demonstration is:

```text
Dashboard
    ↓
Critical Attendance Anomaly
    ↓
AI Alert
    ↓
Assign Surprise Inspection
    ↓
Inspector Assignment
    ↓
GPS Verification
    ↓
Inspection Checklist
    ↓
Real Camera Evidence
    ↓
Inspection Report
    ↓
Submit Inspection
    ↓
Dashboard
```

This demonstrates the complete transition from a monitoring anomaly to verified field inspection and evidence-based reporting.

---

## Development Guidelines

- Work incrementally and test changes on a physical Android device.
- Preserve the existing professional government-monitoring UI.
- Do not introduce Room/local database unless explicitly required.
- Do not invent backend API contracts.
- Keep mock data clearly distinguishable from real functionality.
- Avoid unnecessary redesigns of working screens.
- Use complete replacement files when a screen requires a substantial change.
- Keep captured evidence as a real file/URI during the active inspection flow.
- Store permanent evidence references in the backend, not Android `content://` URIs.
- Test every significant workflow change on the physical device.

---

## Getting Started

### Requirements

- Android Studio
- Android SDK
- Kotlin
- Android device or emulator
- Internet connection for backend functionality

### Android Application

1. Open the `android` directory in Android Studio.
2. Allow Gradle to sync.
3. Connect an Android device or start an emulator.
4. Build and run the application.

### Development GPS Testing

The inspection flow includes a development-only test-location option for testing the checklist and report workflow when the developer is not physically located at the configured institute.

This option must be removed before production deployment.

### Camera Testing

Grant camera permission when requested and capture evidence using the device camera.

---

## Production Checklist

- [ ] Connect authentication API
- [ ] Connect institute APIs
- [ ] Connect dashboard APIs
- [ ] Connect inspection APIs
- [ ] Connect alert APIs
- [ ] Connect reports APIs
- [ ] Persist GPS verification through backend
- [ ] Implement multipart evidence upload
- [ ] Connect object/file storage
- [ ] Store permanent evidence references
- [ ] Connect final inspection submission API
- [ ] Connect real CCTV streams
- [ ] Add production loading/error/empty states
- [ ] Remove development test-location bypass
- [ ] Verify Android permissions
- [ ] Test complete end-to-end workflow
- [ ] Configure production backend
- [ ] Generate and test release build

---

## Project Goal

Nigrani aims to improve transparency, accountability, and real-time monitoring by connecting centralized institutional monitoring with verified field inspections, live evidence capture, and structured compliance reporting.

### Nigrani

**Safe • Inclusive • Accountable**
