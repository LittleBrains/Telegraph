package ru.littlebrains.telegraph.api

class RequestException : Exception {

    var isTimeoutException: Boolean = false
    var isUnknownHostException: Boolean = false

    constructor(message: String) : super(message) {}

    constructor(message: String, isUnknownHostException: Boolean, isTimeoutException: Boolean) : super(message) {
        this.isUnknownHostException = isUnknownHostException
        this.isTimeoutException = isTimeoutException
    }

}