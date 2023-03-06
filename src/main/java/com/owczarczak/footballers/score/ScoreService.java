package com.owczarczak.footballers.score;

import com.owczarczak.footballers.contract.Contract;
import com.owczarczak.footballers.contract.ContractDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScoreService {
    @Autowired
    ScoreRepository repository;

//    List<Object> getAvgGoalsPerMatchForFootballer() {
//        List<Object> scoreList = repository.getAvgGoalsPerMatchForFootballer();
//        List<ContractDto> dtos = new LinkedList<>();
//        for (Object object : scoreList) {
//            ScoreDto dtoToBeAdded = ScoreDto.builder()
//                    .id()
//                    .build();
//        }
//    }

}
