package yapp.be.apiapplication.auth.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import yapp.be.apiapplication.auth.controller.model.SignUpRequest
import yapp.be.apiapplication.system.exception.ErrorResponse
import yapp.be.domain.port.inbound.CreateUserUseCase
import yapp.be.domain.port.inbound.model.CreateUserCommand

@RestController
@RequestMapping("/v1/auth")
@Tag(name = "Auth")
class AuthController(
    private val CreateUserUseCase: CreateUserUseCase,
) {
    @PostMapping("/register")
    @Operation(
        summary = "회원가입",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "회원가입 성공"
            ),
            ApiResponse(
                responseCode = "500",
                description = "서버에러",
                content = [Content(schema = Schema(implementation = ErrorResponse::class))]
            )
        ]
    )
    suspend fun register(
        @RequestBody req: SignUpRequest,
    ): Boolean {
        return CreateUserUseCase.create(
            CreateUserCommand(
                nickname = req.nickname,
                email = req.email,
                phone = req.phone,
                oAuthAccessToken = req.oAuthAccessToken,
            )
        )
    }
}
