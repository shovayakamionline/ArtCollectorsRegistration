package com.shova.artcollectorsregistration.service;

import com.shova.artcollectorsregistration.dto.ArtCollectorDto;
import com.shova.artcollectorsregistration.entity.ArtCollector;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ArtCollectorService {
    void saveArtCollector(ArtCollectorDto artCollectorDto);
    ArtCollector findByEmail(String email);
    List<ArtCollectorDto> findAllArtCollectors();
}
