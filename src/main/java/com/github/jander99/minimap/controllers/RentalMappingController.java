package com.github.jander99.minimap.controllers;

import com.github.jander99.minimap.entities.RentalMapping;
import com.github.jander99.minimap.repositories.RentalMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/mappings/rental")
public class RentalMappingController {

    private RentalMappingRepository rentalMappingRepository;

    @Autowired
    public RentalMappingController(final RentalMappingRepository rentalMappingRepository) {
        this.rentalMappingRepository = rentalMappingRepository;
    }


    @RequestMapping(method = RequestMethod.GET)
    public HttpEntity<Resources<RentalMapping>> findAll() {

        Link selfRelLink = ControllerLinkBuilder
                .linkTo(ControllerLinkBuilder
                .methodOn(RentalMappingController.class)
                .findAll())
                .withSelfRel();

        List<RentalMapping> rentalMappingList = rentalMappingRepository.findAll();

        Resources<RentalMapping> rentalMappingResources = new Resources<>(rentalMappingList, selfRelLink);

        return ResponseEntity.ok(rentalMappingResources);
    }

    @RequestMapping(
            path = "/category/{category}/subcategory/{subcategory}",
            method = RequestMethod.GET
    )
    public HttpEntity<Resource<RentalMapping>> findByCategorySubcategory(
            @PathVariable("category") final String category,
            @PathVariable("subcategory") final String subCategory) {

        Link selfRelLink = ControllerLinkBuilder
                .linkTo(ControllerLinkBuilder
                        .methodOn(RentalMappingController.class)
                        .findByCategorySubcategory(category, subCategory))
                .withSelfRel();

        RentalMapping rentalMapping = rentalMappingRepository.findByCategoryAndSubcategory(category, subCategory);

        if(rentalMapping == null) {
            return ResponseEntity.notFound().build();
        }

        Resource<RentalMapping> rentalMappingResource = new Resource<>(rentalMapping);
        rentalMappingResource.add(selfRelLink);

        return ResponseEntity.ok(rentalMappingResource);
    }

}
