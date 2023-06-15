package yapp.be.apiapplication.shelter.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import yapp.be.apiapplication.shelter.controller.model.ObservationAnimalAddRequest

@Tag(name = "특별 케어 동물 관리 api")
@RequestMapping("/v1/shelter/{shelterId}/observation-animal")
@RestController
class ObservationAnimalAddController {

    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    @Operation(
        summary = "특별 케어 동물 추가"
    )
    fun editShelterProfileImage(
        @PathVariable shelterId: Long,
        @RequestBody req: ObservationAnimalAddRequest
    ) {
    }
}
