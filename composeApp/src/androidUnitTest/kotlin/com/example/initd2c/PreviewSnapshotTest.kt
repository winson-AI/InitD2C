package com.example.initd2c

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.unit.dp
import com.github.takahirom.roborazzi.RoborazziRule
import com.github.takahirom.roborazzi.captureRoboImage
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestName
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

    @get:Rule
    val testName = TestName()

    private var capturedFile: File? = null

    @Test
    fun appPreviewSnapshot() {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val fileName = "app_preview_$timestamp.png"

        val projectRoot = File(System.getProperty("user.dir"))
        val testOutputDir = File(projectRoot, "test-screenshots")
        if (!testOutputDir.exists()) {
            testOutputDir.mkdirs()
        }

        capturedFile = File(testOutputDir, fileName)

        composeRule.setContent {
            AppPreviewContent()
        }

        composeRule.onRoot().captureRoboImage(capturedFile!!.absolutePath)

        if (capturedFile!!.exists()) {
            println("Screenshot saved to: ${capturedFile!!.absolutePath}")
        } else {
            println("Screenshot file path: ${capturedFile!!.absolutePath}")
        }
    }
}

@Composable
fun AppPreviewContent() {
    MaterialTheme {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(onClick = { }) {
                Text("Click me!")
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Compose: Hello, Android!",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
}
