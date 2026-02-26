package gdfs.travelshop.repository

import gdfs.travelshop.entity.Order
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface OrderRepository : JpaRepository<Order, UUID> {

    fun findByUserId(userId: UUID): List<Order>
}