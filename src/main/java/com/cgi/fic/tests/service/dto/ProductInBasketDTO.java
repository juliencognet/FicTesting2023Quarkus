package com.cgi.fic.tests.service.dto;


import io.quarkus.runtime.annotations.RegisterForReflection;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.cgi.fic.tests.domain.ProductInBasket} entity.
 */
@RegisterForReflection
public class ProductInBasketDTO implements Serializable {

    public Long id;

    @NotNull
    public Integer quantity;

    public Long productId;
    public ProductDTO product;
    public Long basketId;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductInBasketDTO)) {
            return false;
        }

        return id != null && id.equals(((ProductInBasketDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ProductInBasketDTO{" +
            "id=" + id +
            ", quantity=" + quantity +
            ", productId=" + productId +
            ", basketId=" + basketId +
            "}";
    }
}
