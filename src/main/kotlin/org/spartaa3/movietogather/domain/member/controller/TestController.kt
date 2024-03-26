package org.spartaa3.movietogather.domain.member.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping

@Controller
class TestController {

    @GetMapping("home")
    fun home(): String {
        return "home"
    }


}