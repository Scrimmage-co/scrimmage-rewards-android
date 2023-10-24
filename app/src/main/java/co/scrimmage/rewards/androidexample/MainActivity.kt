package co.scrimmage.rewards.androidexample

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.webkit.JavascriptInterface
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import co.scrimmage.rewards.androidexample.ui.theme.ScrimmageRewardsExampleTheme
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.material3.Text
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.runtime.*
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScrimmageRewardsExampleTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    WebScreen(url = "https://coinflip.apps.scrimmage.co")
                }
            }
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebScreen(url: String) {
    var token by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    LaunchedEffect(key1 = true) {
        // Fetch the token
        val tokenResponse = fetchToken()
        token = tokenResponse?.token ?: ""
    }

    if (token.isNotEmpty()) {
        AndroidView(factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                webViewClient = WebViewClient()
                val urlWithToken = "$url?token=$token"
                loadUrl(urlWithToken)

                // Add the JavaScript interface to handle messages
                addJavascriptInterface(JavaScriptInterface {
                    // Handle the received message
                    message = it
                }, "Android")

                WebView.setWebContentsDebuggingEnabled(true);
                // You can send messages from JavaScript using "window.Android.handleMessage('your_message')"
            }
        })

        if (message.isNotEmpty()) {
            // Show on UI

            Text(text = message)
        }
    }
}

suspend fun fetchToken(): TokenResponse? = withContext(Dispatchers.IO) {
    // Use your real backend to load the token
    val client = OkHttpClient()
    val request = Request.Builder()
        .url("https://us-central1-bright-practice-331514.cloudfunctions.net/requestGenerateAuthInfo")
        .build()

    val response = client.newCall(request).execute()
    if (response.isSuccessful) {
        val responseString = response.body?.string()
        // Deserialize the JSON response
        // Adjust the deserialization logic according to the actual response structure
        // In this example, the response is deserialized into a TokenResponse object with a 'token' property
        return@withContext Gson().fromJson(responseString, TokenResponse::class.java)
    } else {
        // Handle the failure case
        return@withContext null
    }
}


data class TokenResponse(val token: String)

class JavaScriptInterface(private val onMessageReceived: (String) -> Unit) {
    @JavascriptInterface
    fun handleMessage(message: String) {
        // Handle the received message here
        onMessageReceived(message)
    }
}
