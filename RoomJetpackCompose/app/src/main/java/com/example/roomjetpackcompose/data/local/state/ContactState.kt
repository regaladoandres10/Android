package com.example.roomjetpackcompose.data.local.state

import com.example.roomjetpackcompose.data.local.SortType
import com.example.roomjetpackcompose.data.local.entities.Contact
import com.example.roomjetpackcompose.data.local.events.ContactEvent

data class ContactState(
    val contacts: List<Contact> = emptyList(),
    val firstName: String = "",
    val lastName: String = "",
    val phoneNumber: String = "",
    val isAddingContact: Boolean = false,
    val sortType: SortType = SortType.FIRST_NAME
)
