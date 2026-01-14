package com.example.initd2c

import android.content.res.Configuration
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import com.github.takahirom.roborazzi.captureRoboImage
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
class PreviewSnapshotTest {

    @get:Rule
    val composeRule = createComposeRule()

    companion object {
        // Platform types
        const val PLATFORM_ANDROID = "android"
        const val PLATFORM_IOS = "ios"
        const val PLATFORM_HARMONY = "harmony"

        // Screen size configurations (width x height in dp)
        enum class ScreenSize(val width: Int, val height: Int) {
            PHONE_SMALL(360, 640),
            PHONE_MEDIUM(390, 844),
            PHONE_LARGE(428, 926),
            TABLET(800, 1280)
        }
    }

    private fun getSnapshotDir(): File {
        val projectRoot = File(System.getProperty("user.dir") ?: ".")
        return File(projectRoot, "src/test/snapshots").apply { mkdirs() }
    }

    private fun generateFileName(platform: String, screenSize: ScreenSize, name: String): String {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        return "${platform}_${screenSize.width}_${screenSize.height}_${name}_$timestamp.png"
    }

    // Android - Phone Small (360x640)
    @Test
    @Config(qualifiers = "w360dp-h640dp-mdpi")
    fun androidPhoneSmallSnapshot() {
        captureSnapshot(PLATFORM_ANDROID, ScreenSize.PHONE_SMALL)
    }

    // Android - Phone Medium (390x844)
    @Test
    @Config(qualifiers = "w390dp-h844dp-xhdpi")
    fun androidPhoneMediumSnapshot() {
        captureSnapshot(PLATFORM_ANDROID, ScreenSize.PHONE_MEDIUM)
    }

    // Android - Phone Large (428x926)
    @Test
    @Config(qualifiers = "w428dp-h926dp-xxhdpi")
    fun androidPhoneLargeSnapshot() {
        captureSnapshot(PLATFORM_ANDROID, ScreenSize.PHONE_LARGE)
    }

    // Android - Tablet (800x1280)
    @Test
    @Config(qualifiers = "w800dp-h1280dp-mdpi")
    fun androidTabletSnapshot() {
        captureSnapshot(PLATFORM_ANDROID, ScreenSize.TABLET)
    }

    private fun captureSnapshot(platform: String, screenSize: ScreenSize) {
        val outputDir = getSnapshotDir()
        val fileName = generateFileName(platform, screenSize, "app_preview")
        val capturedFile = File(outputDir, fileName)

        composeRule.setContent {
            App()
        }

        composeRule.onRoot().captureRoboImage(capturedFile.absolutePath)
        println("Screenshot saved to: ${capturedFile.absolutePath}")
    }
}
