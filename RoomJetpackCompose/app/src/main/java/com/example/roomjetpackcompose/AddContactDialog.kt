package com.example.roomjetpackcompose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.roomjetpackcompose.data.local.events.ContactEvent
import com.example.roomjetpackcompose.data.local.state.ContactState

@Composable
fun AddContactDialog(
    state: ContactState,
    onEvent: (ContactEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = {
            onEvent(ContactEvent.hideDialog)
        },
        title = {
            Text("Add contact")
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Name
                OutlinedTextField(
                    value = state.firstName,
                    onValueChange = {
                        onEvent(ContactEvent.SetFirstName(it))
                    },
                    label = {
                        Text(text = "First name")
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                // Last name
                OutlinedTextField(
                    value = state.lastName,
                    onValueChange = {
                        onEvent(ContactEvent.SetLastName(it))
                    },
                    label = {
                        Text(text = "Last name")
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                // Phone number
                OutlinedTextField(
                    value = state.phoneNumber,
                    onValueChange = {
                        onEvent(ContactEvent.SetPhoneNumber(it))
                    },
                    label = {
                        Text(text = "Phone number")
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onEvent(ContactEvent.SaveContact)
                }
            ) {
                Text(text = "Save contact")
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    onEvent(ContactEvent.hideDialog)
                }
            ) {
                Text("Cancel")
            }
        },
        modifier = modifier
    )
}