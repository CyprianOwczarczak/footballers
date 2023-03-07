package com.owczarczak.footballers.clubRepresentation;

import com.owczarczak.footballers.footballer.FootballerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/representation")
public class ClubRepresentationController {
    @Autowired
    ClubRepresentationService service;

    @GetMapping("/")
    public List<ClubRepresentationDto> getAllRepresentations() {
        return service.getAllClubRepresentations();
    }
}
