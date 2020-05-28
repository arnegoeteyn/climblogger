package com.example.climblogger.util

fun castToRange(value: Float, biggestValue: Float): Float {
    // max value should be 75 percent of the itemView's space
    val percentageOf = value / biggestValue
    return percentageOf * 75
}