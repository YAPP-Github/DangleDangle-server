package yapp.be.apiapplication.shelter.controller

import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import yapp.be.apiapplication.system.security.resolver.VolunteerAuthentication
import yapp.be.apiapplication.system.security.resolver.VolunteerAuthenticationInfo

@RestController
@RequestMapping("/v1/shelter")
class ShelterController {

    @GetMapping("/{shelterId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
        summary = "보호소 조회"
    )
    fun editShelterProfileImage(
        @PathVariable shelterId: Long,
        @VolunteerAuthentication volunteerInfo: VolunteerAuthenticationInfo
    ): ResponseEntity<String> {
        println("${volunteerInfo.volunteerId} ${volunteerInfo.email.value}")
        return ResponseEntity.ok("Hello")
    }
}
