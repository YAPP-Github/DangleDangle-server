package yapp.be.apiapplication.shelter

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import yapp.be.apiapplication.shelter.model.SignUpWithAdditionalInfoRequest
import yapp.be.apiapplication.shelter.model.SignUpWithEssentialInfoRequest

@RestController
@RequestMapping("/v1/shelter/user")
class ShelterUserSignUpController {

    @PostMapping("/sign-up")
    fun signUpWithEssentialInfo(
        req: SignUpWithEssentialInfoRequest
    ) {
    }

    @PostMapping("/{userId}/additional-info")
    fun signUpWithAdditionalInfo(
        @PathVariable userId: Long,
        req: SignUpWithAdditionalInfoRequest
    ) {
    }
}
