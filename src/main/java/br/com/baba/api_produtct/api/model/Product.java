package br.com.baba.api_produtct.api.model;

import br.com.baba.api_produtct.api.dto.ProductFormDTO;
import br.com.baba.api_produtct.api.enums.ProductStatusEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated
    private ProductStatusEnum status;

    @Column(precision = 19, scale = 2)
    private BigDecimal quantity;

    @Column(precision = 19, scale = 2)
    private BigDecimal price;

    @Column(name = "registration_date")
    private LocalDate registrationDate;

    public Product(ProductFormDTO productFormDTO) {
        this.name = productFormDTO.name();
        this.status = ProductStatusEnum.ACTIVE;
        this.price = productFormDTO.price();
        this.registrationDate = LocalDate.now();
        this.quantity = BigDecimal.ZERO;
        if (productFormDTO.quantity() != null) {
            this.quantity = productFormDTO.quantity();
        }
    }
}