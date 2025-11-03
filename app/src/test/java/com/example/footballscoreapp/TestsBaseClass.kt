package com.example.footballscoreapp

import android.app.Application
import com.example.footballscoreapp.di.appModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.GlobalContext.stopKoin
import org.koin.test.KoinTest
import org.mockito.Mockito

open class BaseTestClass : KoinTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    private val testModulesList = appModule + listOf(
        ApiServiceMock().module,
        MatchesDbMock().module,
    )

    @Before
    fun before() {
        startKoin {
            androidContext(Mockito.mock(Application::class.java))
            modules(testModulesList)
        }
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun after() {
        stopKoin()
        Dispatchers.resetMain()
    }

    fun test(
        invoke: suspend () -> Unit
    ) {
        runBlocking {
            invoke()
        }
    }

}