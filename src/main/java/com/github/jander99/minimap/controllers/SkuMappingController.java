package com.github.jander99.minimap.controllers;

import com.github.jander99.minimap.entities.SkuMapping;
import com.github.jander99.minimap.repositories.SkuMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/mappings/sku")
public class SkuMappingController {

    private SkuMappingRepository skuMappingRepository;

    @Autowired
    public SkuMappingController(final SkuMappingRepository skuMappingRepository) {
        this.skuMappingRepository = skuMappingRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public HttpEntity<Resources<SkuMapping>> findAll() {

        Link selfRelLink = ControllerLinkBuilder
                .linkTo(ControllerLinkBuilder
                        .methodOn(SkuMappingController.class)
                        .findAll())
                .withSelfRel();

        List<SkuMapping> skuMappingList = skuMappingRepository.findAll();

        Resources<SkuMapping> skuMappingResources = new Resources<>(skuMappingList, selfRelLink);

        return ResponseEntity.ok(skuMappingResources);
    }



    @RequestMapping(path = "{sku}", method = RequestMethod.GET)
    public HttpEntity<Resource<SkuMapping>> findBySku(
            @PathVariable("sku") final long sku) {

        Link selfRelLink = ControllerLinkBuilder
                .linkTo(ControllerLinkBuilder
                        .methodOn(SkuMappingController.class)
                        .findBySku(sku))
                .withSelfRel();

        SkuMapping skuMapping = skuMappingRepository.findBySku(sku);

        if(skuMapping == null) {
            return ResponseEntity.notFound().build();
        }

        Resource<SkuMapping> skuMappingResource = new Resource<>(skuMapping);
        skuMappingResource.add(selfRelLink);

        return ResponseEntity.ok(skuMappingResource);
    }
}
