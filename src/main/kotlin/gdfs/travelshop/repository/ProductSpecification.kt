package gdfs.travelshop.repository

import gdfs.travelshop.entity.Product
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root
import org.springframework.data.jpa.domain.Specification

class ProductSpecification {
    companion object {
        fun withKeyword(keyword: String?): Specification<Product> {
            return Specification { root: Root<Product>, _: CriteriaQuery<*>, cb: CriteriaBuilder ->
                if (keyword.isNullOrBlank()) {
                    null
                } else {
                    cb.or(
                        cb.like(cb.lower(root.get("title")), "%${keyword.lowercase()}%"),
                        cb.like(cb.lower(root.get("description")), "%${keyword.lowercase()}%")
                    )
                }
            }
        }

        fun withMinPrice(minPrice: Int?): Specification<Product> {
            return Specification { root: Root<Product>, _: CriteriaQuery<*>, cb: CriteriaBuilder ->
                if (minPrice == null) {
                    null
                } else {
                    cb.greaterThanOrEqualTo(root.get("price"), minPrice)
                }
            }
        }

        fun withMaxPrice(maxPrice: Int?): Specification<Product> {
            return Specification { root: Root<Product>, _: CriteriaQuery<*>, cb: CriteriaBuilder ->
                if (maxPrice == null) {
                    null
                } else {
                    cb.lessThanOrEqualTo(root.get("price"), maxPrice)
                }
            }
        }
    }
}
