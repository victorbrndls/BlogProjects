package com.victorbrandalise

import io.grpc.android.AndroidChannelBuilder
import java.util.concurrent.TimeUnit

fun main() {
    val channel = AndroidChannelBuilder
        .forAddress("127.0.0.1", 9090)
        .build()

    val channelWithRetry = AndroidChannelBuilder
        .forAddress("127.0.0.1", 9090)
        .maxRetryAttempts(10)
        .build()

    val channelWithKeepAlive = AndroidChannelBuilder
        .forAddress("127.0.0.1", 9090)
        .keepAliveTime(20, TimeUnit.SECONDS)
        .keepAliveTimeout(5, TimeUnit.SECONDS)
        .build()

    channel.getState(false)


}