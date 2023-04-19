package com.owczarczak.footballers.contract;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/contracts")
public class ContractController {
    @Autowired
    private ContractService service;

    @GetMapping("/")
    public List<ContractDto> getAllContracts() {
        return service.getAllContracts();
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

    @GetMapping("/contractsForFootballer/{id}")
    public ResponseEntity<Object> getListOfContractsForSpecificFootballer(@PathVariable int id) {
        List<ContractDto> foundContractList = service.getListOfContractsForSpecificFootballer(id);
        if (foundContractList.isEmpty()) {
            return notFound().build();
        } else {
            return ok(foundContractList);
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

    @PostMapping("/")
    ResponseEntity<?> addContract(@RequestBody ContractAddDto newContractDto) {
        ContractAddDto result = service.addContract(newContractDto);

        return ResponseEntity
                .created(URI.create("/" + result.getId()))
                .body(result);
    }

    @PutMapping("/{id}")
    ResponseEntity extendContractLength(@PathVariable int id,
                                        @RequestParam("daysToAdd") int daysToAdd) {
        if (service.getContractById(id).isEmpty()) {
            return notFound().build();
        } else {
            Optional<ContractDto> result = service.extendContractLength(id, daysToAdd);
            return ok().body(result);
        }
    }
}
