package com.owczarczak.footballers.clubRepresentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/representation")
public class ClubRepresentationController {
    @Autowired
    private ClubRepresentationService service;

    @GetMapping("/")
    public List<ClubRepresentationDto> getAllRepresentations() {
        return service.getAllClubRepresentations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClubRepresentationDto> getClubRepresentationById(@PathVariable Long id) {
        Optional<ClubRepresentationDto> foundDtoOptional = service.getClubRepresentationById(id);
        if (foundDtoOptional.isEmpty()) {
            return notFound().build();
        } else {
            return ok(foundDtoOptional.get());
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteClubRepresentation(@PathVariable Long id) {
        service.deleteClubRepresentation(id);
        return ok().build();
    }
}
