package gdfs.travelshop.entity

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "orders")
class Order(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    var product: Product? = null,

    var createdAt: LocalDateTime? = null
) {
    constructor() : this(null, null, null, null)

    companion object {
        fun builder() = OrderBuilder()
    }

    @PrePersist
    protected fun onCreate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now()
        }
    }
}

class OrderBuilder {
    private var id: UUID? = null
    private var user: User? = null
    private var product: Product? = null
    private var createdAt: LocalDateTime? = null

    fun id(id: UUID?) = apply { this.id = id }
    fun user(user: User?) = apply { this.user = user }
    fun product(product: Product?) = apply { this.product = product }
    fun createdAt(createdAt: LocalDateTime?) = apply { this.createdAt = createdAt }

    fun build() = Order(id, user, product, createdAt)
}
