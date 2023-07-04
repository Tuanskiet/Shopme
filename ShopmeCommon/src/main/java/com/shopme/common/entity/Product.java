package com.shopme.common.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="[products]")
//@OrderColumn
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="product_id")
    private Integer productId;
    private String name;

    @Column(name="price", precision = 12, scale = 3)
    private BigDecimal price;
    @ElementCollection
    private List<String> colors;
    @ElementCollection
    private List<String> sizes;
    private String material;
    private Integer quantityLeft;
    private Integer quantitySold;

    @CreationTimestamp
    private Timestamp dateRelease;
    private String slug;

    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_type_id")
    private ProductType productType;

    @JsonManagedReference
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<ProductImage> images;

    public Product(Integer productId, String name, BigDecimal price, List<String> colors, List<String> sizes, String material, Integer quantityLeft, Integer quantitySold, String slug, ProductType productType, List<ProductImage> images) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.colors = colors;
        this.sizes = sizes;
        this.material = material;
        this.quantityLeft = quantityLeft;
        this.quantitySold = quantitySold;
        this.slug = slug;
        this.productType = productType;
        this.images = images;
    }
}