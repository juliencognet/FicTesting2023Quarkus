package com.cgi.fic.tests.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cgi.fic.tests.TestUtil;
import org.junit.jupiter.api.Test;


public class DiscountCodeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DiscountCode.class);
        DiscountCode discountCode1 = new DiscountCode();
        discountCode1.id = 1L;
        DiscountCode discountCode2 = new DiscountCode();
        discountCode2.id = discountCode1.id;
        assertThat(discountCode1).isEqualTo(discountCode2);
        discountCode2.id = 2L;
        assertThat(discountCode1).isNotEqualTo(discountCode2);
        discountCode1.id = null;
        assertThat(discountCode1).isNotEqualTo(discountCode2);
    }
}
