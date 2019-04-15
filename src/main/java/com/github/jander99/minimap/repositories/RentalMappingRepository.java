package com.github.jander99.minimap.repositories;

import com.github.jander99.minimap.entities.RentalMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path="rental", exported = false)
public interface RentalMappingRepository extends JpaRepository<RentalMapping, Long> {

    List<RentalMapping> findAll();

    RentalMapping findByCategoryAndSubcategory(
            final String category,
            final String subcategory);
}
