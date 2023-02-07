package com.cgi.fic.tests.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cgi.fic.tests.TestUtil;
import org.junit.jupiter.api.Test;


public class ProductInBasketTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductInBasket.class);
        ProductInBasket productInBasket1 = new ProductInBasket();
        productInBasket1.id = 1L;
        ProductInBasket productInBasket2 = new ProductInBasket();
        productInBasket2.id = productInBasket1.id;
        assertThat(productInBasket1).isEqualTo(productInBasket2);
        productInBasket2.id = 2L;
        assertThat(productInBasket1).isNotEqualTo(productInBasket2);
        productInBasket1.id = null;
        assertThat(productInBasket1).isNotEqualTo(productInBasket2);
    }
}
