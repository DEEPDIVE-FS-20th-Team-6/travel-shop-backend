package gdfs.travelshop.entity

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Entity
class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null,

    @Column(nullable = false, length = 200)
    var title: String? = null,

    @Column(columnDefinition = "TEXT")
    var description: String? = null,

    @Column(nullable = false, length = 100)
    var location: String? = null,

    @Column(nullable = false)
    var price: Int? = null,

    var imageUrl: String? = null,

    var createdAt: LocalDateTime? = null
) {
    constructor() : this(null, null, null, null, null, null, null)

    companion object {
        fun builder() = ProductBuilder()
    }

    @PrePersist
    protected fun onCreate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now()
        }
    }
}

class ProductBuilder {
    private var id: UUID? = null
    private var title: String? = null
    private var description: String? = null
    private var location: String? = null
    private var price: Int? = null
    private var imageUrl: String? = null
    private var createdAt: LocalDateTime? = null

    fun id(id: UUID?) = apply { this.id = id }
    fun title(title: String?) = apply { this.title = title }
    fun description(description: String?) = apply { this.description = description }
    fun location(location: String?) = apply { this.location = location }
    fun price(price: Int?) = apply { this.price = price }
    fun imageUrl(imageUrl: String?) = apply { this.imageUrl = imageUrl }
    fun createdAt(createdAt: LocalDateTime?) = apply { this.createdAt = createdAt }

    fun build() = Product(id, title, description, location, price, imageUrl, createdAt)
}
