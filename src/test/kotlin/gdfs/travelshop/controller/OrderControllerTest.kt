package gdfs.travelshop.controller

import com.ninjasquad.springmockk.MockkBean
import gdfs.travelshop.config.SecurityConfig
import gdfs.travelshop.config.TokenAuthenticationFilter
import gdfs.travelshop.dto.OrderRequest
import gdfs.travelshop.dto.OrderResponse
import gdfs.travelshop.service.OrderService
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDateTime
import java.util.*
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication

@WebMvcTest(OrderController::class)
@Import(SecurityConfig::class, TokenAuthenticationFilter::class)
class OrderControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockkBean
    lateinit var orderService: OrderService

    @Test
    fun `POST orders는 인증 헤더 없이 401을 반환한다`() {
        val productId = UUID.randomUUID()

        mockMvc.perform(
            post("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""{"productId": "$productId"}""")
        )
            .andExpect(status().isUnauthorized)
    }

    @Test
    fun `POST orders는 잘못된 토큰 형식으로 401을 반환한다`() {
        val productId = UUID.randomUUID()

        mockMvc.perform(
            post("/orders")
                .header("Authorization", "Bearer invalid-token-format")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""{"productId": "$productId"}""")
        )
            .andExpect(status().isUnauthorized)
    }

    @Test
    fun `POST orders는 productId가 null이면 400을 반환한다`() {
        val validToken = UUID.randomUUID()

        val authentication = UsernamePasswordAuthenticationToken(validToken.toString(), null, emptyList())

        mockMvc.perform(
            post("/orders")
                .header("Authorization", "Bearer $validToken")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""{"productId": null}""")
                .with(authentication(authentication))
        )
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `POST orders는 유효한 토큰으로 주문을 생성한다`() {
        val userId = UUID.randomUUID()
        val productId = UUID.randomUUID()
        val orderId = UUID.randomUUID()
        val now = LocalDateTime.now()

        val request = OrderRequest(productId = productId)
        val response = OrderResponse(
            id = orderId,
            userId = userId,
            productId = productId,
            createdAt = now
        )

        every { orderService.createOrder(userId, request) } returns response
        val authentication = UsernamePasswordAuthenticationToken(userId.toString(), null, emptyList())


        mockMvc.perform(
            post("/orders")
                .header("Authorization", "Bearer $userId")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""{"productId": "$productId"}""")
                .with(authentication(authentication))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(orderId.toString()))
            .andExpect(jsonPath("$.userId").value(userId.toString()))
            .andExpect(jsonPath("$.productId").value(productId.toString()))
    }
}
