package com.cgi.fic.tests.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class BasketMapperTest {

    private BasketMapper basketMapper;

    @BeforeEach
    public void setUp() {
        basketMapper = new BasketMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(basketMapper.fromId(id).id).isEqualTo(id);
        assertThat(basketMapper.fromId(null)).isNull();
    }
}
