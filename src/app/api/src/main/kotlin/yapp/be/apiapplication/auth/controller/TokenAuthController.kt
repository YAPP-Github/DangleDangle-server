package yapp.be.apiapplication.auth.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import yapp.be.apiapplication.auth.controller.model.TokenRefreshRequest
import yapp.be.apiapplication.auth.service.TokenRefreshApplicationService
import yapp.be.apiapplication.auth.service.model.TokenRefreshResponseDto

@RestController
@Tag(name = "토큰 재발급 api")
@RequestMapping("/v1/auth/token")
class TokenAuthController(
    val tokenRefreshApplicationService: TokenRefreshApplicationService,
) {
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/refresh")
    @Operation(
        summary = "만료된 access token 재발급",
    )
    fun loginShelterUser(
        @RequestBody @Valid
        req: TokenRefreshRequest
    ): ResponseEntity<TokenRefreshResponseDto> {
        val reqDto = req.toDto()
        val resDto = tokenRefreshApplicationService.refresh(reqDto)

        return ResponseEntity.ok(resDto)
    }
}
