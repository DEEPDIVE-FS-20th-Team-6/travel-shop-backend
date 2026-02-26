package gdfs.travelshop.dto

import java.time.LocalDateTime
import java.util.UUID

data class OrderResponse(
    val id: UUID?,
    val userId: UUID?,
    val productId: UUID?,
    val createdAt: LocalDateTime?
)
