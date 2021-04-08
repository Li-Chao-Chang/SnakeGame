package com.ivanli.snake

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.concurrent.fixedRateTimer
import kotlin.random.Random

class SnakeViewModel : ViewModel(){
    private lateinit var applePos: Position
    var body = MutableLiveData<List<Position>>()
    var apple = MutableLiveData<Position>()
    var score = MutableLiveData<Int>()
    var gameState = MutableLiveData<GameState>()
    private var snakeBody = mutableListOf<Position>()
    private var direction = Direction.LEFT
    private var point : Int = 0

    @ExperimentalStdlibApi
    fun start(){
        score.postValue(point)
        snakeBody.apply {
            add(Position(10,10))
            add(Position(11,10))
            add(Position(12,10))
            add(Position(13,10))
        }.also {
            body.value = it
        }
        generateApple()
        //速度調整,越小,更新越快
        fixedRateTimer("timer",true,500,500){
            val pos = snakeBody.first().copy().apply {
                when(direction){
                    Direction.LEFT -> x--
                    Direction.RIGHT -> x++
                    Direction.UP -> y--
                    Direction.DOWN -> y++
                }
                if(snakeBody.contains(this) || x < 0 || x >= 20 || y < 0 || y >= 20){
                    cancel()
                    gameState.postValue(GameState.GAMEOVER)
                }
            }
            snakeBody.add(0,pos)
            if(pos != applePos){
                snakeBody.removeLast()
            }else{
                generateApple()
                point += 100
                score.postValue(point)
            }
            body.postValue(snakeBody)
        }

    }
    //蘋果出現
    fun generateApple(){
        val allApple = mutableListOf<Position>().apply {
            for(i in 1..19){
                for (y in 1..19){
                    add(Position(i,y))
                }
            }
        }
        allApple.removeAll(snakeBody)
        allApple.shuffle() //亂數
        //applePos = Position(Random.nextInt(20), Random.nextInt(20))
        applePos = allApple[0]
        apple.postValue(applePos)
    }
    @ExperimentalStdlibApi
    fun reset(){
        point = 0
        snakeBody.clear()
        body.postValue(snakeBody)
        direction = Direction.LEFT
        start()
    }
    fun move(dir: Direction){
        direction = dir
    }
}
data class Position(var x :Int,var y :Int)

enum class Direction{
    UP,DOWN,LEFT,RIGHT
}

enum class GameState{
    ONGOING,GAMEOVER
}