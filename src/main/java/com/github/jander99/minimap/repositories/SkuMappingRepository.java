package com.github.jander99.minimap.repositories;

import com.github.jander99.minimap.entities.SkuMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "skuMappings", path="sku", exported = false)
public interface SkuMappingRepository extends JpaRepository<SkuMapping, Long> {

    SkuMapping findBySku(long sku);
}
