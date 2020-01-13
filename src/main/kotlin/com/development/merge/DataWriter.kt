package com.development.merge

import java.io.OutputStream
import java.io.OutputStreamWriter

/**
 * Class is responsible for data output
 */
class DataWriter(outputStream : OutputStream) {

    val outputStreamWriter = OutputStreamWriter(outputStream)

    fun write(user : User) {
        outputStreamWriter.write(user.name + "-> " + user.mails.joinToString(", ") + "\n")
    }

    fun flush() {
        outputStreamWriter.flush()
    }
}