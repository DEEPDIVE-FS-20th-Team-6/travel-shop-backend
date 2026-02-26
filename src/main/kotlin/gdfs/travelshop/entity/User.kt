package gdfs.travelshop.entity

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "users")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null,

    @Column(nullable = false, unique = true, length = 150)
    var email: String? = null,

    @Column(nullable = false)
    var password: String? = null,

    @Column(nullable = false, length = 100)
    var name: String? = null,

    var createdAt: LocalDateTime? = null
) {
    // Protected no-args constructor for Hibernate
    constructor() : this(null, null, null, null, null)

    // Builder-like factory function
    companion object {
        fun builder() = UserBuilder()
    }

    @PrePersist
    protected fun onCreate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now()
        }
    }
}

class UserBuilder {
    private var id: UUID? = null
    private var email: String? = null
    private var password: String? = null
    private var name: String? = null
    private var createdAt: LocalDateTime? = null

    fun id(id: UUID?) = apply { this.id = id }
    fun email(email: String?) = apply { this.email = email }
    fun password(password: String?) = apply { this.password = password }
    fun name(name: String?) = apply { this.name = name }
    fun createdAt(createdAt: LocalDateTime?) = apply { this.createdAt = createdAt }

    fun build() = User(id, email, password, name, createdAt)
}
