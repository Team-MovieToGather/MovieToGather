package org.spartaa3.movietogather.domain.member.controller

import org.spartaa3.movietogather.domain.member.dto.LoginResponse
import org.spartaa3.movietogather.domain.member.service.MemberService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.status
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

//@RestController
//@RequestMapping("/members")
//class MemberController(
//    private val memberService: MemberService
//){
//
//    @PostMapping("/sociallogin")
//    fun socialLogin(@AuthenticationPrincipal oAuth2User: OAuth2User): ResponseEntity<LoginResponse> {
//        return status(HttpStatus.OK)
//            .body(memberService.socialLogin(oAuth2User))
//    }
//
//
//}