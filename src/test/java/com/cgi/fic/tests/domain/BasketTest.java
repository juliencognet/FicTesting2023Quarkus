package com.cgi.fic.tests.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cgi.fic.tests.TestUtil;
import org.junit.jupiter.api.Test;


public class BasketTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Basket.class);
        Basket basket1 = new Basket();
        basket1.id = 1L;
        Basket basket2 = new Basket();
        basket2.id = basket1.id;
        assertThat(basket1).isEqualTo(basket2);
        basket2.id = 2L;
        assertThat(basket1).isNotEqualTo(basket2);
        basket1.id = null;
        assertThat(basket1).isNotEqualTo(basket2);
    }
}
