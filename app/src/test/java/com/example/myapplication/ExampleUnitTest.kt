package com.example.myapplication

import kotlinx.coroutines.*
import kotlinx.coroutines.test.runTest
//import kotlinx.coroutines.test.runTest
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testA() = runBlocking {
//    fun testA() = runTest {
        repeat(100) {
            val list = mutableListOf<Int>()
            val threadIds = mutableSetOf<Long>()

            coroutineScope {
                println("In coroutineScope")
                repeat(100) { index ->
                    launch(Dispatchers.Default) {
                        println("In launch ${index} before delay")
                        delay(100)
                        threadIds.add(Thread.currentThread().id)
                        println("In launch ${index} add")
                        synchronized(list) {
                            list.add(index)
                        }
                    }
                }
            }

//            testScheduler.runCurrent()

            println("Empty = ${list.isEmpty()}")
            println(list.joinToString())

            repeat(100) {
                assertTrue(list.contains(it))
            }

            println("run $it, ids = ${threadIds.joinToString()}")
        }
    }
}
