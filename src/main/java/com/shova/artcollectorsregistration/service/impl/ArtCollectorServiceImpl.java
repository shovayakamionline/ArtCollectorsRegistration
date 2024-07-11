package com.shova.artcollectorsregistration.service.impl;

import com.shova.artcollectorsregistration.dto.ArtCollectorDto;
import com.shova.artcollectorsregistration.entity.ArtCollector;
import com.shova.artcollectorsregistration.entity.Role;
import com.shova.artcollectorsregistration.repository.ArtCollectorRepository;
import com.shova.artcollectorsregistration.repository.RoleRepository;
import com.shova.artcollectorsregistration.service.ArtCollectorService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArtCollectorServiceImpl implements ArtCollectorService {
    private ArtCollectorRepository artCollectorRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public ArtCollectorServiceImpl(ArtCollectorRepository artCollectorRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.artCollectorRepository = artCollectorRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveArtCollector(ArtCollectorDto artCollectorDto) {
        ArtCollector artCollector = new ArtCollector();
        artCollector.setUsername(artCollectorDto.getFirstName()+ " " + artCollectorDto.getLastName());
        artCollector.setEmail(artCollectorDto.getEmail());

        artCollector.setPassword(passwordEncoder.encode(artCollectorDto.getPassword()));


        //Determine the role based on registration criteria
        String roleName;
        if(artCollectorDto.isAdminRegistration()){
            roleName = "ROLE_ADMIN";
        }else{
            roleName= "ROLE_ARTCOLLECTOR";
        }

        //Check if role already exists in database, otherwise create it
        Role role = roleRepository.findByName(roleName);
        if(role == null){
            role = new Role();
            role.setName(roleName);
            roleRepository.save(role);
        }

        //Assign the role to the artCollector
        artCollector.setRoles(Collections.singletonList(role));
        artCollector.setCountry(artCollectorDto.getCountry());
        artCollectorRepository.save(artCollector);


    }


    @Override
    public ArtCollector findByEmail(String email) {
        return artCollectorRepository.findByEmail(email);
    }

    @Override
    public List<ArtCollectorDto> findAllArtCollectors() {
        List<ArtCollector> artCollectors = artCollectorRepository.findAll();
        return artCollectors.stream().map((artCollector) -> convertEntityToDto(artCollector))
                .collect(Collectors.toList());
    }


    private ArtCollectorDto convertEntityToDto(ArtCollector artCollector){
        ArtCollectorDto artCollectorDto = new ArtCollectorDto();
        String[] name = artCollector.getUsername().split(" ");
        artCollectorDto.setFirstName(name[0]);
        artCollectorDto.setLastName(name[1]);
        artCollectorDto.setEmail(artCollector.getEmail());
        artCollectorDto.setCountry(artCollector.getCountry());
        return artCollectorDto;
    }
}
