package com.ivanli.snake

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class GameView(context: Context,attributeSet: AttributeSet) : View(context,attributeSet){
    var snakeBody : List<Position>? = null //身體集合
    var apple : Position? = null //蘋果位置
    var size = 0 //長度
    var gap = 3//蛇的身間格
    private val paint = Paint().apply { color = Color.BLACK }
    private val paintApple = Paint().apply { color = Color.RED }
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.run {
            snakeBody?.forEach{
                drawRect((it.x*size).toFloat()+gap,
                    (it.y*size).toFloat()+gap,
                    ((it.x+1)*size).toFloat()-gap,
                    ((it.y+1)*size).toFloat()-gap,paint)
            }
            apple?.run {
                drawRect((x*size).toFloat()+gap,
                    (y*size).toFloat()+gap,
                    ((x+1)*size).toFloat()-gap,
                    ((y+1)*size).toFloat()-gap,paintApple)
            }
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        size = width / 20
    }
}
