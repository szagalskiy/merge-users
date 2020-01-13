package com.development.merge

import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream

class MergeTest {

    @Test
    fun testEmpty() {
        val inputData = "\n\n"
        val outputStream = ByteArrayOutputStream()
        merge(inputData.byteInputStream(), outputStream)
        assert(outputStream.toString("UTF-8").isEmpty())
    }

    @Test
    fun testWork() {
        val inputData =
                "user1-> mail1, mail2\n" +
                        "user2-> mail3, mail4\n" +
                        "user3-> mail2, mail4\n" +
                        "user4-> mail5\n\n"
        val outputData = "user1-> mail4, mail3, mail2, mail1\nuser4-> mail5\n"

        val outputStream = ByteArrayOutputStream()
        merge(inputData.byteInputStream(), outputStream)
        val result = outputStream.toString("UTF-8")
        assert(result.equals(outputData))
        println(result)
    }
}