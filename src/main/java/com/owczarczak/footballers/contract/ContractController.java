package com.owczarczak.footballers.contract;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

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
    public ResponseEntity<Object> getListOfContractsForSpecificFootballer(@PathVariable int id) {
        Optional<ContractDto> foundDtoOptional = service.getContractById(id);
        if (foundDtoOptional.isEmpty()) {
            return notFound().build();
        } else {
            return ok(foundDtoOptional.get());
        }
    }

    @GetMapping("/lengthOf/{clubId}")
    public ContractLengthDto getMeanLengthOfContractsInClub(@PathVariable("clubId") int clubId) {
        return service.getMeanLenghtOfContractsInClub(clubId);
    }

    @DeleteMapping("/{id}")
    ResponseEntity deleteContract(@PathVariable int id) {
        service.deleteContract(id);
        return ok().build();
    }
}
