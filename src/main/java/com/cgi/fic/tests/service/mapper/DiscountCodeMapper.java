package com.cgi.fic.tests.service.mapper;


import com.cgi.fic.tests.domain.*;
import com.cgi.fic.tests.service.dto.DiscountCodeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DiscountCode} and its DTO {@link DiscountCodeDTO}.
 */
@Mapper(componentModel = "cdi", uses = {})
public interface DiscountCodeMapper extends EntityMapper<DiscountCodeDTO, DiscountCode> {


    @Mapping(target = "baskets", ignore = true)
    DiscountCode toEntity(DiscountCodeDTO discountCodeDTO);

    default DiscountCode fromId(Long id) {
        if (id == null) {
            return null;
        }
        DiscountCode discountCode = new DiscountCode();
        discountCode.id = id;
        return discountCode;
    }
}
