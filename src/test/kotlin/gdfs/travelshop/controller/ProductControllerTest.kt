package gdfs.travelshop.controller

import com.ninjasquad.springmockk.MockkBean
import gdfs.travelshop.dto.ProductDetailResponse
import gdfs.travelshop.dto.ProductSummaryResponse
import gdfs.travelshop.service.ProductService
import gdfs.travelshop.config.GlobalExceptionHandler
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.*

@Import(GlobalExceptionHandler::class)
@WebMvcTest(ProductController::class)
@AutoConfigureMockMvc(addFilters = false)
class ProductControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockkBean
    lateinit var productService: ProductService

    @Test
    fun `GET products는 상품 목록을 반환한다`() {
        val products = listOf(
            ProductSummaryResponse(
                id = UUID.randomUUID(),
                title = "제주도 3박4일",
                location = "제주도",
                price = 500000,
                imageUrl = "https://example.com/jeju.jpg"
            ),
            ProductSummaryResponse(
                id = UUID.randomUUID(),
                title = "부산 2박3일",
                location = "부산",
                price = 300000,
                imageUrl = "https://example.com/busan.jpg"
            )
        )

        every { productService.listProducts(any(), any(), any()) } returns products

        mockMvc.perform(get("/products"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].title").value("제주도 3박4일"))
            .andExpect(jsonPath("$[1].title").value("부산 2박3일"))
    }

    @Test
    fun `GET products by id는 상품 상세를 반환한다`() {
        val productId = UUID.randomUUID()
        val product = ProductDetailResponse(
            id = productId,
            title = "제주도 3박4일",
            description = "제주도 완전 정복!",
            location = "제주도",
            price = 500000,
            imageUrl = "https://example.com/jeju.jpg"
        )

        every { productService.getProduct(productId) } returns product

        mockMvc.perform(get("/products/{id}", productId))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(productId.toString()))
            .andExpect(jsonPath("$.title").value("제주도 3박4일"))
            .andExpect(jsonPath("$.description").value("제주도 완전 정복!"))
    }

    @Test
    fun `GET products by id는 존재하지 않는 상품에 대해 404를 반환한다`() {
        val productId = UUID.randomUUID()

        every { productService.getProduct(productId) } throws NoSuchElementException("Product not found")

        mockMvc.perform(get("/products/{id}", productId))
            .andExpect(status().isNotFound)
    }
}
