package com.cgi.fic.tests.domain;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import javax.json.bind.annotation.JsonbTransient;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.runtime.annotations.RegisterForReflection;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A DiscountCode.
 */
@Entity
@Table(name = "discount_code")
@RegisterForReflection
public class DiscountCode extends PanacheEntityBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    public Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    public String code;

    @NotNull
    @Column(name = "discount", nullable = false)
    public Float discount;

    @ManyToMany(mappedBy = "discountCodes")
    @JsonbTransient
    public Set<Basket> baskets = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DiscountCode)) {
            return false;
        }
        return id != null && id.equals(((DiscountCode) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DiscountCode{" +
            "id=" + id +
            ", code='" + code + "'" +
            ", discount=" + discount +
            "}";
    }

    public DiscountCode update() {
        return update(this);
    }

    public DiscountCode persistOrUpdate() {
        return persistOrUpdate(this);
    }

    public static DiscountCode update(DiscountCode discountCode) {
        if (discountCode == null) {
            throw new IllegalArgumentException("discountCode can't be null");
        }
        var entity = DiscountCode.<DiscountCode>findById(discountCode.id);
        if (entity != null) {
            entity.code = discountCode.code;
            entity.discount = discountCode.discount;
            entity.baskets = discountCode.baskets;
        }
        return entity;
    }

    public static DiscountCode persistOrUpdate(DiscountCode discountCode) {
        if (discountCode == null) {
            throw new IllegalArgumentException("discountCode can't be null");
        }
        if (discountCode.id == null) {
            persist(discountCode);
            return discountCode;
        } else {
            return update(discountCode);
        }
    }


}
