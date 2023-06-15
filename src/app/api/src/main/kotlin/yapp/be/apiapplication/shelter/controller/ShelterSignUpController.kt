package yapp.be.apiapplication.shelter.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import yapp.be.apiapplication.shelter.controller.model.SignUpWithEssentialInfoRequest
import yapp.be.apiapplication.shelter.service.ShelterSignUpApplicationService
import yapp.be.apiapplication.shelter.service.model.SignUpWithEssentialInfoResponseDto

@RestController
@Tag(name = "보호소 회원가입 api")
@RequestMapping("/v1/shelter")
class ShelterSignUpController(
    val shelterSignUpApplicationService: ShelterSignUpApplicationService
) {
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/sign-up")
    @Operation(
        summary = "보호소 사용자 회원가입",
    )
    fun signUpWithEssentialInfo(
        @RequestBody
        req: SignUpWithEssentialInfoRequest
    ): ResponseEntity<SignUpWithEssentialInfoResponseDto> {
        val reqDto = req.toDto()
        val resDto = shelterSignUpApplicationService.signUpWithEssentialInfo(reqDto)

        return ResponseEntity.ok(resDto)
    }
}
