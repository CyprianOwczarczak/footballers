package com.owczarczak.footballers.club;

import com.owczarczak.footballers.footballer.FootballerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
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

    @PostMapping("/")
    ResponseEntity<?> addClub(@RequestBody ClubDto newClubDto) {
        if (service.repository.existsById(newClubDto.getId())) {
            return ResponseEntity.badRequest().build();
        }

        ClubDto result = service.addClub(newClubDto);
        return ResponseEntity
                .created(URI.create("/" + result.getId()))
                .body(result);
    }

    @DeleteMapping("/{id}")
    ResponseEntity deleteClub(@PathVariable int id) {
        service.deleteClubById(id);
        return ok().build();
    }
}
