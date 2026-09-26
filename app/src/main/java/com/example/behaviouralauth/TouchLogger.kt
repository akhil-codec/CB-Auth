package com.example.behaviouralauth


import android.content.Context
import java.io.File
import java.io.FileWriter

class TouchLogger(private val context: Context) {

    private val buffer = mutableListOf<TouchEvent>()

    fun log(event: TouchEvent) {
        buffer.add(event)
    }

    fun eventCount(): Int = buffer.size

    /** Writes buffered events to a per-session CSV in app-private storage and clears the buffer. */
    fun flushToCsv(sessionId: String): File {
        val dir = File(context.filesDir, "sessions")
        if (!dir.exists()) dir.mkdirs()

        val file = File(dir, "session_${sessionId}.csv")
        FileWriter(file).use { writer ->
            writer.append("sessionId,timestampMs,action,x,y,pressure,size,pointerId\n")
            for (e in buffer) {
                writer.append("${e.sessionId},${e.timestampMs},${e.action},${e.x},${e.y},${e.pressure},${e.size},${e.pointerId}\n")
            }
        }
        buffer.clear()
        return file
    }
}