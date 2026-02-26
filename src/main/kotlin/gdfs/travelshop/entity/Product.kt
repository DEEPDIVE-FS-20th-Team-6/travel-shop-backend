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
class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private var id: UUID? = null

    @Column(nullable = false, length = 200)
    private var title: String? = null

    @Column(columnDefinition = "TEXT")
    private var description: String? = null

    @Column(nullable = false, length = 100)
    private var location: String? = null

    @Column(nullable = false)
    private var price: Int? = null

    private var imageUrl: String? = null

    private var createdAt: LocalDateTime? = null

    @PrePersist
    protected fun onCreate() {
        this.createdAt = LocalDateTime.now()
    }
}