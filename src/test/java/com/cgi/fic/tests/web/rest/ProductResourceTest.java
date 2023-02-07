package com.cgi.fic.tests.web.rest;

import static io.restassured.RestAssured.given;
import static io.restassured.config.ObjectMapperConfig.objectMapperConfig;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

import com.cgi.fic.tests.TestUtil;
import com.cgi.fic.tests.service.dto.ProductDTO;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import liquibase.Liquibase;
import io.quarkus.liquibase.LiquibaseFactory;
import org.junit.jupiter.api.*;

import javax.inject.Inject;

import java.util.List;

@QuarkusTest
public class ProductResourceTest {

    private static final TypeRef<ProductDTO> ENTITY_TYPE = new TypeRef<>() {
    };

    private static final TypeRef<List<ProductDTO>> LIST_OF_ENTITY_TYPE = new TypeRef<>() {
    };

    private static final String DEFAULT_PRODUCT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_NAME = "BBBBBBBBBB";

    private static final Float DEFAULT_UNIT_PRICE = 1F;
    private static final Float UPDATED_UNIT_PRICE = 2F;

    private static final String DEFAULT_EAN_13_BAR_CODE = "AAAAAAAAAA";
    private static final String UPDATED_EAN_13_BAR_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_BRAND = "AAAAAAAAAA";
    private static final String UPDATED_BRAND = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORIES = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORIES = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";



    String adminToken;

    ProductDTO productDTO;

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
    public static ProductDTO createEntity() {
        var productDTO = new ProductDTO();
        productDTO.productName = DEFAULT_PRODUCT_NAME;
        productDTO.unitPrice = DEFAULT_UNIT_PRICE;
        productDTO.ean13BarCode = DEFAULT_EAN_13_BAR_CODE;
        productDTO.brand = DEFAULT_BRAND;
        productDTO.categories = DEFAULT_CATEGORIES;
        productDTO.imageUrl = DEFAULT_IMAGE_URL;
        return productDTO;
    }

    @BeforeEach
    public void initTest() {
        productDTO = createEntity();
    }

    @Test
    public void createProduct() {
        var databaseSizeBeforeCreate = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/products")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE)
            .size();

        // Create the Product
        productDTO = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(productDTO)
            .when()
            .post("/api/products")
            .then()
            .statusCode(CREATED.getStatusCode())
            .extract().as(ENTITY_TYPE);

        // Validate the Product in the database
        var productDTOList = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/products")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE);

        assertThat(productDTOList).hasSize(databaseSizeBeforeCreate + 1);
        var testProductDTO = productDTOList.stream().filter(it -> productDTO.id.equals(it.id)).findFirst().get();
        assertThat(testProductDTO.productName).isEqualTo(DEFAULT_PRODUCT_NAME);
        assertThat(testProductDTO.unitPrice).isEqualTo(DEFAULT_UNIT_PRICE);
        assertThat(testProductDTO.ean13BarCode).isEqualTo(DEFAULT_EAN_13_BAR_CODE);
        assertThat(testProductDTO.brand).isEqualTo(DEFAULT_BRAND);
        assertThat(testProductDTO.categories).isEqualTo(DEFAULT_CATEGORIES);
        assertThat(testProductDTO.imageUrl).isEqualTo(DEFAULT_IMAGE_URL);
    }

    @Test
    public void createProductWithExistingId() {
        var databaseSizeBeforeCreate = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/products")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE)
            .size();

        // Create the Product with an existing ID
        productDTO.id = 1L;

        // An entity with an existing ID cannot be created, so this API call must fail
        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(productDTO)
            .when()
            .post("/api/products")
            .then()
            .statusCode(BAD_REQUEST.getStatusCode());

        // Validate the Product in the database
        var productDTOList = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/products")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE);

        assertThat(productDTOList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkProductNameIsRequired() throws Exception {
        var databaseSizeBeforeTest = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/products")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE)
            .size();

        // set the field null
        productDTO.productName = null;

        // Create the Product, which fails.
        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(productDTO)
            .when()
            .post("/api/products")
            .then()
            .statusCode(BAD_REQUEST.getStatusCode());

        // Validate the Product in the database
        var productDTOList = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/products")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE);

        assertThat(productDTOList).hasSize(databaseSizeBeforeTest);
    }
    @Test
    public void checkUnitPriceIsRequired() throws Exception {
        var databaseSizeBeforeTest = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/products")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE)
            .size();

        // set the field null
        productDTO.unitPrice = null;

        // Create the Product, which fails.
        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(productDTO)
            .when()
            .post("/api/products")
            .then()
            .statusCode(BAD_REQUEST.getStatusCode());

        // Validate the Product in the database
        var productDTOList = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/products")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE);

        assertThat(productDTOList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void updateProduct() {
        // Initialize the database
        productDTO = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(productDTO)
            .when()
            .post("/api/products")
            .then()
            .statusCode(CREATED.getStatusCode())
            .extract().as(ENTITY_TYPE);

        var databaseSizeBeforeUpdate = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/products")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE)
            .size();

        // Get the product
        var updatedProductDTO = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/products/{id}", productDTO.id)
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().body().as(ENTITY_TYPE);

        // Update the product
        updatedProductDTO.productName = UPDATED_PRODUCT_NAME;
        updatedProductDTO.unitPrice = UPDATED_UNIT_PRICE;
        updatedProductDTO.ean13BarCode = UPDATED_EAN_13_BAR_CODE;
        updatedProductDTO.brand = UPDATED_BRAND;
        updatedProductDTO.categories = UPDATED_CATEGORIES;
        updatedProductDTO.imageUrl = UPDATED_IMAGE_URL;

        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(updatedProductDTO)
            .when()
            .put("/api/products")
            .then()
            .statusCode(OK.getStatusCode());

        // Validate the Product in the database
        var productDTOList = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/products")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE);

        assertThat(productDTOList).hasSize(databaseSizeBeforeUpdate);
        var testProductDTO = productDTOList.stream().filter(it -> updatedProductDTO.id.equals(it.id)).findFirst().get();
        assertThat(testProductDTO.productName).isEqualTo(UPDATED_PRODUCT_NAME);
        assertThat(testProductDTO.unitPrice).isEqualTo(UPDATED_UNIT_PRICE);
        assertThat(testProductDTO.ean13BarCode).isEqualTo(UPDATED_EAN_13_BAR_CODE);
        assertThat(testProductDTO.brand).isEqualTo(UPDATED_BRAND);
        assertThat(testProductDTO.categories).isEqualTo(UPDATED_CATEGORIES);
        assertThat(testProductDTO.imageUrl).isEqualTo(UPDATED_IMAGE_URL);
    }

    @Test
    public void updateNonExistingProduct() {
        var databaseSizeBeforeUpdate = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/products")
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
            .body(productDTO)
            .when()
            .put("/api/products")
            .then()
            .statusCode(BAD_REQUEST.getStatusCode());

        // Validate the Product in the database
        var productDTOList = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/products")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE);

        assertThat(productDTOList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteProduct() {
        // Initialize the database
        productDTO = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(productDTO)
            .when()
            .post("/api/products")
            .then()
            .statusCode(CREATED.getStatusCode())
            .extract().as(ENTITY_TYPE);

        var databaseSizeBeforeDelete = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/products")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE)
            .size();

        // Delete the product
        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .delete("/api/products/{id}", productDTO.id)
            .then()
            .statusCode(NO_CONTENT.getStatusCode());

        // Validate the database contains one less item
        var productDTOList = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/products")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .extract().as(LIST_OF_ENTITY_TYPE);

        assertThat(productDTOList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void getAllProducts() {
        // Initialize the database
        productDTO = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(productDTO)
            .when()
            .post("/api/products")
            .then()
            .statusCode(CREATED.getStatusCode())
            .extract().as(ENTITY_TYPE);

        // Get all the productList
        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/products?sort=id,desc")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON);
    }

    @Test
    public void getProduct() {
        // Initialize the database
        productDTO = given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(productDTO)
            .when()
            .post("/api/products")
            .then()
            .statusCode(CREATED.getStatusCode())
            .extract().as(ENTITY_TYPE);

        var response = // Get the product
            given()
                .auth()
                .preemptive()
                .oauth2(adminToken)
                .accept(APPLICATION_JSON)
                .when()
                .get("/api/products/{id}", productDTO.id)
                .then()
                .statusCode(OK.getStatusCode())
                .contentType(APPLICATION_JSON)
                .extract().as(ENTITY_TYPE);

        // Get the product
        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/products/{id}", productDTO.id)
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .body("id", is(productDTO.id.intValue()))

                .body("productName", is(DEFAULT_PRODUCT_NAME))
                .body("unitPrice", is(DEFAULT_UNIT_PRICE.floatValue()))
                .body("ean13BarCode", is(DEFAULT_EAN_13_BAR_CODE))
                .body("brand", is(DEFAULT_BRAND))
                .body("categories", is(DEFAULT_CATEGORIES))
                .body("imageUrl", is(DEFAULT_IMAGE_URL));
    }

    @Test
    public void getNonExistingProduct() {
        // Get the product
        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/products/{id}", Long.MAX_VALUE)
            .then()
            .statusCode(NOT_FOUND.getStatusCode());
    }
}
