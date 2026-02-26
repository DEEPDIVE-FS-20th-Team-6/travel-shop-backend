package gdfs.travelshop.controller

import gdfs.travelshop.dto.OrderRequest
import gdfs.travelshop.dto.OrderResponse
import gdfs.travelshop.service.OrderService
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jakarta.validation.Valid
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@SecurityRequirement(name = "bearerAuth")
class OrderController(
    private val orderService: OrderService
) {
    @PostMapping("/orders")
    fun createOrder(
        authentication: Authentication?,
        @Valid @RequestBody request: OrderRequest
    ): OrderResponse {
        val userId = UUID.fromString(authentication?.principal as String)
        return orderService.createOrder(userId, request)
    }
}
