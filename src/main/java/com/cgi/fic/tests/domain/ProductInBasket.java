package com.cgi.fic.tests.domain;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import javax.json.bind.annotation.JsonbTransient;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.runtime.annotations.RegisterForReflection;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A ProductInBasket.
 */
@Entity
@Table(name = "product_in_basket")
@RegisterForReflection
public class ProductInBasket extends PanacheEntityBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    public Long id;

    @NotNull
    @Column(name = "quantity", nullable = false)
    public Integer quantity;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonbTransient
    public Product product;

    @ManyToOne
    @JoinColumn(name = "basket_id")
    @JsonbTransient
    public Basket basket;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductInBasket)) {
            return false;
        }
        return id != null && id.equals(((ProductInBasket) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ProductInBasket{" +
            "id=" + id +
            ", quantity=" + quantity +
            "}";
    }

    public ProductInBasket update() {
        return update(this);
    }

    public ProductInBasket persistOrUpdate() {
        return persistOrUpdate(this);
    }

    public static ProductInBasket update(ProductInBasket productInBasket) {
        if (productInBasket == null) {
            throw new IllegalArgumentException("productInBasket can't be null");
        }
        var entity = ProductInBasket.<ProductInBasket>findById(productInBasket.id);
        if (entity != null) {
            entity.quantity = productInBasket.quantity;
            entity.product = productInBasket.product;
            entity.basket = productInBasket.basket;
        }
        return entity;
    }

    public static ProductInBasket persistOrUpdate(ProductInBasket productInBasket) {
        if (productInBasket == null) {
            throw new IllegalArgumentException("productInBasket can't be null");
        }
        if (productInBasket.id == null) {
            persist(productInBasket);
            return productInBasket;
        } else {
            return update(productInBasket);
        }
    }


}
