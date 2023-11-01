package yapp.be.apiapplication.shelter.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import java.time.LocalDate
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import yapp.be.apiapplication.shelter.controller.model.AddVolunteerEventRequest
import yapp.be.apiapplication.shelter.controller.model.EditVolunteerEventRequest
import yapp.be.apiapplication.shelter.controller.model.ShelterHomeRequest
import yapp.be.apiapplication.shelter.service.ShelterHomeApplicationService
import yapp.be.apiapplication.shelter.service.VolunteerActivityManageApplicationService
import yapp.be.apiapplication.shelter.service.model.*
import yapp.be.apiapplication.system.security.resolver.ShelterUserAuthentication
import yapp.be.apiapplication.system.security.resolver.ShelterUserAuthenticationInfo
import yapp.be.domain.volunteerActivity.model.dto.VolunteerSimpleVolunteerActivityDto

@Tag(name = "봉사 활동 관리 api")
@RestController
@RequestMapping("/v1/shelter/admin/volunteer-event")
class VolunteerActivityManageController(
    private val volunteerActivityManageApplicationService: VolunteerActivityManageApplicationService,
    private val shelterHomeApplicationService: ShelterHomeApplicationService,
) {

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{volunteerEventId}")
    @Operation(
        summary = "봉사 활동 상세 조회"
    )
    fun getVolunteerEvent(
        @PathVariable volunteerEventId: Long,
        @ShelterUserAuthentication shelterUserInfo: ShelterUserAuthenticationInfo
    ): ResponseEntity<GetDetailVolunteerActivityResponseDto> {
        val reqDto = GetShelterUserVolunteerActivityRequestDto(
            shelterUserId = shelterUserInfo.shelterUserId,
            volunteerEventId = volunteerEventId
        )
        val resDto = volunteerActivityManageApplicationService
            .getVolunteerEvent(reqDto)

        return ResponseEntity.ok(resDto)
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    @Operation(
        summary = "봉사 활동 리스트 조회"
    )
    fun getShelterUserVolunteerEvents(
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        from: LocalDate,
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        to: LocalDate,
        @ShelterUserAuthentication shelterUserInfo: ShelterUserAuthenticationInfo
    ): ResponseEntity<GetShelterUserVolunteerEventListResponseDto> {
        val reqDto = GetShelterUserVolunteerEventListRequestDto(
            shelterUserId = shelterUserInfo.shelterUserId,
            from = from.atStartOfDay(),
            to = to.atTime(23, 59, 59)
        )

        val resDto = volunteerActivityManageApplicationService.getVolunteerEvents(reqDto)

        return ResponseEntity.ok(resDto)
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    @Operation(
        summary = "봉사 활동 추가"
    )
    fun addVolunteerEvent(
        @RequestBody @Valid req: AddVolunteerEventRequest,
        @ShelterUserAuthentication shelterUserInfo: ShelterUserAuthenticationInfo
    ): ResponseEntity<AddVolunteerEventResponseDto> {
        val reqDto = req.toDto()
        val resDto = volunteerActivityManageApplicationService
            .addVolunteerActivity(
                shelterUserId = shelterUserInfo.shelterUserId,
                reqDto = reqDto
            )

        return ResponseEntity.ok(resDto)
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{volunteerEventId}")
    @Operation(
        summary = "봉사 활동 수정"
    )
    fun editVolunteerEvent(
        @PathVariable volunteerEventId: Long,
        @RequestBody @Valid req: EditVolunteerEventRequest,
        @ShelterUserAuthentication shelterUserInfo: ShelterUserAuthenticationInfo
    ): ResponseEntity<EditVolunteerEventResponseDto> {
        val reqDto = EditVolunteerEventRequestDto(
            shelterUserId = shelterUserInfo.shelterUserId,
            volunteerEventId = volunteerEventId,
            title = req.title,
            recruitNum = req.recruitNum,
            description = req.description,
            category = req.category,
            ageLimit = req.ageLimit,
            startAt = req.startAt,
            status = req.status,
            endAt = req.endAt
        )
        val resDto = volunteerActivityManageApplicationService
            .editVolunteerActivity(reqDto)

        return ResponseEntity.ok(resDto)
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{volunteerEventId}")
    @Operation(
        summary = "봉사 활동 삭제"
    )
    fun deleteVolunteerEvent(
        @PathVariable volunteerEventId: Long,
        @ShelterUserAuthentication shelterUserInfo: ShelterUserAuthenticationInfo
    ): ResponseEntity<DeleteVolunteerActivityResponseDto> {

        val reqDto = DeleteVolunteerActivityRequestDto(
            volunteerActivityId = volunteerEventId,
            shelterUserId = shelterUserInfo.shelterUserId
        )
        val resDto = volunteerActivityManageApplicationService
            .deleteVolunteerActivity(reqDto)

        return ResponseEntity.ok(resDto)
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/home")
    @Operation(
        summary = "보호소 파트너 봉사활동 홈페이지 조회"
    )
    fun getHomeVolunteerEvents(
        @ShelterUserAuthentication shelterUserInfo: ShelterUserAuthenticationInfo,
        @RequestBody @Valid req: ShelterHomeRequest,
    ): ResponseEntity<List<VolunteerSimpleVolunteerActivityDto>> {
        val reqDto = GetShelterHomeRequestDto(
            category = req.category,
            status = req.status,
            from = req.from.atStartOfDay(),
            to = req.to.atTime(23, 59, 59),
            shelterUserId = shelterUserInfo.shelterUserId,
        )

        val resDto = shelterHomeApplicationService.getShelterEventHome(reqDto)

        return ResponseEntity.ok(resDto)
    }
}
