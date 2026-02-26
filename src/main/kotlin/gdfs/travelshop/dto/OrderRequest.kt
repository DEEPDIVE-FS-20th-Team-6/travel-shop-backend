package gdfs.travelshop.dto

import jakarta.validation.constraints.NotNull
import java.util.UUID

data class OrderRequest(
    @field:NotNull
    val productId: UUID?
)
