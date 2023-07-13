package yapp.be.apiapplication.auth.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
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
import yapp.be.apiapplication.auth.controller.model.ShelterSignUpCheckDuplicationType
import yapp.be.apiapplication.auth.controller.model.SignUpWithEssentialInfoRequest
import yapp.be.apiapplication.auth.service.ShelterAuthApplicationService
import yapp.be.apiapplication.auth.service.model.CheckShelterUserSignUpDuplicationResponseDto
import yapp.be.apiapplication.auth.service.model.LoginShelterUserResponseDto
import yapp.be.apiapplication.auth.service.model.SignUpShelterWithEssentialInfoResponseDto
import yapp.be.apiapplication.system.exception.ApiExceptionType
import yapp.be.exceptions.CustomException
import yapp.be.model.vo.Email

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
        @RequestBody @Valid
        req: LoginShelterUserRequest
    ): ResponseEntity<LoginShelterUserResponseDto> {
        val reqDto = req.toDto()
        val resDto = shelterAuthApplicationService.login(reqDto)

        return ResponseEntity.ok(resDto)
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/exist")
    @Operation(
        summary = "보호소 사용자 입력값 중복여부 체크",
    )
    fun checkShelterUserEmailDuplicate(
        @RequestParam value: String,
        @RequestParam type: ShelterSignUpCheckDuplicationType
    ): ResponseEntity<CheckShelterUserSignUpDuplicationResponseDto> {

        val resDto = when (type) {
            ShelterSignUpCheckDuplicationType.EMAIL ->
                shelterAuthApplicationService.checkIsShelterUserEmailExist(
                    email = Email(value)
                )
            ShelterSignUpCheckDuplicationType.NAME ->
                shelterAuthApplicationService.checkIsShelterUserNameExist(value)
            else -> throw CustomException(ApiExceptionType.RUNTIME_EXCEPTION, "올바르지 않은 입력 입니다. type = $type")
        }
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
