package com.tournament

import java.util.Properties
import java.io.FileInputStream

object ConfigLoader {
    private val properties = Properties()

    init {
        FileInputStream("src/main/resources/config.properties").use { properties.load(it) }
    }

    val mongoUri: String get() = properties.getProperty("mongo.uri")
    val mongoDbName: String get() = properties.getProperty("mongo.dbname")
}