package com.owczarczak.footballers.club;

import com.owczarczak.footballers.contract.ContractDto;
import org.springframework.aop.framework.autoproxy.target.LazyInitTargetSourceCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/clubs")
public class ClubController {
    @Autowired
    ClubService service;

    @GetMapping("/")
    public List<ClubDto> getAllClubs() {
        return service.getAllClubs();
    }

    @GetMapping("/MoreThan3Matches")
    public List<ClubDto> getAllClubsWhichPlayedMoreThan3Matches() {
        return service.getAllClubsWhichPlayedMoreThan3Matches();
    }
}
