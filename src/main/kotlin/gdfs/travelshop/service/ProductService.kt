package gdfs.travelshop.service

import gdfs.travelshop.dto.ProductDetailResponse
import gdfs.travelshop.dto.ProductSummaryResponse
import gdfs.travelshop.repository.ProductRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.UUID

@Service
class ProductService(
    private val productRepository: ProductRepository
) {
    fun listProducts(keyword: String?, minPrice: Int?, maxPrice: Int?): List<ProductSummaryResponse> {
        val products = productRepository.findAll()
        return products.map { product ->
            ProductSummaryResponse(
                id = product.id,
                title = product.title ?: "",
                location = product.location ?: "",
                price = product.price ?: 0,
                imageUrl = product.imageUrl
            )
        }
    }

    fun getProduct(id: UUID): ProductDetailResponse {
        val product = productRepository.findById(id).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found")
        }
        return ProductDetailResponse(
            id = product.id,
            title = product.title ?: "",
            description = product.description,
            location = product.location ?: "",
            price = product.price ?: 0,
            imageUrl = product.imageUrl
        )
    }
}
