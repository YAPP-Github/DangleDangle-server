package yapp.be.apiapplication.shelter.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import yapp.be.apiapplication.shelter.service.ObservationAnimalApplicationService
import yapp.be.apiapplication.shelter.service.model.GetObservationAnimalResponseDto
import yapp.be.model.support.PagedResult

@Tag(name = "특별케어 동물 api")
@RestController
@RequestMapping("/v1/shelter/{shelterId}/observation-animal")
class ObservationAnimalController(
    private val observationAnimalApplicationService: ObservationAnimalApplicationService
) {

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    @Operation(
        summary = "특별케어동물 리스트 조회"
    )
    fun getShelterObservationAnimals(
        @PathVariable shelterId: Long,
        @RequestParam page: Int
    ): ResponseEntity<PagedResult<GetObservationAnimalResponseDto>> {
        val resDto = observationAnimalApplicationService
            .getShelterObservationAnimals(
                page = page,
                shelterId = shelterId
            )

        return ResponseEntity.ok(resDto)
    }
}
