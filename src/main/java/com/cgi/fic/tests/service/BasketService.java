package com.cgi.fic.tests.service;

import com.cgi.fic.tests.domain.Basket;
import com.cgi.fic.tests.domain.DiscountCode;
import com.cgi.fic.tests.service.dto.BasketDTO;
import com.cgi.fic.tests.service.dto.DiscountCodeDTO;
import com.cgi.fic.tests.service.mapper.BasketMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Transactional
public class BasketService {

    private final Logger log = LoggerFactory.getLogger(BasketService.class);

    @Inject
    DiscountCodeService discountCodeService;

    @Inject
    BasketMapper basketMapper;

    @Transactional
    public BasketDTO persistOrUpdate(BasketDTO basketDTO) {
        log.debug("Request to save Basket : {}", basketDTO);
        var basket = basketMapper.toEntity(basketDTO);
        basket = computeTotalPrice(basket);
        basket = Basket.persistOrUpdate(basket);
        return basketMapper.toDto(basket);
    }

    /**
     * Delete the Basket by id.
     *
     * @param id the id of the entity.
     */
    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete Basket : {}", id);
        Basket.findByIdOptional(id).ifPresent(basket -> {
            basket.delete();
        });
    }

    @Transactional
    public BasketDTO addDiscountCode(Long basketId, String discountCode) {
        BasketDTO basketDTO = findOne(basketId).get();
        DiscountCodeDTO discount = discountCodeService.findByCode(discountCode);
        basketDTO.discountCodes.add(discount);
        return persistOrUpdate(basketDTO);
    }

    /**
     * Get one basket by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<BasketDTO> findOne(Long id) {
        log.debug("Request to get Basket : {}", id);
        Optional<Basket> basket = Basket.findOneWithEagerRelationships(id);
        if (basket.isPresent()) {
            basket = Optional.of(computeTotalPrice(basket.get()));
            return basket.map(b -> basketMapper.toDto((Basket) b));
        } else {
            return Optional.empty();
        }
    }

    private Basket computeTotalPrice(Basket basket){
        basket.totalPrice = basket.products.stream().map(p -> (float)(p.quantity * p.product.unitPrice)).reduce(0F, Float::sum);
        return basket;
    }

    /**
     * Get all the baskets.
     * @return the list of entities.
     */
    public  List<BasketDTO> findAll() {
        log.debug("Request to get all Baskets");
        List<Basket> baskets = Basket.findAllWithEagerRelationships().list();
        return basketMapper.toDto(baskets);
    }


    /**
     * Get all the baskets with eager load of many-to-many relationships.
     * @return the list of entities.
     */
    public  List<BasketDTO> findAllWithEagerRelationships() {
        List<Basket> baskets= Basket.findAllWithEagerRelationships().list();
        return basketMapper.toDto(baskets);
    }


}
