package com.owczarczak.footballers.contract;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/contracts")
public class ContractController {
    @Autowired
    ContractService service;

    @GetMapping("/")
    public List<ContractDto> getAllContracts() {
        return service.getAllContracts();
    }

    @GetMapping("/{id}")
    public List<ContractDto> getListOfContractsForSpecificFootballer(@PathVariable int id) {
        return service.getListOfContractsForSpecificFootballer(id);
    }

    @GetMapping("/lengthOf/{clubId}")
    public ContractLengthDto getMeanLengthOfContractsInClub(@PathVariable("clubId") int clubId) {
        return service.getMeanLenghtOfContractsInClub(clubId);
    }
}
