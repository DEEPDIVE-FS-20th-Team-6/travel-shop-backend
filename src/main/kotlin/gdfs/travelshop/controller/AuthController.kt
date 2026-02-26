package gdfs.travelshop.controller

import gdfs.travelshop.dto.LoginRequest
import gdfs.travelshop.dto.LoginResponse
import gdfs.travelshop.service.AuthService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(
    private val authService: AuthService
) {
    @PostMapping("/auth/login")
    fun login(@Valid @RequestBody request: LoginRequest): LoginResponse {
        return authService.login(request)
    }
}
