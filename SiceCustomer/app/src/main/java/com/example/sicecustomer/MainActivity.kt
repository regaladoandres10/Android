package com.example.sicecustomer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.sicecustomer.ui.SiceClient
import com.example.sicecustomer.ui.screens.CargaAcademicaClientScreen
import com.example.sicecustomer.ui.theme.SiceCustomerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SiceCustomerTheme {
                SiceClient()
                //CardexClientScreen()
                //CargaAcademicaClientScreen()
            }
        }
    }
}

