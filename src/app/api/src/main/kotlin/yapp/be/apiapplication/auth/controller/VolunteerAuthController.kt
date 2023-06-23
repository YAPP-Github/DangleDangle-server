package yapp.be.apiapplication.auth.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import yapp.be.apiapplication.auth.controller.model.LoginVolunteerRequest
import yapp.be.apiapplication.auth.controller.model.VolunteerSignUpRequest
import yapp.be.apiapplication.auth.service.VolunteerAuthApplicationService
import yapp.be.apiapplication.auth.service.model.CheckUserNicknameExistResponseDto
import yapp.be.apiapplication.auth.service.model.LoginVolunteerResponseDto
import yapp.be.apiapplication.auth.service.model.SignUpUserWithEssentialInfoResponseDto

@RestController
@Tag(name = "개인 회원 회원가입 api")
@RequestMapping("/v1/auth/volunteer")
class VolunteerAuthController(
    private val volunteerAuthApplicationService: VolunteerAuthApplicationService,
) {
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/register")
    @Operation(
        summary = "봉사자 회원가입",
    )
    fun register(
        @RequestBody req: VolunteerSignUpRequest,
    ): ResponseEntity<SignUpUserWithEssentialInfoResponseDto> {
        val reqDto = req.toDto()
        val resDto = volunteerAuthApplicationService.register(reqDto)
        return ResponseEntity.ok(resDto)
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/nickname/exist")
    @Operation(
        summary = "봉사자 닉네임 중복여부 체크",
    )
    fun checkUserNicknameDuplicate(
        @RequestParam nickname: String
    ): ResponseEntity<CheckUserNicknameExistResponseDto> {
        val resDto = volunteerAuthApplicationService.checkIsUserNicknameExist(nickname)
        return ResponseEntity.ok(resDto)
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/token")
    @Operation(
        summary = "봉사자 토큰 발급",
    )
    fun issueUserToken(
        @RequestBody req: LoginVolunteerRequest,
    ): ResponseEntity<LoginVolunteerResponseDto> {
        val reqDto = req.toDto()
        val resDto = volunteerAuthApplicationService.issueToken(reqDto)
        return ResponseEntity.ok(resDto)
    }
}
