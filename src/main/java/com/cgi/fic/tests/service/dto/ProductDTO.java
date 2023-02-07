package com.cgi.fic.tests.service.dto;


import io.quarkus.runtime.annotations.RegisterForReflection;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.cgi.fic.tests.domain.Product} entity.
 */
@RegisterForReflection
public class ProductDTO implements Serializable {
    
    public Long id;

    @NotNull
    public String productName;

    @NotNull
    public Float unitPrice;

    public String ean13BarCode;

    public String brand;

    public String categories;

    public String imageUrl;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductDTO)) {
            return false;
        }

        return id != null && id.equals(((ProductDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
            "id=" + id +
            ", productName='" + productName + "'" +
            ", unitPrice=" + unitPrice +
            ", ean13BarCode='" + ean13BarCode + "'" +
            ", brand='" + brand + "'" +
            ", categories='" + categories + "'" +
            ", imageUrl='" + imageUrl + "'" +
            "}";
    }
}
