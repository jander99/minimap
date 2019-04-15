package com.github.jander99.minimap.features;

import com.github.jander99.minimap.entities.RentalMapping;
import com.github.jander99.minimap.entities.SkuMapping;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@Slf4j
public class EndpointTests {


    @Autowired
    TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int port;


    @Test
    public void testSkuListEndpoint() {

        String endpoint = "/api/mappings/sku";

        String url = "http://localhost:" + port + endpoint;

        ResponseEntity<Resources<SkuMapping>> result = testRestTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Resources<SkuMapping>>(){});



        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotNull();
        List<SkuMapping> mappings = new ArrayList<>(result.getBody().getContent());

        assertThat(mappings).hasSize(6);
    }

    @Test
    public void testSpecificSkuEndpointReturnsGasCode() {

        String endpoint = "/api/mappings/sku/";

        Long sku = 123456L;
        String url = "http://localhost:" + port + endpoint + sku;

        int expectedGasCode = 2050731;

        ResponseEntity<Resource<SkuMapping>> result = testRestTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Resource<SkuMapping>>(){});

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        SkuMapping mapping = result.getBody().getContent();

        assertThat(mapping.getSku()).isEqualTo(sku);
        assertThat(mapping.getGasCode()).isEqualTo(expectedGasCode);

        log.info(result.toString());
    }

    @Test
    public void testRentalListEndpoint() {

        String endpoint = "/api/mappings/rental/";

        String url = "http://localhost:" + port + endpoint;

        ResponseEntity<Resources<RentalMapping>> result = testRestTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Resources<RentalMapping>>(){});



        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotNull();
        List<RentalMapping> mappings = new ArrayList<>(result.getBody().getContent());

        assertThat(mappings).hasSize(6);
    }


    @Test
    public void testSpecificRentalEndpointReturnsGasCode() {

        String endpoint = "http://localhost:%s/api/mappings/rental/category/%s/subcategory/%s";

        String category = "01";
        String subcategory = "234";
        String url = String.format(endpoint,port, category, subcategory);

        int expectedGasCode = 2051547;

        ResponseEntity<Resource<RentalMapping>> result = testRestTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Resource<RentalMapping>>(){});

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        RentalMapping mapping = result.getBody().getContent();

        assertThat(mapping.getCategory()).isEqualTo(category);
        assertThat(mapping.getSubcategory()).isEqualTo(subcategory);
        assertThat(mapping.getGasCode()).isEqualTo(expectedGasCode);

        log.info(result.toString());
    }
}
