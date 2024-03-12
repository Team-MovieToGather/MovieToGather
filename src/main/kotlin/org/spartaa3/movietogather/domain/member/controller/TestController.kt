package org.spartaa3.movietogather.domain.member.controller


import org.spartaa3.movietogather.domain.member.oauth2.info.MemberInfo
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class TestController{

    @GetMapping("home")
    fun home(): String {
        return "home"
    }


}