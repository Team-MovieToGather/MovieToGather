package org.spartaa3.movietogather.domain.member.controller


import org.spartaa3.movietogather.domain.member.dto.MemberInfoResponse
import org.spartaa3.movietogather.domain.member.dto.UpdateMemberInfoRequest
import org.spartaa3.movietogather.domain.member.service.MemberService
import org.spartaa3.movietogather.infra.security.jwt.UserPrincipal
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.status
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/members")
class MemberController(
    private val memberService: MemberService
) {

    @GetMapping
    fun getMemberInfo(
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<MemberInfoResponse> {
        return status(HttpStatus.OK)
            .body(memberService.getMemberInfo(userPrincipal))
    }

    @PutMapping("/update")
    fun update(
        @RequestBody request: UpdateMemberInfoRequest,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<MemberInfoResponse> {
        return status(HttpStatus.OK)
            .body(memberService.updateMemberInfo(userPrincipal, request))
    }

    @GetMapping("/info")
    fun getUserInfo(@AuthenticationPrincipal userDetails: UserDetails): String {
        return userDetails.username
    }

}