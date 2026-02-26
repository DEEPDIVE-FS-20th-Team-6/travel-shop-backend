package gdfs.travelshop.service

import gdfs.travelshop.dto.OrderRequest
import gdfs.travelshop.dto.OrderResponse
import gdfs.travelshop.entity.Order
import gdfs.travelshop.repository.OrderRepository
import gdfs.travelshop.repository.ProductRepository
import gdfs.travelshop.repository.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.UUID

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val userRepository: UserRepository,
    private val productRepository: ProductRepository
) {
    fun createOrder(userId: UUID, request: OrderRequest): OrderResponse {
        val user = userRepository.findById(userId).orElseThrow {
            ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found")
        }
        
        val product = productRepository.findById(request.productId!!).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found")
        }

        val order = Order()
        order.user = user
        order.product = product
        val savedOrder = orderRepository.save(order)

        return OrderResponse(
            id = savedOrder.id,
            userId = savedOrder.user?.id,
            productId = savedOrder.product?.id,
            createdAt = savedOrder.createdAt
        )
    }
}
