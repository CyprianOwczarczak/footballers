package com.owczarczak.footballers.contract;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class ContractService {
    @Autowired
    ContractRepository repository;

    public List<ContractDto> getAllContracts() {
        List<Contract> contractList = repository.findAll();
        List<ContractDto> dtos = new LinkedList<>();
        for (Contract contract : contractList) {
            ContractDto dtoToBeAdded = ContractDto.builder()
                    .id(contract.getId())
                    .clubName(contract.getClub().getName())
                    .footballerName(contract.getFootballer().getName())
                    .contractStart(contract.getContractStart())
                    .contractEnd(contract.getContractEnd())
                    .salary(contract.getSalary())
                    .build();
            dtos.add(dtoToBeAdded);
        }
        return dtos;
    }

    Optional<ContractDto> getContractById(@PathVariable int id) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        } else {
            Contract contract = repository.getReferenceById(id);
            return Optional.ofNullable(ContractDto.builder()
                    .id(contract.getId())
                    .clubName(contract.getClub().getName())
                    .footballerName(contract.getFootballer().getName())
                    .contractStart(contract.getContractStart())
                    .contractEnd(contract.getContractEnd())
                    .salary(contract.getSalary())
                    .build());
        }
    }

    public List<ContractDto> getListOfContractsForSpecificFootballer(int footballerId) {
        List<Contract> contractList = repository.getListOfContractsForSpecificFootballer(footballerId);
        List<ContractDto> dtos = new LinkedList<>();
        for (Contract contract : contractList) {
            ContractDto dtoToBeAdded = ContractDto.builder()
                    .id(contract.getId())
                    .clubName(contract.getClub().getName())
                    .footballerName(contract.getFootballer().getName())
                    .contractStart(contract.getContractStart())
                    .contractEnd(contract.getContractEnd())
                    .salary(contract.getSalary())
                    .build();
            dtos.add(dtoToBeAdded);
        }
        return dtos;
    }

    public ContractLengthDto getMeanLenghtOfContractsInClub(int clubId) {
        //Id klubu pobieramy z parametru
        BigDecimal averageLength = repository.getMeanLenghtOfContractsInClub(clubId);
        return ContractLengthDto.builder()
                .clubId(clubId)
                .averageLength(averageLength.intValue())
                .build();
    }

    @Transactional
    public void deleteContract(@PathVariable int id) {
        repository.deleteById(id);
    }
}
