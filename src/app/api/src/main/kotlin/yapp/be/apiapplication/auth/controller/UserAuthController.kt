package yapp.be.apiapplication.auth.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import yapp.be.apiapplication.auth.controller.model.UserSignUpRequest
import yapp.be.apiapplication.auth.service.UserAuthApplicationService
import yapp.be.apiapplication.auth.service.model.CheckUserNicknameExistResponseDto
import yapp.be.apiapplication.auth.service.model.SignUpUserWithEssentialInfoResponseDto

@RestController
@Tag(name = "개인 회원 회원가입 api")
@RequestMapping("/v1/auth/user")
class UserAuthController(
    private val userAuthApplicationService: UserAuthApplicationService,
) {
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/register")
    @Operation(
        summary = "회원가입",
    )
    fun register(
        @RequestBody req: UserSignUpRequest,
    ): ResponseEntity<SignUpUserWithEssentialInfoResponseDto> {
        val reqDto = req.toDto()
        val resDto = userAuthApplicationService.register(reqDto)
        return ResponseEntity.ok(resDto)
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/nickname/exist")
    @Operation(
        summary = "사용자 닉네임 중복여부 체크",
    )

    fun checkUserNicknameDuplicate(
        @RequestParam nickname: String
    ): ResponseEntity<CheckUserNicknameExistResponseDto> {
        val resDto = userAuthApplicationService.checkIsUserNicknameExist(nickname)
        return ResponseEntity.ok(resDto)
    }
}
