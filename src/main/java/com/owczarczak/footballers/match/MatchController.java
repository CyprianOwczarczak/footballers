package com.owczarczak.footballers.match;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/matches")
public class MatchController {

    @Autowired
    MatchService service;

    @GetMapping("/byRefereeName/{name}")
    List<MatchDto> findByRefereeName(@RequestParam("name") String name) {
        return service.findByRefereeName(name);
    }
}
