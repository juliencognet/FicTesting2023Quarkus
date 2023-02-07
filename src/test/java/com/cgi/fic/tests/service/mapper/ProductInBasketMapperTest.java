package com.cgi.fic.tests.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductInBasketMapperTest {

    private ProductInBasketMapper productInBasketMapper;

    @BeforeEach
    public void setUp() {
        productInBasketMapper = new ProductInBasketMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(productInBasketMapper.fromId(id).id).isEqualTo(id);
        assertThat(productInBasketMapper.fromId(null)).isNull();
    }
}
