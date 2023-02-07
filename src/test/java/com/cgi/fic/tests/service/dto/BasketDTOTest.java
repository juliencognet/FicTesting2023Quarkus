package com.cgi.fic.tests.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cgi.fic.tests.TestUtil;

public class BasketDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BasketDTO.class);
        BasketDTO basketDTO1 = new BasketDTO();
        basketDTO1.id = 1L;
        BasketDTO basketDTO2 = new BasketDTO();
        assertThat(basketDTO1).isNotEqualTo(basketDTO2);
        basketDTO2.id = basketDTO1.id;
        assertThat(basketDTO1).isEqualTo(basketDTO2);
        basketDTO2.id = 2L;
        assertThat(basketDTO1).isNotEqualTo(basketDTO2);
        basketDTO1.id = null;
        assertThat(basketDTO1).isNotEqualTo(basketDTO2);
    }
}
