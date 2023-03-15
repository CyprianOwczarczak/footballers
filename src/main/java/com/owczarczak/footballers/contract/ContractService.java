package com.owczarczak.footballers.contract;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

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
}
