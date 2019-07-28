package com.example.climblogger.data


class RouteAmount(val grade: String, val amount: Int) {

    override fun toString(): String {
        return "$grade -> $amount"
    }
}