package com.example.checkconnectionmodule

class Response {
    var detected : Boolean = false
    var code : Int = 0

    constructor(code: Int, isDetected: Boolean){
        this.code = code
        this.detected = isDetected
    }
}