package com.owczarczak.footballers.contract;

import com.owczarczak.footballers.footballer.FootballerDto;
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

    @GetMapping("/contractsForFootballer/{id}")
    public ResponseEntity<Object> getListOfContractsForSpecificFootballer(@PathVariable int id) {
        List<ContractDto> foundContractList = service.getListOfContractsForSpecificFootballer(id);
        if (foundContractList.isEmpty()) {
            return notFound().build();
        } else {
            return ok().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContractDto> getContractById(@PathVariable int id) {
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
        service.deleteContractById(id);
        return ok().build();
    }

    @PutMapping("/{id}")
    Optional<Contract> extendContractLength(@PathVariable int id,
                                           @RequestParam("monthsToAdd") int monthsToAdd) {
//        if (service.getContractById(id).isEmpty()) {
//            return notFound().build();
//        } else {
//            Optional<Contract> result = service.extendContractLength(id, monthsToAdd);
//            return ok(result.get());
//        }

        return service.extendContractLength(id, monthsToAdd);
    }
}
