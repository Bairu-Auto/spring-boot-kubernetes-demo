package com.example.demo.services

import io.qameta.allure.Description
import io.qameta.allure.Feature
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class TestServiceTest(
    @Autowired private val testService: TestService
) {

    companion object {
        private const val EXPECTED_RESULT = "Hello World!"
    }

    @Test
    @Feature("Sample Feature")
    @Description("This is a sample test for Allure integration.")
    fun `test 1`() {
        val result = testService.returnStaticWord()

        assertEquals(EXPECTED_RESULT, result)
    }
}
