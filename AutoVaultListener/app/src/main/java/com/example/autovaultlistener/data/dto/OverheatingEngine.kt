package com.example.autovaultlistener.data.dto

class OverheatingEngine(
    val propertyId: Int = 0,
    val status: String = "Unknown",
    val unit: String = "percent",
    val value: Int = 100
)