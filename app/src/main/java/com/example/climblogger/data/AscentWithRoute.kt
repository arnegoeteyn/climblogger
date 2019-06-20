package com.example.climblogger.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

@Entity
data class AscentWithRoute(
    @Embedded(prefix = "ascent_")
    val ascent: Ascent,
    @Embedded
    val route: Route
)