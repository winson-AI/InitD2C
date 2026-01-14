package com.example.initd2c

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import com.github.takahirom.roborazzi.captureRoboImage
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
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

    private val testOutputDir: File by lazy {
        val projectRoot = File(System.getProperty("user.dir") ?: ".")
        File(projectRoot, "test-screenshots").apply { mkdirs() }
    }

    @Test
    fun appPreviewSnapshot() {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val fileName = "app_preview_$timestamp.png"
        val capturedFile = File(testOutputDir, fileName)

        composeRule.setContent {
            App()
        }

        composeRule.onRoot().captureRoboImage(capturedFile.absolutePath)
        println("Screenshot saved to: ${capturedFile.absolutePath}")
    }
}
