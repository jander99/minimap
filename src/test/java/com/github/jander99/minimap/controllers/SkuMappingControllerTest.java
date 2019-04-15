package com.github.jander99.minimap.controllers;

import com.github.jander99.minimap.repositories.SkuMappingRepository;
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
public class SkuMappingControllerTest {

    private MockMvc mockMvc;

    private SkuMappingRepository skuMappingRepository;

    private SkuMappingController skuMappingController;

    @Before
    public void setup() {
        skuMappingRepository = mock(SkuMappingRepository.class);
        skuMappingController = new SkuMappingController(skuMappingRepository);

        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(skuMappingController).build();
    }

    @Test
    public void givenACallToSkuFindAllWhenNoRecordsFoundThenReturnEmptyList() throws Exception {

        when(skuMappingRepository.findAll()).thenReturn(new ArrayList<>());

        MvcResult result = mockMvc.perform(get("/api/mappings/sku")).andExpect(status().isOk()).andReturn();

        assertThat(result).isNotNull();
    }

    @Test
    public void givenACallForSpecificSkuAndNoResultThenReturn404() throws Exception {

        when(skuMappingRepository.findBySku(123)).thenReturn(null);

        MvcResult result = mockMvc.perform(get("/api/mappings/sku/123")).andExpect(status().isNotFound()).andReturn();

        assertThat(result).isNotNull();

    }



}