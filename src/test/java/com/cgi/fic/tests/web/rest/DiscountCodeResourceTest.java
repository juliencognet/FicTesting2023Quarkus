package com.cgi.fic.tests.web.rest;

import static io.restassured.RestAssured.given;
import static io.restassured.config.ObjectMapperConfig.objectMapperConfig;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

import com.cgi.fic.tests.TestUtil;
import com.cgi.fic.tests.service.dto.DiscountCodeDTO;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import liquibase.Liquibase;
import io.quarkus.liquibase.LiquibaseFactory;
import org.junit.jupiter.api.*;

import javax.inject.Inject;

import java.util.List;

@QuarkusTest
public class DiscountCodeResourceTest {

    private static final TypeRef<DiscountCodeDTO> ENTITY_TYPE = new TypeRef<>() {
    };

    private static final TypeRef<List<DiscountCodeDTO>> LIST_OF_ENTITY_TYPE = new TypeRef<>() {
    };

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Float DEFAULT_DISCOUNT = 1F;
    private static final Float UPDATED_DISCOUNT = 2F;



    String adminToken;

    DiscountCodeDTO discountCodeDTO;

    @Inject
    LiquibaseFactory liquibaseFactory;

    @BeforeAll
    static void jsonMapper() {
        RestAssured.config =
            RestAssured.config().objectMapperConfig(objectMapperConfig().defaultObjectMapper(TestUtil.jsonbObjectMapper()));
    }

    @BeforeEach
    public void authenticateAdmin() {
        this.adminToken = TestUtil.getAdminToken();
    }

    @BeforeEach
    public void databaseFixture() {
        try (Liquibase liquibase = liquibaseFactory.createLiquibase()) {
            liquibase.dropAll();
            liquibase.validate();
            liquibase.update(liquibaseFactory.createContexts(), liquibaseFactory.createLabels());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DiscountCodeDTO createEntity() {
        var discountCodeDTO = new DiscountCodeDTO();
        discountCodeDTO.code = DEFAULT_CODE;
        discountCodeDTO.discount = DEFAULT_DISCOUNT;
        return discountCodeDTO;
    }

    @BeforeEach
    public void initTest() {
        discountCodeDTO = createEntity();
    }

    @Test
    public void createDiscountCode() {
        var databaseSizeBeforeCreate = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/discount-codes")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE)
            .size();

        // Create the DiscountCode
        discountCodeDTO = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(discountCodeDTO)
            .when()
            .post("/api/discount-codes")
            .then()
            .statusCode(CREATED.getStatusCode())
            .extract().as(ENTITY_TYPE);

        // Validate the DiscountCode in the database
        var discountCodeDTOList = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/discount-codes")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE);

        assertThat(discountCodeDTOList).hasSize(databaseSizeBeforeCreate + 1);
        var testDiscountCodeDTO = discountCodeDTOList.stream().filter(it -> discountCodeDTO.id.equals(it.id)).findFirst().get();
        assertThat(testDiscountCodeDTO.code).isEqualTo(DEFAULT_CODE);
        assertThat(testDiscountCodeDTO.discount).isEqualTo(DEFAULT_DISCOUNT);
    }

    @Test
    public void createDiscountCodeWithExistingId() {
        var databaseSizeBeforeCreate = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/discount-codes")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE)
            .size();

        // Create the DiscountCode with an existing ID
        discountCodeDTO.id = 1L;

        // An entity with an existing ID cannot be created, so this API call must fail
        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(discountCodeDTO)
            .when()
            .post("/api/discount-codes")
            .then()
            .statusCode(BAD_REQUEST.getStatusCode());

        // Validate the DiscountCode in the database
        var discountCodeDTOList = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/discount-codes")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE);

        assertThat(discountCodeDTOList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkCodeIsRequired() throws Exception {
        var databaseSizeBeforeTest = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/discount-codes")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE)
            .size();

        // set the field null
        discountCodeDTO.code = null;

        // Create the DiscountCode, which fails.
        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(discountCodeDTO)
            .when()
            .post("/api/discount-codes")
            .then()
            .statusCode(BAD_REQUEST.getStatusCode());

        // Validate the DiscountCode in the database
        var discountCodeDTOList = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/discount-codes")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE);

        assertThat(discountCodeDTOList).hasSize(databaseSizeBeforeTest);
    }
    @Test
    public void checkDiscountIsRequired() throws Exception {
        var databaseSizeBeforeTest = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/discount-codes")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE)
            .size();

        // set the field null
        discountCodeDTO.discount = null;

        // Create the DiscountCode, which fails.
        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(discountCodeDTO)
            .when()
            .post("/api/discount-codes")
            .then()
            .statusCode(BAD_REQUEST.getStatusCode());

        // Validate the DiscountCode in the database
        var discountCodeDTOList = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/discount-codes")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE);

        assertThat(discountCodeDTOList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void updateDiscountCode() {
        // Initialize the database
        discountCodeDTO = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(discountCodeDTO)
            .when()
            .post("/api/discount-codes")
            .then()
            .statusCode(CREATED.getStatusCode())
            .extract().as(ENTITY_TYPE);

        var databaseSizeBeforeUpdate = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/discount-codes")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE)
            .size();

        // Get the discountCode
        var updatedDiscountCodeDTO = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/discount-codes/{id}", discountCodeDTO.id)
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().body().as(ENTITY_TYPE);

        // Update the discountCode
        updatedDiscountCodeDTO.code = UPDATED_CODE;
        updatedDiscountCodeDTO.discount = UPDATED_DISCOUNT;

        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(updatedDiscountCodeDTO)
            .when()
            .put("/api/discount-codes")
            .then()
            .statusCode(OK.getStatusCode());

        // Validate the DiscountCode in the database
        var discountCodeDTOList = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/discount-codes")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE);

        assertThat(discountCodeDTOList).hasSize(databaseSizeBeforeUpdate);
        var testDiscountCodeDTO = discountCodeDTOList.stream().filter(it -> updatedDiscountCodeDTO.id.equals(it.id)).findFirst().get();
        assertThat(testDiscountCodeDTO.code).isEqualTo(UPDATED_CODE);
        assertThat(testDiscountCodeDTO.discount).isEqualTo(UPDATED_DISCOUNT);
    }

    @Test
    public void updateNonExistingDiscountCode() {
        var databaseSizeBeforeUpdate = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/discount-codes")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE)
            .size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(discountCodeDTO)
            .when()
            .put("/api/discount-codes")
            .then()
            .statusCode(BAD_REQUEST.getStatusCode());

        // Validate the DiscountCode in the database
        var discountCodeDTOList = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/discount-codes")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE);

        assertThat(discountCodeDTOList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteDiscountCode() {
        // Initialize the database
        discountCodeDTO = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(discountCodeDTO)
            .when()
            .post("/api/discount-codes")
            .then()
            .statusCode(CREATED.getStatusCode())
            .extract().as(ENTITY_TYPE);

        var databaseSizeBeforeDelete = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/discount-codes")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE)
            .size();

        // Delete the discountCode
        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .delete("/api/discount-codes/{id}", discountCodeDTO.id)
            .then()
            .statusCode(NO_CONTENT.getStatusCode());

        // Validate the database contains one less item
        var discountCodeDTOList = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/discount-codes")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE);

        assertThat(discountCodeDTOList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void getAllDiscountCodes() {
        // Initialize the database
        discountCodeDTO = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(discountCodeDTO)
            .when()
            .post("/api/discount-codes")
            .then()
            .statusCode(CREATED.getStatusCode())
            .extract().as(ENTITY_TYPE);

        // Get all the discountCodeList
        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/discount-codes?sort=id,desc")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .body("id", hasItem(discountCodeDTO.id.intValue()))
            .body("code", hasItem(DEFAULT_CODE))            .body("discount", hasItem(DEFAULT_DISCOUNT.floatValue()));
    }

    @Test
    public void getDiscountCode() {
        // Initialize the database
        discountCodeDTO = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(discountCodeDTO)
            .when()
            .post("/api/discount-codes")
            .then()
            .statusCode(CREATED.getStatusCode())
            .extract().as(ENTITY_TYPE);

        var response = // Get the discountCode
            given()
                .auth()
                .preemptive()
                .oauth2(adminToken)
                .accept(APPLICATION_JSON)
                .when()
                .get("/api/discount-codes/{id}", discountCodeDTO.id)
                .then()
                .statusCode(OK.getStatusCode())
                .contentType(APPLICATION_JSON)
                .extract().as(ENTITY_TYPE);

        // Get the discountCode
        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/discount-codes/{id}", discountCodeDTO.id)
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .body("id", is(discountCodeDTO.id.intValue()))

                .body("code", is(DEFAULT_CODE))
                .body("discount", is(DEFAULT_DISCOUNT.floatValue()));
    }

    @Test
    public void getNonExistingDiscountCode() {
        // Get the discountCode
        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/discount-codes/{id}", Long.MAX_VALUE)
            .then()
            .statusCode(NOT_FOUND.getStatusCode());
    }
}
