package yapp.be.apiapplication.auth.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import yapp.be.apiapplication.auth.controller.model.LoginShelterUserRequest
import yapp.be.apiapplication.auth.controller.model.SignUpWithEssentialInfoRequest
import yapp.be.apiapplication.auth.service.ShelterAuthApplicationService
import yapp.be.apiapplication.auth.service.model.CheckShelterUserEmailExistResponseDto
import yapp.be.apiapplication.auth.service.model.LoginShelterUserResponseDto
import yapp.be.apiapplication.auth.service.model.SignUpShelterWithEssentialInfoResponseDto
import yapp.be.model.Email

@RestController
@Tag(name = "보호소 회원가입/로그인 api")
@RequestMapping("/v1/auth/shelter")
class ShelterAuthController(
    val shelterAuthApplicationService: ShelterAuthApplicationService
) {

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    @Operation(
        summary = "보호소 사용자 로그인",
    )
    fun loginShelterUser(
        @RequestBody
        req: LoginShelterUserRequest
    ): ResponseEntity<LoginShelterUserResponseDto> {
        val reqDto = req.toDto()
        val resDto = shelterAuthApplicationService.login(reqDto)

        return ResponseEntity.ok(resDto)
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/email/exist")
    @Operation(
        summary = "보호소 사용자 이메일 중복여부 체크",
    )
    fun checkShelterUserEmailDuplicate(
        @RequestParam email: String
    ): ResponseEntity<CheckShelterUserEmailExistResponseDto> {
        val resDto = shelterAuthApplicationService.checkIsShelterUserEmailExist(
            email = Email(email)
        )
        return ResponseEntity.ok(resDto)
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/register")
    @Operation(
        summary = "보호소 사용자 회원가입",
    )
    fun signUpWithEssentialInfo(
        @RequestBody
        req: SignUpWithEssentialInfoRequest
    ): ResponseEntity<SignUpShelterWithEssentialInfoResponseDto> {
        val reqDto = req.toDto()
        val resDto = shelterAuthApplicationService.signUpWithEssentialInfo(reqDto)

        return ResponseEntity.ok(resDto)
    }
}
