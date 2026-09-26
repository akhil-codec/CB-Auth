# CB-Auth 🔐📱

**Continuous Behavioral Authentication & Active Device Enforcement for Android**

`CB-Auth` (Continuous Behavioral Authentication) is an Android research and development application designed for **Behavioral Biometric Authentication**. Unlike traditional static authentication (such as one-time fingerprint scans or PINs at unlock), `CB-Auth` continuously verifies user identity during device usage by analyzing unique gesture dynamics—such as **typing pressure, touch contact area (size), gesture coordinates, and interaction timing**.

If a behavioral anomaly or unauthorized usage is detected, the application enforces security by programmatically locking the Android device using system-level **Device Administrator privileges**.

---

## ✨ Key Features

- 👆 **High-Precision Touch Dynamics Logging**: Captures fine-grained `MotionEvent` data (`ACTION_DOWN`, `ACTION_MOVE`, `ACTION_UP`), recording:
  - $(x, y)$ Screen Coordinates
  - Finger Pressure (`MotionEvent.getPressure()`)
  - Touch Contact Area Proxy (`MotionEvent.getSize()`)
  - Timestamp in milliseconds
  - Multi-touch Pointer ID & Session UUID
- 💾 **CSV Dataset Exporter**: Automatically buffers and exports recorded touch interactions into session-specific CSV datasets (`session_<id>.csv`) in app-private storage for machine learning analysis.
- 🔒 **Active Security Enforcement**: Integrates Android `DeviceAdminReceiver` and `DevicePolicyManager` to programmatically lock the phone screen (`lockNow()`) upon detecting security anomalies or behavioral drift.
- 🎯 **Interactive Data Capture Harness**: Includes a dedicated testing activity with free-form typing fields and tap grids to collect diverse behavioral touch patterns.

---

## Project Architecture

```text
com.example.behaviouralauth
 ├── MainActivity.kt             # Admin permission setup & screen lock enforcement trigger
 ├── TouchCaptureActivity.kt     # Interactive UI harness for capturing touch gesture dynamics
 ├── TouchLogger.kt              # In-memory buffer & CSV dataset exporter
 ├── TouchEvent.kt               # Data model for individual touch metric events
 └── MyDeviceAdminReceiver.kt    # Android Device Admin Receiver handling security policies
```

---

## 📊 CSV Dataset Schema

The generated session CSV files (`session_<id>.csv`) follow this schema:

| Column Header | Type | Description |
| :--- | :--- | :--- |
| `sessionId` | String | Unique 8-character UUID identifying the session |
| `timestampMs` | Long | Epoch timestamp in milliseconds |
| `action` | String | Touch event type (`DOWN`, `MOVE`, `UP`) |
| `x` | Float | X-coordinate of the touch event on screen |
| `y` | Float | Y-coordinate of the touch event on screen |
| `pressure` | Float | Touch pressure applied by the finger |
| `size` | Float | Normalized touch contact area proxy |
| `pointerId` | Int | Multi-touch pointer identifier |

---

## 🚀 Getting Started

### Prerequisites

* **Android Studio**: Ladybug / Jellyfish or newer
* **JDK**: Version 11 or higher
* **Android Device / Emulator**: Running Android 7.0 (API Level 24) or higher

### Building & Running

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/your-username/CB-Auth.git
   cd CB-Auth
   ```

2. **Open in Android Studio**:
   - Open Android Studio and select **Open**.
   - Navigate to the cloned `CB-Auth` directory.

3. **Build and Run**:
   - Connect your Android device via USB with **USB Debugging** enabled.
   - Click the green **Run** button (`Shift + F10`) to build and install the APK on your device.

---

## 📱 How to Use

1. **Request Device Admin Permissions**:
   - Open the app and tap **"Request Device Admin"**.
   - Grant Device Administrator rights when prompted by the system.
2. **Collect Behavioral Touch Data**:
   - Tap **"Open Touch Capture Session"**.
   - Type in the text field or tap inside the gesture box.
   - Tap **"End Session & Save"** to export the captured session to a CSV dataset.
3. **Inspect the Dataset**:
   - Open **Device Explorer** in Android Studio (`View > Tool Windows > Device Explorer`).
   - Navigate to `/data/data/com.example.behaviouralauth/files/sessions/`.
   - Double-click the generated `.csv` file to inspect or export the dataset.
4. **Test Security Lock**:
   - On the main screen, tap **"Test lockNow()"** to test immediate device locking upon security alert.

---

## 🛠️ Tech Stack & Dependencies

* **Language**: [Kotlin](https://kotlinlang.org/)
* **UI Toolkit**: AndroidX AppCompat, Material Design Components, ConstraintLayout
* **Security & Policy**: Android `DevicePolicyManager`, `DeviceAdminReceiver`
* **Target SDK**: API 37 (Android 15)
* **Min SDK**: API 24 (Android 7.0)
* **Build System**: Gradle with Kotlin DSL (`build.gradle.kts`)

---

## 📄 License

This project is open-source and available under the [MIT License](LICENSE).
