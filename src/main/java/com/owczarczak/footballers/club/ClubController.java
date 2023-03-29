package com.owczarczak.footballers.club;

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
@RequestMapping("/clubs")
public class ClubController {
    @Autowired
    ClubService service;

    @GetMapping("/")
    public List<ClubDto> getAllClubs() {
        return service.getAllClubs();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClubDto> getClubById(@PathVariable int id) {
        Optional<ClubDto> foundDtoOptional = service.getClubById(id);
        if (foundDtoOptional.isEmpty()) {
            return notFound().build();
        } else {
            return ok(foundDtoOptional.get());
        }
    }

    @GetMapping("/MoreThan3Matches")
    public List<ClubDto> getAllClubsWhichPlayedMoreThan3Matches() {
        return service.getAllClubsWhichPlayedMoreThan3Matches();
    }

    @DeleteMapping("/{id}")
    ResponseEntity deleteClub(@PathVariable int id) {
        service.deleteClubById(id);
        return ok().build();
    }
}
