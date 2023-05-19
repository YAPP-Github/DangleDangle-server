package yapp.be.apiapplication

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController {
    @GetMapping("/")
    fun ping(): String {
        return "pong"
    }
}
