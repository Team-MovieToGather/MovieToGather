package org.spartaa3.movietogather.domain.member.controller


import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.spartaa3.movietogather.domain.member.oauth2.info.MemberInfo
import org.spartaa3.movietogather.domain.member.service.MemberService
import org.spartaa3.movietogather.infra.security.jwt.UserPrincipal
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class TestController(
    private val memberService: MemberService
) {
    @GetMapping("/")
    fun home(
        model: Model,
        userPrincipal: UserPrincipal
    ): String {
        val memberInfo = memberService.getMemberInfo(userPrincipal.id)
        model.addAttribute("memberInfo", memberInfo)
        return "home"
    }

    @GetMapping("/logout")
    fun logout(
        request: HttpServletRequest,
        response: HttpServletResponse
    ): String {
        SecurityContextHolder.getContext().authentication = null
        val cookies = request.cookies
        if (cookies != null) {
            for (cookie in cookies) {
                cookie.maxAge = 0
                response.addCookie(cookie)
            }
        }
        return "redirect:/login"
    }

}