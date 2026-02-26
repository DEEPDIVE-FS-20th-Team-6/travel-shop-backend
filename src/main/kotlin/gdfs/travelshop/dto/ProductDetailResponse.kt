package gdfs.travelshop.dto

import java.util.UUID

data class ProductDetailResponse(
    val id: UUID?,
    val title: String,
    val description: String?,
    val location: String,
    val price: Int,
    val imageUrl: String?
)
