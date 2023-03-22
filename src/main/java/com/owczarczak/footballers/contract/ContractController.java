package com.owczarczak.footballers.contract;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    //TODO extend the Contract of the Footballer whose contract comes to an end
    // (pass the number of days to extend the contract in the parameter)
    @PutMapping("/{id}")
    ResponseEntity<?> extendContractLength(@PathVariable int id,
                                           @RequestParam("monthsToAdd") int monthsToAdd) {
        if (service.getContractById(id).isEmpty()) {
            return notFound().build();
        }

        Optional<ContractDto> result = service.extendContractLength(id, monthsToAdd);
        return ok(result.get());
    }

//    @PutMapping("/")
//    ResponseEntity<?> updateFootballer(@RequestBody FootballerDto footballerToUpdate) {
//        if (service.getFootballerById(footballerToUpdate.getId()).isEmpty()) {
//            return notFound().build();
//        }
//
//        ArrayList<String> errorList = returnErrorList(footballerToUpdate);
//        if (!errorList.isEmpty()) {
//            return badRequest().body(errorList);
//        }
//        Optional<FootballerDto> result = service.updateFootballer(footballerToUpdate.getId(), footballerToUpdate);
//        return ok(result.get());
//    }


}
