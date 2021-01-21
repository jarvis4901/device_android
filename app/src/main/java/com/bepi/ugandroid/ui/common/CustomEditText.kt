package com.bepi.ugandroid.ui.common

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.text.Editable
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.KeyEvent
import androidx.appcompat.widget.AppCompatEditText
import com.bepi.ugandroid.R
import com.bepi.ugandroid.utils.DisplayUtil
import java.lang.StringBuilder


class CustomEditText : AppCompatEditText {
    private val STYLE_RECTANGLE = 0
    private val STYLE_ROUND_RECTANGLE = 1
    private val DEFAULT_STYLE = STYLE_RECTANGLE
    private val DEFAULT_PWD_COUNT = 6
    private val DEFAULT_STROKE_RADIUS = dp2Px(6)
    private val DEFAULT_STROKE_WIDTH = dp2Px(1)
    private val DEFAULT_STROKE_COLOR: Int = Color.parseColor("#CCCCCC")
    private val DEFAULT_DOT_COLOR: Int = Color.BLACK
    private val DEFAULT_DOT_RADIUS = dp2Px(4)
    private val DEFAULT_IS_DOT_STYLE = false
    private val DEFAULT_TEXT_SIZE = 24


    private var style // 控件的样式，矩形或圆角矩形
            = 0
    private var strokeRadius // 边框圆角的半径
            = 0f
    private var strokeWidth // 边框宽度
            = 0f
    private var strokeColor // 边框颜色
            = 0
    private var pwdDotColor // 密码圆点颜色
            = 0
    private var pwdDotRadius // 密码圆点半径
            = 0f
    private var mWidth // 控件宽度
            = 0
    private var mHeight // 控件高度
            = 0
    private var strokePaint // 绘制边框paint
            : Paint? = null
    private var pwdDotPaint // 绘制密码圆点paint
            : Paint? = null
    private var mCount // 密码框个数
            = 0
    private var cellWidth // 每个密码框的宽度
            = 0f
    private var paddingWidth = 0f
    private var halfStrokeWidth = 0f
    private var mCurInputCount // 当前输入字符个数
            = 0
    private var isDotStyle = false //输入是否显示为黑色密码圆点
    private var textSize = 32 //输入是否显示为黑色密码圆点
    private var textSizeToDp = 0f

//    private lateinit var inputTextSbuilder:StringBuilder

    constructor(context: Context) : this(context, null) {}

    /**
     * 无论xml布局文件中有没有写自定义属性，都调用两个参数的构造函数
     */
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initAttrs(context, attrs)
        init()

        textSizeToDp = DisplayUtil.px2dip(
            context,
            DisplayUtil.sp2px(context, textSize.toFloat()).toFloat()
        ).toFloat()
    }

    /**
     * 当有自定义的样式时，调用三个参数的构造函数
     */
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
    }

    /**
     * 初始化自定义属性
     */
    private fun initAttrs(context: Context, attrs: AttributeSet?) {
        val typedArray: TypedArray =
            context.obtainStyledAttributes(attrs, R.styleable.CustomEditText)
        style = typedArray.getInt(R.styleable.CustomEditText_style, DEFAULT_STYLE)
        mCount = typedArray.getInt(R.styleable.CustomEditText_pwdCount, DEFAULT_PWD_COUNT)
        strokeColor =
            typedArray.getColor(R.styleable.CustomEditText_strokeColor, DEFAULT_STROKE_COLOR)
        strokeWidth =
            typedArray.getDimension(R.styleable.CustomEditText_strokeWidth, DEFAULT_STROKE_WIDTH)
        strokeRadius =
            typedArray.getDimension(R.styleable.CustomEditText_strokeRadius, DEFAULT_STROKE_RADIUS)
        pwdDotColor = typedArray.getColor(R.styleable.CustomEditText_dotColor, DEFAULT_DOT_COLOR)
        pwdDotRadius =
            typedArray.getDimension(R.styleable.CustomEditText_dotRadius, DEFAULT_DOT_RADIUS)
        isDotStyle =
            typedArray.getBoolean(R.styleable.CustomEditText_dotStyle, DEFAULT_IS_DOT_STYLE)
        textSize =
            typedArray.getDimension(
                R.styleable.CustomEditText_inputTextSize,
                DEFAULT_TEXT_SIZE.toFloat()
            ).toInt()
        typedArray.recycle()
    }

    /**
     * 初始化操作
     */
    private fun init() {
        // 初始化边框画笔
        strokePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        strokePaint!!.setColor(strokeColor)
        strokePaint!!.setStrokeWidth(strokeWidth)
        strokePaint!!.setStyle(Paint.Style.STROKE)

        // 初始化圆点画笔
        pwdDotPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        pwdDotPaint!!.setStyle(Paint.Style.FILL)
        pwdDotPaint!!.setColor(pwdDotColor)
        halfStrokeWidth = strokeWidth / 2

        // 设置光标不可见
        setCursorVisible(false)
        // 设置限定最大长度
        setFilters(arrayOf<InputFilter>(LengthFilter(mCount)))
        // 设置无背景
//        setBackgroundColor(getResources().getColor(android.R.color.transparent));
        setBackgroundColor(Color.WHITE)
        setMaxLines(1)
        setFocusable(false)
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                if (onTextChangedListener != null) {
                    onTextChangedListener!!.beforeTextChanged(s, start, count, after)
                }
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (onTextChangedListener != null) {
                    onTextChangedListener!!.onTextChanged(s, start, before, count)
                }
                mCurInputCount = s.toString().length

                // 输入完成的回调
                if (mCurInputCount == mCount) {
                    if (onTextInputListener != null) {
                        onTextInputListener!!.onComplete(s.toString())
                    }
                }
            }

            override fun afterTextChanged(s: Editable) {
                if (onTextChangedListener != null) {
                    onTextChangedListener!!.afterTextChanged(s)
                }
            }
        })
    }

    protected override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHeight = h
        //        cellWidth = (mWidth - strokeWidth) / mCount;
        paddingWidth = 6f
        val allPaddingWidth = paddingWidth * (mCount - 1)
        cellWidth = (mWidth - strokeWidth * mCount - allPaddingWidth) / mCount
        //        Log.d(AppConfig.TAG, "paddingWidth---------->" + paddingWidth);
//        Log.d(AppConfig.TAG, "allPaddingWidth---------->" + allPaddingWidth);
//        Log.d(AppConfig.TAG, "cellWidth---------->" + cellWidth);
    }

    protected override fun onDraw(canvas: Canvas) {
//        super.onDraw(canvas);
        drawRect(canvas)
        //        drawHorizontalStroke(canvas);
//        drawVerticalDivider(canvas);
        drawPwdDot(canvas)
    }

    private fun drawPwdDot(canvas: Canvas) {
        val inputText: String = this.getText().toString()
        if (inputText != "") {
            for (i in 1..mCurInputCount) {
                pwdDotPaint?.let {
                    if (
                        isDotStyle
                    ) {
                        canvas.drawCircle(
                            halfStrokeWidth + cellWidth / 2 + (cellWidth + paddingWidth + strokeWidth) * (i - 1),
                            (mHeight / 2).toFloat(),
                            pwdDotRadius,
                            it
                        )
                    } else {
                        it.textSize = textSize.toFloat()
                        //textSizeToDp!!) / 2.4f 这个是看着调的居中对齐
                        val x: Float =
                            halfStrokeWidth + (cellWidth / 2 - ((textSizeToDp!!) / 2.4f)) + (cellWidth + paddingWidth + strokeWidth) * (i - 1)
                        canvas.drawText(
                            inputText.get(i - 1).toString(),
                            x,
                            ((mHeight / 2) + ((textSizeToDp!!) / 2)),
                            it
                        )
                    }
                }

            }
        }

    }

    // 绘制矩形方块
    private fun drawRect(canvas: Canvas) {
        if (mCount == 1) {
            val rectF = RectF(
                halfStrokeWidth, halfStrokeWidth, mWidth - halfStrokeWidth,
                mHeight - halfStrokeWidth
            )
            strokePaint?.let { canvas.drawRoundRect(rectF, strokeRadius, strokeRadius, it) }
        } else {
            for (i in 0 until mCount) {
                // 画框框 左上，右下
                var left: Float
                left = if (i == 0) {
                    halfStrokeWidth
                } else {
                    halfStrokeWidth * (2 * i) + cellWidth * i + paddingWidth * i
                }
                val top = strokeWidth / 2 // 就是halfStrokeWidth，语法不允许int x=0;y=x;
                val right = halfStrokeWidth * (2 * i + 1) + cellWidth * (i + 1) + paddingWidth * i
                val bottom = mHeight - halfStrokeWidth
                val rectF = RectF(
                    left,
                    top,
                    right,
                    bottom
                )
                strokePaint?.let { canvas.drawRoundRect(rectF, strokeRadius, strokeRadius, it) }
            }
        }
    }

    // 绘制竖直方向分割线
    private fun drawVerticalDivider(canvas: Canvas) {
        if (mCount == 1) {
            return
        }
        for (i in 1 until mCount) {
            strokePaint?.let {
                canvas.drawLine(
                    halfStrokeWidth + cellWidth * i,
                    halfStrokeWidth,
                    halfStrokeWidth + cellWidth * i,
                    mHeight - halfStrokeWidth,
                    it
                )
            }

//            // 第一條 line+cell*+padding*(i-1)
//            canvas.drawLine(halfStrokeWidth + cellWidth * i + paddingWidth * (i - 1),
//                    halfStrokeWidth,
//                    halfStrokeWidth + cellWidth * i + paddingWidth * (i - 1),
//                    mHeight - halfStrokeWidth,
//                    strokePaint);
//
//            // 第二條 line+cell*+line*i+padding*i
//            canvas.drawLine(halfStrokeWidth + cellWidth * i + paddingWidth * i,
//                    halfStrokeWidth,
//                    halfStrokeWidth + cellWidth * i + paddingWidth * i,
//                    mHeight - halfStrokeWidth,
//                    strokePaint);
        }
    }

    // 绘制水平方向分割线
    private fun drawHorizontalStroke(canvas: Canvas) {
        if (style == STYLE_RECTANGLE) {
            strokePaint?.let {
                canvas.drawRect(
                    halfStrokeWidth, halfStrokeWidth, mWidth - halfStrokeWidth,
                    mHeight - halfStrokeWidth, it
                )
            }
        } else {
            val rectF = RectF(
                halfStrokeWidth, halfStrokeWidth, mWidth - halfStrokeWidth,
                mHeight - halfStrokeWidth
            )
            strokePaint?.let { canvas.drawRoundRect(rectF, strokeRadius, strokeRadius, it) }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_ENTER) {
            false
        } else super.onKeyDown(keyCode, event)
    }

    private fun dp2Px(dpValue: Int): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dpValue.toFloat(),
            getResources().getDisplayMetrics()
        )
    }

    interface OnTextChangedListener {
        fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int)
        fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)
        fun afterTextChanged(s: Editable?)
    }

    interface OnTextInputListener {
        fun onComplete(result: String?)
    }

    private var onTextInputListener: OnTextInputListener? = null
    fun setOnTextInputListener(onTextInputListener: OnTextInputListener?) {
        this.onTextInputListener = onTextInputListener
    }

    private var onTextChangedListener: OnTextChangedListener? = null
    fun addTextChangedListener(listener: OnTextChangedListener?) {
        onTextChangedListener = listener
    }
}


