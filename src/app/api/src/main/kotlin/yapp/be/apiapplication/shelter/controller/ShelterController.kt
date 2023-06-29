package yapp.be.apiapplication.shelter.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import yapp.be.apiapplication.shelter.service.shelter.ShelterApplicationService
import yapp.be.apiapplication.shelter.service.shelter.model.BookMarkShelterRequestDto
import yapp.be.apiapplication.shelter.service.shelter.model.BookMarkShelterResponseDto
import yapp.be.apiapplication.system.security.resolver.VolunteerAuthentication
import yapp.be.apiapplication.system.security.resolver.VolunteerAuthenticationInfo

@Tag(name = "보호소 api")
@RestController
@RequestMapping("/v1/shelter")
class ShelterController(
    private val shelterApplicationService: ShelterApplicationService
) {

    @GetMapping("/{shelterId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
        summary = "보호소 조회"
    )
    fun getShelterInfo(
        @PathVariable shelterId: Long,
        @VolunteerAuthentication volunteerInfo: VolunteerAuthenticationInfo
    ): ResponseEntity<String> {
        return ResponseEntity.ok("Hello")
    }

    @PostMapping("/{shelterId}/bookmark")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
        summary = "보호소 즐겨찾기 API (추가/삭제 겸용)"
    )
    fun bookmarkShelter(
        @PathVariable shelterId: Long,
        @VolunteerAuthentication volunteerInfo: VolunteerAuthenticationInfo
    ): ResponseEntity<BookMarkShelterResponseDto> {
        val reqDto = BookMarkShelterRequestDto(
            shelterId = shelterId,
            volunteerId = volunteerInfo.volunteerId
        )
        val resDto = shelterApplicationService.bookMarkShelter(reqDto)

        return ResponseEntity.ok(resDto)
    }
}
