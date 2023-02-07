package com.cgi.fic.tests.service.mapper;


import com.cgi.fic.tests.domain.*;
import com.cgi.fic.tests.service.dto.ProductDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Product} and its DTO {@link ProductDTO}.
 */
@Mapper(componentModel = "cdi", uses = {})
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {



    default Product fromId(Long id) {
        if (id == null) {
            return null;
        }
        Product product = new Product();
        product.id = id;
        return product;
    }
}
