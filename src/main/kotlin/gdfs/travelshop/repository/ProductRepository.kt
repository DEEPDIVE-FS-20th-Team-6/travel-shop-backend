package gdfs.travelshop.repository

import gdfs.travelshop.entity.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import java.util.*

interface ProductRepository :
    JpaRepository<Product, UUID>,
    JpaSpecificationExecutor<Product>