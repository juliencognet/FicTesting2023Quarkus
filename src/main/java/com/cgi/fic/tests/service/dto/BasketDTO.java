package com.cgi.fic.tests.service.dto;


import io.quarkus.runtime.annotations.RegisterForReflection;
import java.time.LocalDate;
import com.cgi.fic.tests.config.Constants;
import javax.json.bind.annotation.JsonbDateFormat;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the {@link com.cgi.fic.tests.domain.Basket} entity.
 */
@RegisterForReflection
public class BasketDTO implements Serializable {

    public Long id;

    @NotNull
    public Float totalPrice;

    @NotNull
    @JsonbDateFormat(value = Constants.LOCAL_DATE_FORMAT)
    public LocalDate creationDate;

    public Set<DiscountCodeDTO> discountCodes = new HashSet<>();

    public Set<ProductInBasketDTO> products = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BasketDTO)) {
            return false;
        }

        return id != null && id.equals(((BasketDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "BasketDTO{" +
            "id=" + id +
            ", totalPrice=" + totalPrice +
            ", creationDate='" + creationDate + "'" +
            ", discountCodes='" + discountCodes + "'" +
            "}";
    }
}
