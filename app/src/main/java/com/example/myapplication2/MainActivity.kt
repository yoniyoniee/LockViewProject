package com.example.lockscreen

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication2.R

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import kotlin.math.abs

abstract class OnSwipeTouchListener(context: Context) : View.OnTouchListener {

    private val gestureDetector = GestureDetector(context, GestureListener())

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    private inner class GestureListener : GestureDetector.SimpleOnGestureListener() {

        private val SWIPE_THRESHOLD = 100
        private val SWIPE_VELOCITY_THRESHOLD = 100

        override fun onDown(e: MotionEvent): Boolean {
            return true
        }

        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            val diffY = e2?.y?.minus(e1?.y ?: 0f) ?: 0f
            if (abs(diffY) > SWIPE_THRESHOLD && abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                if (diffY < 0) {
                    onSwipeUp()
                }
                return true
            }
            return false
        }
    }

    abstract fun onSwipeUp()

//    companion object {
//        private const val SWIPE_THRESHOLD = 100
//        private const val SWIPE_VELOCITY_THRESHOLD = 100
//    }
}


class MainActivity : AppCompatActivity() {

    private lateinit var lockScreenView: FrameLayout
    private lateinit var backgroundView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lockScreenView = findViewById(R.id.lock_screen_view)
        backgroundView = findViewById(R.id.background_view)

        val swipeListener = object : OnSwipeTouchListener(this) {
            override fun onSwipeUp() {
                // 잠금 화면을 숨기고 배경화면을 표시
                lockScreenView.visibility = View.GONE
                backgroundView.visibility = View.VISIBLE
            }
        }

        // 잠금화면에 터치 리스너 설정
        lockScreenView.setOnTouchListener(swipeListener)
    }
}
