package com.cgi.fic.tests.service.mapper;


import com.cgi.fic.tests.domain.*;
import com.cgi.fic.tests.service.dto.BasketDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Basket} and its DTO {@link BasketDTO}.
 */
@Mapper(componentModel = "cdi", uses = {DiscountCodeMapper.class})
public interface BasketMapper extends EntityMapper<BasketDTO, Basket> {


    @Mapping(target = "products", ignore = true)
    Basket toEntity(BasketDTO basketDTO);

    default Basket fromId(Long id) {
        if (id == null) {
            return null;
        }
        Basket basket = new Basket();
        basket.id = id;
        return basket;
    }
}
