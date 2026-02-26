package gdfs.travelshop.config

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.util.*

@Component
class TokenAuthenticationFilter : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader("Authorization")
        
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            val token = authHeader.substring(7)
            
            try {
                // Token is UUID format
                val userId = UUID.fromString(token)
                val authentication = UsernamePasswordAuthenticationToken(userId.toString(), null, emptyList())
                SecurityContextHolder.getContext().authentication = authentication
            } catch (e: IllegalArgumentException) {
                // Invalid UUID format - continue without authentication
            }
        }
        
        filterChain.doFilter(request, response)
    }
}
