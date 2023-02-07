package com.cgi.fic.tests.service.dto;


import io.quarkus.runtime.annotations.RegisterForReflection;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.cgi.fic.tests.domain.DiscountCode} entity.
 */
@RegisterForReflection
public class DiscountCodeDTO implements Serializable {
    
    public Long id;

    @NotNull
    public String code;

    @NotNull
    public Float discount;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DiscountCodeDTO)) {
            return false;
        }

        return id != null && id.equals(((DiscountCodeDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DiscountCodeDTO{" +
            "id=" + id +
            ", code='" + code + "'" +
            ", discount=" + discount +
            "}";
    }
}
