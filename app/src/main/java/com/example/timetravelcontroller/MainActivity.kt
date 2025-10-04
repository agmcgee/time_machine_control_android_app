package com.example.timetravelcontroller

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.timetravelcontroller.ui.theme.TimeTravelTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TimeTravelTheme {
                StandardButtonExample()
            }
        }
    }
}

@Composable
fun StandardButtonExample() {
    val coroutineScope = rememberCoroutineScope()
    var targetAddress by remember { mutableStateOf("192.168.1.100") } // Example IP
    var targetPort by remember { mutableStateOf(12345) } // Example Port

    Column(modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
        )
    {
        Surface(color = Color.Cyan) {
            Text(
                text = "Time Travel Controller",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(24.dp))
        }
        TextField(
            value = targetAddress,
            onValueChange = { targetAddress = it },
            label = { Text(text = "Target Ip",
                modifier = Modifier.fillMaxWidth()) },
        )
        TextField(
            value = targetPort.toString(),
            onValueChange = { newValue ->
                targetPort = newValue.toIntOrNull() ?: 0
            },
            label = { Text(text = "Target Port",
                modifier = Modifier.fillMaxWidth()) }
        )
        Button(
            onClick = { coroutineScope.launch {
                sendUdpMessage("1", targetAddress, targetPort)
            }},
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text("Scenario 1")
        }
        Button(
            onClick = { coroutineScope.launch {
                sendUdpMessage("2", targetAddress, targetPort)
            }},
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text("Scenario 2")
        }
        Button(
            onClick = { coroutineScope.launch {
                sendUdpMessage("3", targetAddress, targetPort)
            }},
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text("Scenario 3")
        }
        Button(
            onClick = { coroutineScope.launch {
                sendUdpMessage("4", targetAddress, targetPort)
            }},
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text("Scenario 4")
        }
    }
}

suspend fun sendUdpMessage(
    message: String,
    targetAddress: String,
    targetPort: Int
){
    withContext(Dispatchers.IO) { // Perform network operation on IO dispatcher
        try {
            val socket = DatagramSocket()
            val sendData = message.toByteArray()
            val serverAddress = InetAddress.getByName(targetAddress)
            val sendPacket = DatagramPacket(sendData, sendData.size, serverAddress, targetPort)
            socket.send(sendPacket)
            socket.close() // Close the socket after sending
            println("UDP message sent: $message to $targetAddress:$targetPort")
        } catch (e: IOException) {
            println("Error sending UDP message: ${e.message}")
            // Handle the error (e.g., show a Toast or update UI state)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TimeTravelPreview() {
    TimeTravelTheme {
        StandardButtonExample()
    }
}