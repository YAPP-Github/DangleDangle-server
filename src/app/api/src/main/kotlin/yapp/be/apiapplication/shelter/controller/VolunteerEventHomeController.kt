package yapp.be.apiapplication.shelter.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import yapp.be.apiapplication.shelter.controller.model.VolunteerEventHomeRequest
import yapp.be.apiapplication.shelter.service.VolunteerEventHomeApplicationService
import yapp.be.apiapplication.shelter.service.model.GetVolunteerEventHomeRequestDto
import yapp.be.apiapplication.system.security.resolver.VolunteerAuthentication
import yapp.be.apiapplication.system.security.resolver.VolunteerAuthenticationInfo
import yapp.be.domain.model.dto.VolunteerSimpleVolunteerEventDto

@Tag(name = "봉사 이벤트 홈화면 api")
@RestController
@RequestMapping("/v1/volunteer-event")
class VolunteerEventHomeController(
    private val volunteerEventHomeApplicationService: VolunteerEventHomeApplicationService,
) {
    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    @Operation(
        summary = "봉사이벤트 홈페이지 조회"
    )
    fun getVolunteerEvents(
        @VolunteerAuthentication volunteerInfo: VolunteerAuthenticationInfo?,
        @RequestBody @Valid req: VolunteerEventHomeRequest,
    ): ResponseEntity<List<VolunteerSimpleVolunteerEventDto>> {
        val reqDto = GetVolunteerEventHomeRequestDto(
            volunteerId = volunteerInfo?.volunteerId,
            category = req.category,
            status = req.status,
            from = req.from.atStartOfDay(),
            to = req.to.atTime(23, 59, 59),
            longitude = req.longitude,
            latitude = req.latitude,
            address = req.address,
            isFavorite = req.isFavorite,
        )

        val resDto = volunteerEventHomeApplicationService.getVolunteerEventHome(reqDto)

        return ResponseEntity.ok(resDto)
    }
}
