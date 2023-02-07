package com.cgi.fic.tests.web.rest;

import static javax.ws.rs.core.UriBuilder.fromPath;

import com.cgi.fic.tests.service.BasketService;
import com.cgi.fic.tests.web.rest.errors.BadRequestAlertException;
import com.cgi.fic.tests.web.util.HeaderUtil;
import com.cgi.fic.tests.web.util.ResponseUtil;
import com.cgi.fic.tests.service.dto.BasketDTO;

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
 * REST controller for managing {@link com.cgi.fic.tests.domain.Basket}.
 */
@Path("/api/baskets")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class BasketResource {

    private final Logger log = LoggerFactory.getLogger(BasketResource.class);

    private static final String ENTITY_NAME = "basket";

    @ConfigProperty(name = "application.name")
    String applicationName;


    @Inject
    BasketService basketService;
    /**
     * {@code POST  /baskets} : Create a new basket.
     *
     * @param basketDTO the basketDTO to create.
     * @return the {@link Response} with status {@code 201 (Created)} and with body the new basketDTO, or with status {@code 400 (Bad Request)} if the basket has already an ID.
     */
    @POST
    public Response createBasket(@Valid BasketDTO basketDTO, @Context UriInfo uriInfo) {
        log.debug("REST request to save Basket : {}", basketDTO);
        if (basketDTO.id != null) {
            throw new BadRequestAlertException("A new basket cannot already have an ID", ENTITY_NAME, "idexists");
        }
        var result = basketService.persistOrUpdate(basketDTO);
        var response = Response.created(fromPath(uriInfo.getPath()).path(result.id.toString()).build()).entity(result);
        HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.id.toString()).forEach(response::header);
        return response.build();
    }

    /**
     * {@code PUT  /baskets} : Updates an existing basket.
     *
     * @param basketDTO the basketDTO to update.
     * @return the {@link Response} with status {@code 200 (OK)} and with body the updated basketDTO,
     * or with status {@code 400 (Bad Request)} if the basketDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the basketDTO couldn't be updated.
     */
    @PUT
    public Response updateBasket(@Valid BasketDTO basketDTO) {
        log.debug("REST request to update Basket : {}", basketDTO);
        if (basketDTO.id == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        var result = basketService.persistOrUpdate(basketDTO);
        var response = Response.ok().entity(result);
        HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, basketDTO.id.toString()).forEach(response::header);
        return response.build();
    }


    @PUT
    @Path("/{id}/discount/{discountCode}")
    public BasketDTO addDiscountCode(@PathParam("id") Long id, @PathParam("discountCode") String discountCode) {
        log.info("REST request to update Basket : {} to add discount code {}", id,discountCode);
        return basketService.addDiscountCode(id, discountCode);
    }

    /**
     * {@code DELETE  /baskets/:id} : delete the "id" basket.
     *
     * @param id the id of the basketDTO to delete.
     * @return the {@link Response} with status {@code 204 (NO_CONTENT)}.
     */
    @DELETE
    @Path("/{id}")
    public Response deleteBasket(@PathParam("id") Long id) {
        log.debug("REST request to delete Basket : {}", id);
        basketService.delete(id);
        var response = Response.noContent();
        HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()).forEach(response::header);
        return response.build();
    }

    /**
     * {@code GET  /baskets} : get all the baskets.
     *     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link Response} with status {@code 200 (OK)} and the list of baskets in body.
     */
    @GET
    public List<BasketDTO> getAllBaskets(@QueryParam(value = "eagerload") boolean eagerload) {
        log.debug("REST request to get all Baskets");
        return basketService.findAll();
    }


    /**
     * {@code GET  /baskets/:id} : get the "id" basket.
     *
     * @param id the id of the basketDTO to retrieve.
     * @return the {@link Response} with status {@code 200 (OK)} and with body the basketDTO, or with status {@code 404 (Not Found)}.
     */
    @GET
    @Path("/{id}")
    public Response getBasket(@PathParam("id") Long id) {
        log.debug("REST request to get Basket : {}", id);
        Optional<BasketDTO> basketDTO = basketService.findOne(id);
        return ResponseUtil.wrapOrNotFound(basketDTO);
    }
}
