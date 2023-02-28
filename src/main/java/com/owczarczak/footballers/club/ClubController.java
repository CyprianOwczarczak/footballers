package com.owczarczak.footballers.club;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;


//@RestController
//@RequestMapping("/clubs")
//public class ClubController {

//    @Autowired
//    ClubRepository repository;
//
//    @GetMapping("/")
//    public List<Club> getAllClubs() {
//        return repository.findAll();
//    }
//
//    @GetMapping("/{id}")
//    public Optional<Club> getClubById(@PathVariable int id) {
//        return repository.findById(id);
//    }
//
//    @GetMapping("/sortByName")
//    public List<Club> getClubsSortedByName() {
//        return repository.find;
//    }
//}
