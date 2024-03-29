package com.owczarczak.footballers.contract;

import com.owczarczak.footballers.club.Club;
import com.owczarczak.footballers.club.ClubRepository;
import com.owczarczak.footballers.footballer.Footballer;
import com.owczarczak.footballers.footballer.FootballerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class ContractService {
    @Autowired
    private ContractRepository contractRepository;
    @Autowired
    private ClubRepository clubRepository;
    @Autowired
    private FootballerRepository footballerRepository;

    public List<ContractDto> getAllContracts() {
        List<Contract> contractList = contractRepository.findAll();
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

    Optional<ContractDto> getContractById(@PathVariable Long id) {
        if (!contractRepository.existsById(id)) {
            return Optional.empty();
        } else {
            Contract contract = contractRepository.getReferenceById(id);
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

    public List<ContractDto> getListOfContractsForSpecificFootballer(Long footballerId) {
        List<Contract> contractList = contractRepository.getListOfContractsForSpecificFootballer(footballerId);
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

    public ContractLengthDto getMeanLenghtOfContractsInClub(Long clubId) {
        //We get the club id from the parameter
        BigDecimal averageLength = contractRepository.getMeanLenghtOfContractsInClub(clubId);
        return ContractLengthDto.builder()
                .clubId(clubId)
                .averageLength(averageLength.longValue())
                .build();
    }

    public Optional<ContractDto> extendContractLength(Long id, Long daysToAdd) {
        Contract contractToUpdate;
        if (!contractRepository.existsById(id)) {
            return Optional.empty();
        }
        contractToUpdate = contractRepository.getReferenceById(id);
        LocalDate newContractEnd = contractToUpdate.getContractEnd().plus(daysToAdd, ChronoUnit.DAYS);
        contractToUpdate.setContractEnd(newContractEnd);
        Contract savedEntity = contractRepository.save(contractToUpdate);

        return Optional.ofNullable(ContractDto.builder()
                .id(savedEntity.getId())
                .clubName(savedEntity.getClub().getName())
                .footballerName(savedEntity.getFootballer().getName())
                .contractStart(savedEntity.getContractStart())
                .contractEnd(savedEntity.getContractEnd())
                .salary(savedEntity.getSalary())
                .build());
    }

    public ContractAddDto addContract(ContractAddDto contractToBeAdded) {

        Club optionalClub = clubRepository.findById(contractToBeAdded.getClubId()).orElseThrow(RuntimeException::new);
        Footballer optionalFootballer = footballerRepository
                .findById(contractToBeAdded.getFootballerId()).orElseThrow(RuntimeException::new);

        Contract newContract = Contract.builder()
                .club(optionalClub)
                .footballer(optionalFootballer)
                .contractStart(contractToBeAdded.getContractStart())
                .contractEnd(contractToBeAdded.getContractEnd())
                .salary(contractToBeAdded.getSalary())
                .build();
        Contract savedEntity = contractRepository.save(newContract);

        return ContractAddDto.builder()
                .id(savedEntity.getId())
                .clubId(savedEntity.getClub().getId())
                .footballerId(savedEntity.getFootballer().getId())
                .contractStart(savedEntity.getContractStart())
                .contractEnd(savedEntity.getContractEnd())
                .salary(savedEntity.getSalary())
                .build();
    }

    @Transactional
    public void deleteContractById(@PathVariable Long id) {
        if (contractRepository.existsById(id)) {
            contractRepository.deleteById(id);
        }
    }
}
