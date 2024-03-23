package org.spartaa3.movietogather.domain.member.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class TestController {

    @GetMapping("home")
    fun home(): String {
        return "home"
    }


}