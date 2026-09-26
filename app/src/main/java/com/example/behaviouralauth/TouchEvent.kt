package com.example.behaviouralauth

data class TouchEvent(
    val sessionId: String,
    val timestampMs: Long,
    val action: String,   // "DOWN", "MOVE", "UP"
    val x: Float,
    val y: Float,
    val pressure: Float,
    val size: Float,       // contact area proxy from MotionEvent.getSize()
    val pointerId: Int
)