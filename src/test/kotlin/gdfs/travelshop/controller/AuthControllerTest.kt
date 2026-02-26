package gdfs.travelshop.controller

import com.ninjasquad.springmockk.MockkBean
import gdfs.travelshop.dto.LoginRequest
import gdfs.travelshop.dto.LoginResponse
import gdfs.travelshop.service.AuthService
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.*

@WebMvcTest(AuthController::class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockkBean
    lateinit var authService: AuthService

    @Test
    fun `로그인시 이메일이 공백이면 400을 반환한다`() {
        mockMvc.perform(
            post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""{"email": "", "password": "password123"}""")
        )
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `로그인시 비밀번호가 공백이면 400을 반환한다`() {
        mockMvc.perform(
            post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""{"email": "test@example.com", "password": ""}""")
        )
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `로그인 성공시 액세스 토큰을 반환한다`() {
        val accessToken = UUID.randomUUID().toString()
        val response = LoginResponse(accessToken = accessToken)

        every { authService.login(any()) } returns response

        mockMvc.perform(
            post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""{"email": "test@example.com", "password": "password123"}""")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.accessToken").value(accessToken))
    }
}
