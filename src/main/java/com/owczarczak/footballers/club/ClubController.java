package com.owczarczak.footballers.club;

import com.owczarczak.footballers.match.MatchAddDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private ClubService service;

    @GetMapping("/")
    public List<ClubDto> getAllClubs() {
        return service.getAllClubs();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClubDto> getClubById(@PathVariable Long id) {
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
        ArrayList<String> errorList = returnErrorList(newClubDto);
        if (!errorList.isEmpty()) {
            return ResponseEntity.badRequest().body(errorList);
        }

        ClubDto result = service.addClub(newClubDto);
        return ResponseEntity
                .created(URI.create("/" + result.getId()))
                .body(result);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteClub(@PathVariable Long id) {
        service.deleteClubById(id);
        return ok().build();
    }

    private ArrayList<String> returnErrorList(ClubDto newClubDto) {
        ArrayList<String> errorList = new ArrayList<>();

        if (newClubDto.getName() == null) {
            errorList.add("You have to provide a club name !");
        }
        if (newClubDto.getCreated() == null) {
            errorList.add("You have to provide a creation date !");
        }
        return errorList;
    }
}
