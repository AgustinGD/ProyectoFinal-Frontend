package com.example.omnitricswearable.domain.model.entities

import java.util.Date

data class Routine(
     val id: String,
     val name: String,
     val isFavourite: Boolean,
     val createdAt: Date
 )