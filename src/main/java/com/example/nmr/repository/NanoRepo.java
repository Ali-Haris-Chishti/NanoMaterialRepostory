package com.example.nmr.repository;

import com.example.nmr.model.NanoMaterial;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface NanoRepo extends CrudRepository<NanoMaterial, Integer> {
    Optional<NanoMaterial> findByName(String name);
    @Query("SELECT nm FROM NanoMaterial nm WHERE (:name IS NULL OR :name = '' OR nm.name LIKE %:name%)")
    List<NanoMaterial> findAllByNameLike(@Param("name") String name);
    @Query("SELECT nm FROM NanoMaterial nm WHERE (:structure IS NULL OR nm.structure = :structure) AND (:name IS NULL OR :name = '' OR nm.name LIKE %:name%)")
    List<NanoMaterial> findAllByStructureAndNameLike(@Param("structure") String structure, @Param("name") String name);

    @Query("SELECT nm FROM NanoMaterial nm WHERE (:name IS NULL OR :name = '' OR nm.name LIKE %:name%) AND (:properties IS NULL OR nm.properties LIKE %:properties%)")
    List<NanoMaterial> findAllByNameLikeAndPropertiesLike(@Param("name") String name, @Param("properties") String properties);

    @Query("SELECT nm FROM NanoMaterial nm WHERE (:name IS NULL OR :name = '' OR nm.name LIKE %:name%) AND (:properties IS NULL OR nm.properties LIKE %:properties%) AND (:structure IS NULL OR nm.structure = :structure)")
    List<NanoMaterial> findAllByNameLikeAndPropertiesLikeAndStructure(@Param("name") String name, @Param("properties") String properties, @Param("structure") String structure);

}
