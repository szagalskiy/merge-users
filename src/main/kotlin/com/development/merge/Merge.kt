package com.development.merge

import java.io.InputStream
import java.io.OutputStream

/**
 * Application's entry point
 */
fun main(args : Array<String>) {
    merge(System.`in`, System.out.buffered())
}

/**
 * This method takes input data from input stream, invokes merging algorithm and
 * writes processed data to output stream
 */
fun merge(inputStream: InputStream, outputStream: OutputStream) {
    val dataReader = DataReader(inputStream)
    val merger = MergeService()
    val dataWriter = DataWriter(outputStream)
    while (dataReader.hasNext()) {
        dataReader.next()?.let(merger::add)
    }
    merger.getResult().forEach(dataWriter::write)
    dataWriter.flush()
}