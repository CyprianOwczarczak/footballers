package com.owczarczak.footballers.club;

import com.owczarczak.footballers.contract.ContractDto;
import com.owczarczak.footballers.footballer.FootballerDto;
import org.springframework.aop.framework.autoproxy.target.LazyInitTargetSourceCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        service.deleteClub(id);
        return ok().build();
    }
}
