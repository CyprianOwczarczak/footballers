package com.owczarczak.footballers.contract;

import com.owczarczak.footballers.club.ClubDto;
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
import java.util.ArrayList;
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
    public ResponseEntity<ContractDto> getContractById(@PathVariable Long id) {
        Optional<ContractDto> foundDtoOptional = service.getContractById(id);
        return foundDtoOptional.map(ResponseEntity::ok).orElseGet(() -> notFound().build());
    }

    @GetMapping("/contractsForFootballer/{id}")
    public ResponseEntity<Object> getListOfContractsForSpecificFootballer(@PathVariable Long id) {
        List<ContractDto> foundContractList = service.getListOfContractsForSpecificFootballer(id);
        if (foundContractList.isEmpty()) {
            return notFound().build();
        } else {
            return ok(foundContractList);
        }
    }

    @GetMapping("/lengthOf/{clubId}")
    public ContractLengthDto getMeanLengthOfContractsInClub(@PathVariable("clubId") Long clubId) {
        return service.getMeanLenghtOfContractsInClub(clubId);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteContract(@PathVariable Long id) {
        service.deleteContractById(id);
        return ok().build();
    }

    @PostMapping("/")
    ResponseEntity<?> addContract(@RequestBody ContractAddDto newContractDto) {
        ArrayList<String> errorList = returnErrorList(newContractDto);
        if (!errorList.isEmpty()) {
            return ResponseEntity.badRequest().body(errorList);
        }

        ContractAddDto result = service.addContract(newContractDto);

        return ResponseEntity
                .created(URI.create("/" + result.getId()))
                .body(result);
    }

    @PutMapping("/{id}")
    ResponseEntity<?> extendContractLength(@PathVariable Long id,
                                           @RequestParam("daysToAdd") Long daysToAdd) {
        if (service.getContractById(id).isEmpty()) {
            return notFound().build();
        } else {
            Optional<ContractDto> result = service.extendContractLength(id, daysToAdd);
            return ok().body(result);
        }
    }

    private ArrayList<String> returnErrorList(ContractAddDto newContractDto) {
        ArrayList<String> errorList = new ArrayList<>();

        if (newContractDto.getClubId() == null) {
            errorList.add("You have to provide a club id !");
        }
        if (newContractDto.getFootballerId() == null) {
            errorList.add("You have to provide a footballer id !");
        }
        if (newContractDto.getContractStart() == null) {
            errorList.add("You have to provide a contract start date !");
        }
        if (newContractDto.getContractEnd() == null) {
            errorList.add("You have to provide a contract end date !");
        }
        if (newContractDto.getSalary() == null) {
            errorList.add("You have to provide a salary !");
        }
        return errorList;
    }
}
