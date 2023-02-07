package com.cgi.fic.tests.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cgi.fic.tests.TestUtil;

public class ProductInBasketDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductInBasketDTO.class);
        ProductInBasketDTO productInBasketDTO1 = new ProductInBasketDTO();
        productInBasketDTO1.id = 1L;
        ProductInBasketDTO productInBasketDTO2 = new ProductInBasketDTO();
        assertThat(productInBasketDTO1).isNotEqualTo(productInBasketDTO2);
        productInBasketDTO2.id = productInBasketDTO1.id;
        assertThat(productInBasketDTO1).isEqualTo(productInBasketDTO2);
        productInBasketDTO2.id = 2L;
        assertThat(productInBasketDTO1).isNotEqualTo(productInBasketDTO2);
        productInBasketDTO1.id = null;
        assertThat(productInBasketDTO1).isNotEqualTo(productInBasketDTO2);
    }
}
