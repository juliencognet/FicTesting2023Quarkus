package com.cgi.fic.tests.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cgi.fic.tests.TestUtil;

public class DiscountCodeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DiscountCodeDTO.class);
        DiscountCodeDTO discountCodeDTO1 = new DiscountCodeDTO();
        discountCodeDTO1.id = 1L;
        DiscountCodeDTO discountCodeDTO2 = new DiscountCodeDTO();
        assertThat(discountCodeDTO1).isNotEqualTo(discountCodeDTO2);
        discountCodeDTO2.id = discountCodeDTO1.id;
        assertThat(discountCodeDTO1).isEqualTo(discountCodeDTO2);
        discountCodeDTO2.id = 2L;
        assertThat(discountCodeDTO1).isNotEqualTo(discountCodeDTO2);
        discountCodeDTO1.id = null;
        assertThat(discountCodeDTO1).isNotEqualTo(discountCodeDTO2);
    }
}
