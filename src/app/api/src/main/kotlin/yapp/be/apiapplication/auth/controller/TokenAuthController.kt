package yapp.be.apiapplication.auth.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import yapp.be.apiapplication.auth.controller.model.TokenRefreshRequest
import yapp.be.apiapplication.auth.service.UserAuthApplicationService
import yapp.be.apiapplication.auth.service.model.TokenRefreshResponseDto

@RestController
@Tag(name = "인증 관련 api")
@RequestMapping("/v1/auth")
class TokenAuthController(
    val userAuthApplicationService: UserAuthApplicationService,
) {
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/token/refresh")
    @Operation(
        summary = "만료된 access token 재발급",
    )
    fun loginShelterUser(
        @RequestBody @Valid
        req: TokenRefreshRequest
    ): ResponseEntity<TokenRefreshResponseDto> {
        val reqDto = req.toDto()
        val resDto = userAuthApplicationService.refresh(reqDto)

        return ResponseEntity.ok(resDto)
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/logout")
    @Operation(
        summary = "로그아웃",
    )
    fun loginShelterUser(
        @RequestHeader(value = "Authorization") accessToken: String
    ): ResponseEntity<String> {
        val resDto = userAuthApplicationService.logout(accessToken.replace("Bearer ", ""))

        return ResponseEntity.ok("Logout Success")
    }
}
