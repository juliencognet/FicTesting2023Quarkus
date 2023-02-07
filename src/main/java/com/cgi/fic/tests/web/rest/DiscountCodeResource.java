package com.cgi.fic.tests.web.rest;

import static javax.ws.rs.core.UriBuilder.fromPath;

import com.cgi.fic.tests.service.DiscountCodeService;
import com.cgi.fic.tests.web.rest.errors.BadRequestAlertException;
import com.cgi.fic.tests.web.util.HeaderUtil;
import com.cgi.fic.tests.web.util.ResponseUtil;
import com.cgi.fic.tests.service.dto.DiscountCodeDTO;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.cgi.fic.tests.domain.DiscountCode}.
 */
@Path("/api/discount-codes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class DiscountCodeResource {

    private final Logger log = LoggerFactory.getLogger(DiscountCodeResource.class);

    private static final String ENTITY_NAME = "discountCode";

    @ConfigProperty(name = "application.name")
    String applicationName;


    @Inject
    DiscountCodeService discountCodeService;
    /**
     * {@code POST  /discount-codes} : Create a new discountCode.
     *
     * @param discountCodeDTO the discountCodeDTO to create.
     * @return the {@link Response} with status {@code 201 (Created)} and with body the new discountCodeDTO, or with status {@code 400 (Bad Request)} if the discountCode has already an ID.
     */
    @POST
    public Response createDiscountCode(@Valid DiscountCodeDTO discountCodeDTO, @Context UriInfo uriInfo) {
        log.debug("REST request to save DiscountCode : {}", discountCodeDTO);
        if (discountCodeDTO.id != null) {
            throw new BadRequestAlertException("A new discountCode cannot already have an ID", ENTITY_NAME, "idexists");
        }
        var result = discountCodeService.persistOrUpdate(discountCodeDTO);
        var response = Response.created(fromPath(uriInfo.getPath()).path(result.id.toString()).build()).entity(result);
        HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.id.toString()).forEach(response::header);
        return response.build();
    }

    /**
     * {@code PUT  /discount-codes} : Updates an existing discountCode.
     *
     * @param discountCodeDTO the discountCodeDTO to update.
     * @return the {@link Response} with status {@code 200 (OK)} and with body the updated discountCodeDTO,
     * or with status {@code 400 (Bad Request)} if the discountCodeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the discountCodeDTO couldn't be updated.
     */
    @PUT
    public Response updateDiscountCode(@Valid DiscountCodeDTO discountCodeDTO) {
        log.debug("REST request to update DiscountCode : {}", discountCodeDTO);
        if (discountCodeDTO.id == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        var result = discountCodeService.persistOrUpdate(discountCodeDTO);
        var response = Response.ok().entity(result);
        HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, discountCodeDTO.id.toString()).forEach(response::header);
        return response.build();
    }

    /**
     * {@code DELETE  /discount-codes/:id} : delete the "id" discountCode.
     *
     * @param id the id of the discountCodeDTO to delete.
     * @return the {@link Response} with status {@code 204 (NO_CONTENT)}.
     */
    @DELETE
    @Path("/{id}")
    public Response deleteDiscountCode(@PathParam("id") Long id) {
        log.debug("REST request to delete DiscountCode : {}", id);
        discountCodeService.delete(id);
        var response = Response.noContent();
        HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()).forEach(response::header);
        return response.build();
    }

    /**
     * {@code GET  /discount-codes} : get all the discountCodes.
     *     * @return the {@link Response} with status {@code 200 (OK)} and the list of discountCodes in body.
     */
    @GET
    public List<DiscountCodeDTO> getAllDiscountCodes() {
        log.debug("REST request to get all DiscountCodes");
        return discountCodeService.findAll();
    }


    /**
     * {@code GET  /discount-codes/:id} : get the "id" discountCode.
     *
     * @param id the id of the discountCodeDTO to retrieve.
     * @return the {@link Response} with status {@code 200 (OK)} and with body the discountCodeDTO, or with status {@code 404 (Not Found)}.
     */
    @GET
    @Path("/{id}")

    public Response getDiscountCode(@PathParam("id") Long id) {
        log.debug("REST request to get DiscountCode : {}", id);
        Optional<DiscountCodeDTO> discountCodeDTO = discountCodeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(discountCodeDTO);
    }
}
