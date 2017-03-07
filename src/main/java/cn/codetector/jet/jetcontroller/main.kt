package cn.codetector.jet.jetcontroller

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch

/**
 * Created by Codetector on 2017/3/7.
 * Project Classroom
 */
fun main(args: Array<String>) {
    val start = System.currentTimeMillis()
    println("Start")

    launch(CommonPool) {
        delay(1000)
        println("Hello")
    }


    Thread.sleep(2000)
    println("Stop ${System.currentTimeMillis() - start}")
}