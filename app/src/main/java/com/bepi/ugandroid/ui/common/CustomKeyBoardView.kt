package com.bepi.ugandroid.ui.common

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener
import android.util.AttributeSet
import android.util.Log
import com.bepi.ugandroid.R
import java.util.*


class CustomKeyBoardView : KeyboardView, OnKeyboardActionListener {
    private val delKeyBackgroundColor = -0x252526
    private var keyIconRect: Rect? = null
    private val keyGap: Int = 20

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        Log.d(TAG, "PwdKeyboardView: two params")
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        Log.d(TAG, "PwdKeyboardView: three params")
        init(context)
    }

    private fun init(context: Context) {
        val keyboard = Keyboard(context, R.xml.custom_keyboard) // 初始化 keyboard
        setKeyboard(keyboard)
        isEnabled = true
        isFocusable = true
        isPreviewEnabled = false // 设置点击按键不显示预览气泡
        onKeyboardActionListener = this
    }

    /**
     * 重新绘制删除按键和空白键
     */
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val keys = keyboard.keys
        for (key in keys) {
            if (key.codes[0] == KEY_EMPTY) {
                // 绘制空白键背景
                drawKeyBackground(key, canvas, delKeyBackgroundColor)
            }
            if (key.codes[0] == Keyboard.KEYCODE_DELETE) {
                // 删除删除按键背景
                drawKeyBackground(key, canvas, delKeyBackgroundColor)
                // 绘制删除按键图标
                drawKeyIcon(key, canvas, resources.getDrawable(R.drawable.icon_backspace))
            }
        }
    }

    /**
     * 绘制按键的背景
     */
    private fun drawKeyBackground(key: Keyboard.Key, canvas: Canvas, color: Int) {
        val drawable = ColorDrawable(color)

        drawable.setBounds(key.x, key.y + keyGap, key.x + key.width, key.y + key.height + 20)
        drawable.draw(canvas)
    }

    /**
     * 绘制按键的 icon
     */
    private fun drawKeyIcon(key: Keyboard.Key, canvas: Canvas, iconDrawable: Drawable?) {
        if (iconDrawable == null) {
            return
        }
        // 计算按键icon 的rect 范围
        if (keyIconRect == null || keyIconRect!!.isEmpty()) {
            // 得到 keyicon 的显示大小，因为图片放在不同的drawable-dpi目录下，显示大小也不一样
            val intrinsicWidth = iconDrawable.intrinsicWidth
            val intrinsicHeight = iconDrawable.intrinsicHeight
            var drawWidth = intrinsicWidth
            var drawHeight = intrinsicHeight
            // 限制图片的大小，防止图片按键范围
            if (drawWidth > key.width) {
                drawWidth = key.width
                // 此时高就按照比例缩放
                drawHeight = (drawWidth * 1.0f / intrinsicWidth * intrinsicHeight).toInt()
            } else if (drawHeight > key.height) {
                drawHeight = key.height
                drawWidth = (drawHeight * 1.0f / intrinsicHeight * intrinsicWidth).toInt()
            }
            // 获取图片的 x,y 坐标,图片在按键的正中间
            val left = key.x + key.width / 2 - drawWidth / 2
            val top = key.y + key.height / 2 - drawHeight / 2 + keyGap
            keyIconRect = Rect(left, top, left + drawWidth, top + drawHeight)
        }
        if (keyIconRect != null && !keyIconRect!!.isEmpty()) {
            iconDrawable.bounds = keyIconRect as Rect
            iconDrawable.draw(canvas)
        }
    }

    override fun onPress(primaryCode: Int) {}
    override fun onRelease(primaryCode: Int) {}

    /**
     * 处理按键的点击事件
     */
    override fun onKey(primaryCode: Int, keyCodes: IntArray) {
        Log.d(
            TAG,
            "onKey: primaryCode = " + primaryCode + ", keyCodes = " + Arrays.toString(keyCodes)
        )
        if (primaryCode == KEY_EMPTY) {
            return
        }
        if (listener != null) {
            if (primaryCode == Keyboard.KEYCODE_DELETE) {
                listener!!.onDelete()
            } else {
//                listener!!.onInput(primaryCode as Char.toString())
            }
        }
    }

    override fun onText(charSequence: CharSequence) {}
    override fun swipeLeft() {}
    override fun swipeRight() {}
    override fun swipeDown() {}
    override fun swipeUp() {}
    interface OnKeyListener {
        // 输入回调
        fun onInput(text: String?)

        // 删除回调
        fun onDelete()
    }

    private var listener: OnKeyListener? = null
    fun setOnKeyListener(listener: OnKeyListener?) {
        this.listener = listener
    }

    companion object {
        private const val TAG = "PwdKeyboardView"
        private const val KEY_EMPTY = -10
    }
}