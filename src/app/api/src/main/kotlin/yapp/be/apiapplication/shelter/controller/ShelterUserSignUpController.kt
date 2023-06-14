package yapp.be.apiapplication.shelter.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
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
    @PostMapping("/sign-up")
    fun signUpWithEssentialInfo(
        req: SignUpWithEssentialInfoRequest
    ): ResponseEntity<SignUpWithEssentialInfoResponseDto> {
        val reqDto = req.toDto()
        val resDto = shelterSignUpApplicationService.signUpWithEssentialInfo(reqDto)

        return ResponseEntity.ok(resDto)
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/{userId}/additional-info")
    fun signUpWithAdditionalInfo(
        @PathVariable userId: Long,
        req: SignUpWithAdditionalInfoRequest
    ): ResponseEntity<SignUpWithAdditionalInfoResponseDto> {
        val reqDto = req.toDto()
        val resDto = shelterSignUpApplicationService.signUpWithAdditionalInfo(
            userId = userId,
            reqDto = reqDto
        )

        return ResponseEntity.ok(resDto)
    }
}
