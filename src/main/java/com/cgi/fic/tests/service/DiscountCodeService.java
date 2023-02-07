package com.cgi.fic.tests.service;

import com.cgi.fic.tests.domain.DiscountCode;
import com.cgi.fic.tests.service.dto.DiscountCodeDTO;
import com.cgi.fic.tests.service.mapper.DiscountCodeMapper;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Transactional
public class DiscountCodeService {

    private final Logger log = LoggerFactory.getLogger(DiscountCodeService.class);

    @Inject
    DiscountCodeMapper discountCodeMapper;

    @Transactional
    public DiscountCodeDTO persistOrUpdate(DiscountCodeDTO discountCodeDTO) {
        log.debug("Request to save DiscountCode : {}", discountCodeDTO);
        var discountCode = discountCodeMapper.toEntity(discountCodeDTO);
        discountCode = DiscountCode.persistOrUpdate(discountCode);
        return discountCodeMapper.toDto(discountCode);
    }

    /**
     * Delete the DiscountCode by id.
     *
     * @param id the id of the entity.
     */
    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete DiscountCode : {}", id);
        DiscountCode.findByIdOptional(id).ifPresent(discountCode -> {
            discountCode.delete();
        });
    }

    /**
     * Get one discountCode by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<DiscountCodeDTO> findOne(Long id) {
        log.debug("Request to get DiscountCode : {}", id);
        return DiscountCode.findByIdOptional(id)
            .map(discountCode -> discountCodeMapper.toDto((DiscountCode) discountCode)); 
    }

    /**
     * Get all the discountCodes.
     * @return the list of entities.
     */
    public  List<DiscountCodeDTO> findAll() {
        log.debug("Request to get all DiscountCodes");
        List<DiscountCode> discountCodes = DiscountCode.findAll().list();
        return discountCodeMapper.toDto(discountCodes);
    }



}
