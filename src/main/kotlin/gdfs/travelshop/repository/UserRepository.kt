package gdfs.travelshop.repository

import gdfs.travelshop.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<User, UUID> {

    fun findByEmail(email: String): User?
}