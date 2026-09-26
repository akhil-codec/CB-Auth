package com.example.behaviouralauth


import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.UUID

class TouchCaptureActivity : AppCompatActivity() {

    private lateinit var logger: TouchLogger
    private lateinit var sessionId: String
    private lateinit var countLabel: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_touch_capture)

        logger = TouchLogger(applicationContext)
        sessionId = UUID.randomUUID().toString().take(8)
        countLabel = findViewById(R.id.tvEventCount)

        val typingBox = findViewById<EditText>(R.id.etFreeType)
        val tapGrid = findViewById<TextView>(R.id.tvTapGrid) // stand-in for a real grid view

        val listener = View.OnTouchListener { _, event -> handleTouch(event); false }
        typingBox.setOnTouchListener(listener)
        tapGrid.setOnTouchListener(listener)

        findViewById<Button>(R.id.btnEndSession).setOnClickListener {
            val file = logger.flushToCsv(sessionId)
            Toast.makeText(this, "Saved: ${file.absolutePath}", Toast.LENGTH_LONG).show()
            sessionId = UUID.randomUUID().toString().take(8) // start a fresh session
            countLabel.text = "Events: 0"
        }
    }

    private fun handleTouch(event: MotionEvent) {
        val actionName = when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> "DOWN"
            MotionEvent.ACTION_MOVE -> "MOVE"
            MotionEvent.ACTION_UP -> "UP"
            else -> return // ignore POINTER_DOWN/UP, CANCEL for Week 1 simplicity
        }

        val pointerIndex = event.actionIndex
        val touchEvent = TouchEvent(
            sessionId = sessionId,
            timestampMs = System.currentTimeMillis(),
            action = actionName,
            x = event.getX(pointerIndex),
            y = event.getY(pointerIndex),
            pressure = event.getPressure(pointerIndex),
            size = event.getSize(pointerIndex),
            pointerId = event.getPointerId(pointerIndex)
        )

        logger.log(touchEvent)
        countLabel.text = "Events: ${logger.eventCount()}"
    }
}