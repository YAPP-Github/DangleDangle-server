package yapp.be.apiapplication.user.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import yapp.be.apiapplication.system.exception.ErrorResponse
import yapp.be.domain.port.inbound.CheckUserUseCase

@RestController
@RequestMapping("/v1/user")
@Tag(name = "User")
class UserController(
    private val checkUserUseCase: CheckUserUseCase
) {
    @GetMapping("/nickname")
    @Operation(
        summary = "닉네임 중복 체크",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "닉네임 중복 체크 성공"
            ),
            ApiResponse(
                responseCode = "500",
                description = "서버에러",
                content = [Content(schema = Schema(implementation = ErrorResponse::class))]
            )
        ]
    )
    fun checkNickname(
        @RequestParam nickname: String,
    ): Boolean {
        return checkUserUseCase.isExistByNickname(nickname)
    }
}
