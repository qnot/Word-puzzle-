package com.example.model

data class SceneObject(
    val id: String,
    val name: String,
    val label: String,
    val xRatio: Float,
    val yRatio: Float,
    val radiusRatio: Float = 0.10f,
    val iconKey: String = "star",
    val primaryColor: Long = 0xFF4F46E5,
    val secondaryColor: Long = 0xFFF59E0B
)
