package com.shova.artcollectorsregistration.repository;

import com.shova.artcollectorsregistration.entity.ArtCollector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtCollectorRepository extends JpaRepository<ArtCollector, Long> {

    ArtCollector findByEmail(String email);
}
