package com.shiki.koko.amap.widget

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import com.shiki.koko.amap.R

/**
 * 楼层控制控件
 */

class IndoorFloorSwitchView : ScrollView {

    //子视图容器
    private lateinit var llViews: LinearLayout
    private val items: ArrayList<String> = arrayListOf()

    //滑动方向
    private val scrollDirection = -1
    private val SCROLL_DIRECTION_UP = 0
    private val SCROLL_DIRECTION_DOWN = 1

    //绘制数据
    private var viewWidth = 0
    private lateinit var selectBitmap: Bitmap
    private val backGroundColor = Color.parseColor("#eeffffff")
    private val strokeColor = Color.parseColor("#44383838")
    private val strokeWidth = 4 // 边框宽度

    //手势滑动
    private lateinit var scrollerTask: Runnable
    private val offset = 1 // 偏移量在最前面和最后面补全
    private var displayItemCount = 0 // 每页显示的数
    private var selectedIndex = 1
    private var initialY = 0
    private var itemHeight = 0
    private val newCheck = 50

    constructor(context: Context?) : super(context) {
        initVariable()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initVariable()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initVariable()
    }

    private fun initVariable() {
        this.isVerticalScrollBarEnabled = false
        selectBitmap = BitmapFactory.decodeResource(context!!.resources, R.drawable.map_indoor_select)

        llViews = LinearLayout(context)
        llViews.orientation = LinearLayout.VERTICAL
        this.addView(llViews)

        //初始化滑动任务
        scrollerTask = Runnable {
            val newY = scrollY
            if (initialY - newY == 0) { // stopped
                val remainder = initialY % itemHeight
                val divided = initialY / itemHeight
                if (remainder == 0) {
                    selectedIndex = divided + offset
                    onSelectCallBack()
                } else {
                    if (remainder > itemHeight / 2) {
                        post {
                            smoothScrollTo(0, initialY - remainder + itemHeight)
                            selectedIndex = divided + offset + 1
                            onSelectCallBack()
                        }
                    } else {
                        post {
                            smoothScrollTo(0, initialY - remainder)
                            selectedIndex = divided + offset
                            onSelectCallBack()
                        }
                    }
                }
            } else {
                initialY = scrollY
                postDelayed(
                    scrollerTask,
                    newCheck.toLong()
                )
            }
        }
    }

    /**
     * 设置显示的内容
     */
    fun setItems(strings: Array<String>) {
        items.clear()
        items.add("") // 前面补
        strings.forEach { items.add(it) }
        items.add("") //后面补
        initData()
    }

    private fun initData() {
        if (items.size == 0) {
            return
        }
        llViews.removeAllViews()
        displayItemCount = offset * 2 + 1

        for (i in items.indices.reversed()) {
            llViews.addView(createView(items[i]))
        }

        refreshItemView(0)
    }

    /**
     * 刷新item
     */
    private fun refreshItemView(y: Int) {
        var position = y / itemHeight + offset
        val remainder = y % itemHeight
        val divided = y / itemHeight

        if (remainder == 0) {
            position = divided + offset
        } else {
            if (remainder > itemHeight / 2) {
                position = divided + offset + 1
            }
        }

        val childSize: Int = llViews.childCount
        for (i in 0 until childSize) {
            val itemView = llViews.getChildAt(i) as TextView
            if (position == i) {
                itemView.setTextColor(Color.parseColor("#0288ce"))
            } else {
                itemView.setTextColor(Color.parseColor("#bbbbbb"))
            }
        }
    }

    /**
     * 创建itemView
     */
    private fun createView(item: String): TextView {
        val tv = TextView(context)
        tv.layoutParams = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        tv.isSingleLine = true
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
        tv.text = item
        tv.gravity = Gravity.CENTER
        val tp = tv.paint
        tp.isFakeBoldText = true
        val paddingH: Int = dip2px(context, 8f)
        val paddingV: Int = dip2px(context, 6f)
        tv.setPadding(paddingH, paddingV, paddingH, paddingV)
        if (0 == itemHeight) {
            itemHeight = getViewMeasuredHeight(tv)
            llViews.layoutParams = LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, itemHeight * displayItemCount
            )
            this.layoutParams = LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, itemHeight * displayItemCount
            )
        }
        return tv
    }


    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        if (ev!!.action == MotionEvent.ACTION_UP) {
            startScrollerTask()
        }
        return super.onTouchEvent(ev)
    }

    /**
     * 开始滑动任务
     */
    private fun startScrollerTask() {
        initialY = scrollY
        postDelayed(scrollerTask, newCheck.toLong())
    }


    /**
     * 手势惯性重写
     */
    override fun fling(velocityY: Int) {
        super.fling(velocityY / 3)
    }


    /**
     * 当前选中层数
     */
    private fun getSelectedIndex(): Int {
        if (items.isEmpty()) {
            return 0
        }
        val result = items.size - 1 - selectedIndex - offset
        val maxResult = Math.max(0, result)
        val itemIndex = items.size - 2 * offset
        return Math.min(itemIndex, maxResult)
    }

    /**
     * 选中回调
     */
    private fun onSelectCallBack() {
        if (null != onSelectedAction) {
            try {
                onSelectedAction?.invoke(getSelectedIndex())
            } catch (e: Throwable) {
            }
        }
    }

    /**
     * 选中回调
     */
    var onSelectedAction: ((selectedIndex: Int) -> Unit)? = null


    private fun dip2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    private fun px2dip(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }


    /**
     * 获取控件的高度，如果获取的高度为0，则重新计算尺寸后再返回高度
     *
     * @param view
     * @return
     */
    private fun getViewMeasuredHeight(view: View): Int {
        calcViewMeasure(view)
        return view.measuredHeight
    }

    /**
     * 测量控件的尺寸
     *
     * @param view
     */
    private fun calcViewMeasure(view: View) {
        val width = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
        val expandSpec = MeasureSpec.makeMeasureSpec(Int.MAX_VALUE shr 2, MeasureSpec.AT_MOST)
        view.measure(width, expandSpec)
    }


    override fun setBackground(background: Drawable) {
        if (viewWidth == 0) {
            viewWidth = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                context.display?.width ?: 0
            } else {
                (context as Activity).windowManager.defaultDisplay.width
            }
        }

        val drawable = object : Drawable() {

            override fun draw(canvas: Canvas) {
                kotlin.runCatching {
                    drawBg(canvas)
                    drawCenterLine(canvas)
                    drawStroke(canvas)
                }
            }

            override fun setAlpha(alpha: Int) = Unit
            override fun setColorFilter(colorFilter: ColorFilter?) = Unit
            override fun getOpacity(): Int = PixelFormat.UNKNOWN

            private fun drawBg(canvas: Canvas) {
                canvas.drawColor(backGroundColor)
            }

            private fun drawCenterLine(canvas: Canvas) {
                val paint = Paint()
                val src = Rect() // 图片 >>原矩
                val dst = Rect() // 屏幕 >>目标矩形

                src.left = 0
                src.top = 0
                src.right = 0 + selectBitmap.width
                src.bottom = 0 + selectBitmap.height

                dst.left = 0
                dst.top = obtainSelectedAreaBorder()[0]
                dst.right = 0 + viewWidth
                dst.bottom = obtainSelectedAreaBorder()[1]

                canvas.drawBitmap(selectBitmap, src, dst, paint)

            }

            private fun drawStroke(canvas: Canvas) {
                val mPaint = Paint()
                val rect = canvas.clipBounds
                mPaint.color = strokeColor
                mPaint.style = Paint.Style.STROKE
                mPaint.strokeWidth = strokeWidth.toFloat()
                canvas.drawRect(rect, mPaint)
            }

            /**
             * 获取选中区域的边
             */
            private fun obtainSelectedAreaBorder(): IntArray {
                val selectedAreaBorder = IntArray(2)
                selectedAreaBorder[0] = itemHeight * offset
                selectedAreaBorder[1] = itemHeight * (offset + 1)
                return selectedAreaBorder
            }
        }

        super.setBackground(drawable)
    }


}

