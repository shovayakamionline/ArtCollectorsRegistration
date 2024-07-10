package com.shova.artcollectorsregistration.controller;

import com.shova.artcollectorsregistration.dto.ArtCollectorDto;
import com.shova.artcollectorsregistration.entity.ArtCollector;
import com.shova.artcollectorsregistration.service.ArtCollectorService;
import org.springframework.stereotype.Controller;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AuthorizeController {

    private ArtCollectorService artCollectorService;

    @Autowired
    public AuthorizeController(ArtCollectorService artCollectorService) {
        this.artCollectorService = artCollectorService;
    }

    @GetMapping("index")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    //handler method will handle user registration request
    @GetMapping("art-collector-registration")
    public String showRegistrationForm(Model model) {
        ArtCollectorDto artCollectorDto = new ArtCollectorDto();
        model.addAttribute("artCollectorDto", artCollectorDto);
        return "art-collector-registration";
    }

    @PostMapping("/art-collector-registration/save")
    public String registration(@Valid @ModelAttribute("artCollector") ArtCollectorDto artCollectorDto, BindingResult result, Model model) {
        ArtCollector existing = artCollectorService.findByEmail(artCollectorDto.getEmail());
        if (existing != null) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }

        if (result.hasErrors()) {
            model.addAttribute("artCollectorDto", artCollectorDto);
            return "art-collector-registration";
        }

        artCollectorService.saveArtCollector(artCollectorDto);
        return "redirect:/art-collector-registration?success";
    }

    @GetMapping("/artcollectors")
    public String listRegisteredUsers(Model model){
        List<ArtCollectorDto> artCollectorDtos = artCollectorService.findAllArtCollectors();
        model.addAttribute("artCollectorDtos", artCollectorDtos);
        return "registered-art-collectors";

    }
}
