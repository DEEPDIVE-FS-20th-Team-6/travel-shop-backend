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
@Table(name = "users")
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private var id: UUID? = null

    @Column(nullable = false, unique = true, length = 150)
    private var email: String? = null

    @Column(nullable = false)
    private var password: String? = null

    @Column(nullable = false, length = 100)
    private var name: String? = null

    private var createdAt: LocalDateTime? = null

    @PrePersist
    protected fun onCreate() {
        this.createdAt = LocalDateTime.now()
    }
}