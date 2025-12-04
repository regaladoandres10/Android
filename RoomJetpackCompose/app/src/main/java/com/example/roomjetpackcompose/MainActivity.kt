package com.example.roomjetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.roomjetpackcompose.data.local.AppDatabase
import com.example.roomjetpackcompose.navigation.screen.ContactScreen
import com.example.roomjetpackcompose.ui.theme.RoomJetpackComposeTheme
import com.example.roomjetpackcompose.viewmodel.ContactViewModel

class MainActivity : ComponentActivity() {

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "contacts.db"
        ).build()
    }

    //Modelo de vista
    private val viewModel by viewModels<ContactViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ContactViewModel(db.dao) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RoomJetpackComposeTheme {
                val state by viewModel.state.collectAsState()
                ContactScreen(
                    state = state,
                    onEvent = viewModel::onEvent
                )
            }
        }
    }
}
