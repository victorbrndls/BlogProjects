package com.victorbrandalise

import io.grpc.android.AndroidChannelBuilder
import java.util.concurrent.TimeUnit

fun main() {
    val scope = CoroutineScope

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

    val book = BookOuterClass.Book.newBuilder()
        .setId(1)
        .setTitle("gRPC In-Depth")
        .build()

    val createBookRequest = BookOuterClass.CreateBookRequest.newBuilder()
        .setBook(book)
        .build()

    val stub = BookstoreGrpcKt.BookstoreCoroutineStub(channel)
    val a = stub.createBook(createBookRequest)
}