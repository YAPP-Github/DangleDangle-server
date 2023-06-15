package yapp.be.apiapplication.shelter.controller

import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import yapp.be.apiapplication.shelter.controller.model.SignUpWithAdditionalInfoRequest
import yapp.be.apiapplication.shelter.controller.model.SignUpWithEssentialInfoRequest
import yapp.be.apiapplication.shelter.service.ShelterSignUpApplicationService
import yapp.be.apiapplication.shelter.service.model.SignUpWithAdditionalInfoResponseDto
import yapp.be.apiapplication.shelter.service.model.SignUpWithEssentialInfoResponseDto

@RestController
@RequestMapping("/v1/shelter/user")
class ShelterUserSignUpController(
    val shelterSignUpApplicationService: ShelterSignUpApplicationService
) {

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/sign-up/essential-info")
    @Operation(
        summary = "보호소 사용자 회원가입 (필수정보)",
    )
    fun signUpWithEssentialInfo(
        req: SignUpWithEssentialInfoRequest
    ): ResponseEntity<SignUpWithEssentialInfoResponseDto> {
        val reqDto = req.toDto()
        val resDto = shelterSignUpApplicationService.signUpWithEssentialInfo(reqDto)

        return ResponseEntity.ok(resDto)
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/sign-up/additional-info")
    @Operation(
        summary = "보호소 사용자 회원가입 (추가정보)",
    )
    fun signUpWithAdditionalInfo(
        req: SignUpWithAdditionalInfoRequest
    ): ResponseEntity<SignUpWithAdditionalInfoResponseDto> {
        val reqDto = req.toDto()
        val resDto = shelterSignUpApplicationService.signUpWithAdditionalInfo(
            reqDto = reqDto
        )

        return ResponseEntity.ok(resDto)
    }
}
