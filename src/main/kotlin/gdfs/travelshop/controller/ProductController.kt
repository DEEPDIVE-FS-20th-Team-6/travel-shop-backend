package gdfs.travelshop.controller

import gdfs.travelshop.dto.ProductDetailResponse
import gdfs.travelshop.dto.ProductSummaryResponse
import gdfs.travelshop.service.ProductService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
class ProductController(
    private val productService: ProductService
) {
    @GetMapping("/products")
    fun listProducts(
        @RequestParam keyword: String?,
        @RequestParam minPrice: Int?,
        @RequestParam maxPrice: Int?
    ): List<ProductSummaryResponse> {
        return productService.listProducts(keyword, minPrice, maxPrice)
    }

    @GetMapping("/products/{id}")
    fun getProduct(@PathVariable id: UUID): ProductDetailResponse {
        return productService.getProduct(id)
    }
}
