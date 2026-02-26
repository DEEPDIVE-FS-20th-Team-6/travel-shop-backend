package gdfs.travelshop.dto

import java.util.UUID

data class ProductSummaryResponse(
    val id: UUID?,
    val title: String,
    val location: String,
    val price: Int,
    val imageUrl: String?
)
