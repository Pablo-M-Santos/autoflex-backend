package com.autoflex.productrawmaterial;

import com.autoflex.product.ProductEntity;
import com.autoflex.rawmaterial.RawMaterialEntity;
import jakarta.persistence.*;

@Entity
@Table(
        name = "product_raw_material",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"product_id", "raw_material_id"})
        }
)
public class ProductRawMaterialEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @ManyToOne(optional = false)
    @JoinColumn(name = "raw_material_id")
    private RawMaterialEntity rawMaterial;

    @Column(name = "required_quantity", nullable = false)
    private Integer requiredQuantity;


    public Long getId() {
        return id;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public RawMaterialEntity getRawMaterial() {
        return rawMaterial;
    }

    public Integer getRequiredQuantity() {
        return requiredQuantity;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public void setRawMaterial(RawMaterialEntity rawMaterial) {
        this.rawMaterial = rawMaterial;
    }

    public void setRequiredQuantity(Integer requiredQuantity) {
        this.requiredQuantity = requiredQuantity;
    }
}
