package com.development.merge

import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream

class DataWriterTest {

    @Test
    fun testEmpty() {
        val outputStream = ByteArrayOutputStream()
        val userWriter = DataWriter(outputStream)
        userWriter.flush()
        assert(outputStream.toByteArray().isEmpty())
    }

    @Test
    fun testOneUserOneMail() {
        val outputStream = ByteArrayOutputStream()
        val userWriter = DataWriter(outputStream)
        userWriter.write(User("user1", hashSetOf("mail1")))
        userWriter.flush()
        assert(outputStream.toString("UTF-8").equals("user1-> mail1\n"))
    }

    @Test
    fun testOneUserTwoMails() {
        val outputStream = ByteArrayOutputStream()
        val userWriter = DataWriter(outputStream)
        userWriter.write(User("user1", hashSetOf("mail1", "mail2")))
        userWriter.flush()
        val result = outputStream.toString("UTF-8")
        assert(result.equals("user1-> mail1, mail2\n") || result.equals("user1-> mail2, mail1\n"))
    }

    @Test
    fun testOneWithoutMails() {
        val outputStream = ByteArrayOutputStream()
        val userWriter = DataWriter(outputStream)
        userWriter.write(User("user1", hashSetOf()))
        userWriter.flush()
        assert(outputStream.toString("UTF-8").equals("user1-> \n"))
    }

    @Test
    fun testTwoUsers() {
        val outputStream = ByteArrayOutputStream()
        val userWriter = DataWriter(outputStream)
        userWriter.write(User("user1", hashSetOf("mail1")))
        userWriter.write(User("user2", hashSetOf("mail2")))
        userWriter.flush()
        assert(outputStream.toString("UTF-8").equals(
                "user1-> mail1\n" +
                        "user2-> mail2\n"
        ))
    }
}