package com.cgi.fic.tests.domain;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import javax.json.bind.annotation.JsonbTransient;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.runtime.annotations.RegisterForReflection;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Optional;

/**
 * A Basket.
 */
@Entity
@Table(name = "basket")
@RegisterForReflection
public class Basket extends PanacheEntityBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    public Long id;

    @NotNull
    @Column(name = "total_price", nullable = false)
    public Float totalPrice;

    @Column(name = "creation_date", nullable = false)
    public LocalDate creationDate;

    @OneToMany(mappedBy = "basket")
    public Set<ProductInBasket> products = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "basket_discount_codes",
               joinColumns = @JoinColumn(name = "basket_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "discount_codes_id", referencedColumnName = "id"))
    @JsonbTransient
    public Set<DiscountCode> discountCodes = new HashSet<>();




    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Basket)) {
            return false;
        }
        return id != null && id.equals(((Basket) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Basket{" +
            "id=" + id +
            ", totalPrice=" + totalPrice +
            ", creationDate='" + creationDate + "'" +
            "}";
    }

    public Basket update() {
        return update(this);
    }

    public Basket persistOrUpdate() {
        return persistOrUpdate(this);
    }

    public static Basket update(Basket basket) {
        if (basket == null) {
            throw new IllegalArgumentException("basket can't be null");
        }
        var entity = Basket.<Basket>findById(basket.id);
        if (entity != null) {
            entity.totalPrice = basket.totalPrice;
            entity.creationDate = basket.creationDate;
            entity.products = basket.products;
            entity.discountCodes = basket.discountCodes;
        }
        return entity;
    }

    public static Basket persistOrUpdate(Basket basket) {
        if (basket == null) {
            throw new IllegalArgumentException("basket can't be null");
        }
        if (basket.id == null) {
            persist(basket);
            return basket;
        } else {
            return update(basket);
        }
    }

    public static PanacheQuery<Basket> findAllWithEagerRelationships() {
        return find("select distinct basket from Basket basket left join fetch basket.discountCodes left join fetch basket.products");
    }

    public static Optional<Basket> findOneWithEagerRelationships(Long id) {
        return find("select basket from Basket basket left join fetch basket.discountCodes left join fetch basket.products where basket.id =?1", id).firstResultOptional();
    }

}
