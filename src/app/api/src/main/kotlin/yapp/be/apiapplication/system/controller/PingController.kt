package yapp.be.apiapplication.system.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PingController {
    @GetMapping("/ping")
    fun ping(): String {
        return "pong"
    }
}
