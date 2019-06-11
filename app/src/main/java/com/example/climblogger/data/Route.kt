package com.example.climblogger.data

data class Route(
    val name: String,
    val grade: String
) {
    override fun toString(): String {
        return "$name - $grade"
    }
}