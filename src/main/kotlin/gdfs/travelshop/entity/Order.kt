package gdfs.travelshop.entity

import jakarta.persistence.*
import lombok.*
import java.time.LocalDateTime
import java.util.*

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "orders")
class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private var id: UUID? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private var user: User? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private var product: Product? = null

    private var createdAt: LocalDateTime? = null

    @PrePersist
    protected fun onCreate() {
        this.createdAt = LocalDateTime.now()
    }
}