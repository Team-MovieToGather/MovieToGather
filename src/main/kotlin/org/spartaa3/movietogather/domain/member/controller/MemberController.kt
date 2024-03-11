package org.spartaa3.movietogather.domain.member.controller

import org.spartaa3.movietogather.domain.member.dto.LoginResponse
import org.spartaa3.movietogather.domain.member.service.MemberService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.status
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/members")
class MemberController(
    private val customOAuth2MemberService: CustomOAuth2MemberService,
    private val memberService: MemberService
) {

    @GetMapping
    fun getMemberInfo(
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<MemberInfoResponse>{
        return status(HttpStatus.OK)
            .body(memberService.getMemberInfo(userPrincipal.id))
    }

    @PutMapping("/update")
    fun update(
        @RequestBody request: UpdateMemberInfoRequest,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<MemberInfoResponse>{
        return status(HttpStatus.OK)
            .body(memberService.updateMemberInfo(userPrincipal.id, request))
    }

    @GetMapping("/info")
    fun getUserInfo(@AuthenticationPrincipal userDetails: UserDetails): String {
        return userDetails.username
    }

}