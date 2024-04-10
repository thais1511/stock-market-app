package tqthai.demo.stockapplication.data.csv

import java.io.InputStream

interface CSVParser<T> {
    suspend fun parse(byteStream: InputStream): List<T>
}