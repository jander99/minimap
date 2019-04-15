package com.github.jander99.minimap.controllers;

import com.github.jander99.minimap.repositories.RentalMappingRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class RentalMappingControllerTest {

    private MockMvc mockMvc;

    private RentalMappingRepository rentalMappingRepository;

    private RentalMappingController rentalMappingController;

    @Before
    public void setup() {
        rentalMappingRepository = mock(RentalMappingRepository.class);
        rentalMappingController = new RentalMappingController(rentalMappingRepository);

        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(rentalMappingController).build();
    }

    @Test
    public void givenACallToRentalFindAllWhenNoRecordsFoundThenReturnEmptyList() throws Exception {

        when(rentalMappingRepository.findAll()).thenReturn(new ArrayList<>());

        MvcResult result = mockMvc.perform(get("/api/mappings/rental")).andExpect(status().isOk()).andReturn();

        assertThat(result).isNotNull();
    }

    @Test
    public void givenACallForSpecificRentalMappingAndNoResultThenReturn404() throws Exception {

        when(rentalMappingRepository.findByCategoryAndSubcategory("01", "123")).thenReturn(null);

        MvcResult result = mockMvc.perform(get("/api/mappings/rental/category/01/subcategory/123")).andExpect(status().isNotFound()).andReturn();

        assertThat(result).isNotNull();

    }


}
